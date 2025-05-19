package com.lawfirm.api.config;

import com.lawfirm.auth.security.filter.JwtAuthenticationFilter;
import com.lawfirm.auth.security.provider.JwtTokenProvider;
import com.lawfirm.common.security.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Web安全配置 - 处理API资源访问安全
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 启用方法级别的安全注解
@RequiredArgsConstructor
@Order(90) // 确保高于common-security的优先级(默认100)
@ConditionalOnProperty(name = "law-firm.common.security.enabled", havingValue = "true", matchIfMissing = true)
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final Environment environment;

    /**
     * API文档相关的路径模式
     * 这些路径允许匿名访问
     */
    private static final String[] API_DOCS_PATHS = {
            "/doc.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/*/api-docs/**",
            "/knife4j/**",
            "/*.html",
            "/favicon.ico",
            "/swagger-config",
            "/swagger-ui.html"
    };

    private static final String[] PUBLIC_STATIC_RESOURCE_PATHS = {
            "/health/**",
            "/actuator/**",
            "/favicon.ico",
            "/static/**",
            "/error"
    };

    /**
     * 公开API路径 - 注意：生产环境应该严格限制
     * 任何添加到此处的路径将允许未认证访问，需要进行安全评估
     */
    private static final String[] PUBLIC_API_PATHS = {
            // 认证相关API
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/captcha",
            // 健康检查和基本信息API
            "/api/health",
            "/api/version",
            // API文档配置调试端点
            "/api/doc-config"
            // 注意：其他API路径应以类似"/api/auth/**"方式添加
            // SecurityConstants.PUBLIC_RESOURCE_PATHS 是全局设置，应谨慎合并
    };

    /**
     * 安全过滤器链 - API后端
     */
    @Bean("apiSecurityFilterChain") // 指定Bean名称以避免与common模块冲突
    @Order(101) // 确保此过滤器链在common模块的默认过滤器链之后（如果common模块有且优先级为100）
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置API模块特定的安全过滤器链 (apiSecurityFilterChain)");

        // 合并所有公共路径，统一使用 permitAll()
        String[] allPublicPaths = Stream.of(
                API_DOCS_PATHS,
                PUBLIC_STATIC_RESOURCE_PATHS,
                PUBLIC_API_PATHS
        ).flatMap(Arrays::stream).toArray(String[]::new);

        AntPathRequestMatcher[] publicMatchers = Arrays.stream(allPublicPaths)
                .map(AntPathRequestMatcher::antMatcher)
                .toArray(AntPathRequestMatcher[]::new);

        http
            .authorizeHttpRequests(authorize -> authorize
                // 所有定义的公共路径（API文档、静态资源、公共API）允许匿名访问
                .requestMatchers(publicMatchers).permitAll()
                // 其他所有 /api/** 下的请求都需要认证 (如果SecurityConstants.PUBLIC_RESOURCE_PATHS包含非/api/路径，需要调整此处的匹配)
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).authenticated()
                // 对于不由webSecurityCustomizer忽略且未在此处明确配置的其他所有请求，也要求认证
                // 如果担心有路径遗漏，可以更显式地拒绝或要求认证
                .anyRequest().authenticated() 
            )
            .csrf(AbstractHttpConfigurer::disable) // 禁用CSRF，因为使用JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 无状态会话
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // 添加JWT过滤器
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint((request, response, authException) -> {
                    log.warn("API未授权访问: {} - {}", request.getMethod(), request.getRequestURI(), authException);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"code\":401,\"message\":\"未授权访问，请先登录\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.warn("API访问被拒绝: {} - {} (用户: {})", request.getMethod(), request.getRequestURI(), request.getRemoteUser(), accessDeniedException);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"code\":403,\"message\":\"访问权限不足\"}");
                })
            );
            // 如果 common 模块的 BaseWebSecurityConfig 中的 commonWebSecurityFilterChain
            // 条件是 @ConditionalOnMissingBean(name = {"webSecurityFilterChain", "securityFilterChain"})
            // 那么这里的 Bean 名称 "apiSecurityFilterChain" 就不会冲突，不需要改回 "securityFilterChain"

        return http.build();
    }
} 