package com.lawfirm.auth.config;

import com.lawfirm.auth.security.filter.JwtAuthenticationFilter;
import com.lawfirm.auth.security.handler.LoginFailureHandler;
import com.lawfirm.auth.security.handler.LoginSuccessHandler;
import com.lawfirm.auth.security.provider.JwtTokenProvider;
import com.lawfirm.common.security.config.BaseSecurityConfig;
import com.lawfirm.common.security.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证模块安全配置类
 * <p>
 * 提供认证授权相关的安全配置，包括:
 * - JWT认证过滤器
 * - 密码编码器
 * - 认证管理器
 * - 安全过滤链
 * </p>
 * 
 * 注意: 该配置类优先级高于通用安全配置，Bean名称唯一，避免与其他安全配置冲突
 */
// @Configuration("authSecurityConfig") // Temporarily disable this configuration
@Configuration("authSecurityConfig")
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(90)  // 确保此配置在通用安全配置之前加载
public class SecurityConfig extends BaseSecurityConfig {
    
    private final JwtTokenProvider tokenProvider;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    // 公开路径列表
    private static final String[] PERMIT_ALL_PATHS = {
        "/auth/login", "/auth/register", "/auth/refreshToken", 
        "/error", "/actuator/**", "/favicon.ico"
    };
    
    /**
     * 配置密码编码器，覆盖父类方法
     * 
     * 注意：此Bean只有在缺少passwordEncoder Bean时才会创建
     * 在API模块中，会由PasswordEncoderConfig提供主要的passwordEncoder
     */
    @Bean("authPasswordEncoder")
    @Primary
    @ConditionalOnMissingBean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 配置认证管理器
     */
    @Bean("authAuthenticationManager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    /**
     * 配置JWT认证过滤器
     */
    // @Bean("jwtAuthFilter") // Temporarily disable the creation of this specific filter bean
    @Bean("jwtAuthFilter")
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider);
    }
    
    /**
     * 配置安全过滤链，覆盖父类方法
     */
    // @Bean("authFilterChain") // Temporarily disable this bean
    @Bean("authFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(request -> {
                    String uri = request.getRequestURI();
                    // 检查是否是API文档路径
                    for (String pattern : SecurityConstants.API_DOC_PATHS) {
                        if (pathMatcher.match(pattern, uri)) {
                            return true;
                        }
                    }
                    // 检查是否是公开路径
                    for (String pattern : PERMIT_ALL_PATHS) {
                        if (pathMatcher.match(pattern, uri)) {
                            return true;
                        }
                    }
                    return false;
                }).permitAll()
                .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form
                .loginProcessingUrl("/auth/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"code\":401,\"message\":\"未授权访问\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\"code\":403,\"message\":\"访问被拒绝\"}");
                })
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}

