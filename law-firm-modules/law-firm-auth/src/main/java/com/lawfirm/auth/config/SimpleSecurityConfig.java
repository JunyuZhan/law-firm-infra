package com.lawfirm.auth.config;

import com.lawfirm.common.security.config.BaseSecurityConfig;
import com.lawfirm.common.security.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 简单安全配置类
 * 当数据库功能禁用时使用的简化版安全配置
 * 允许所有访问，适用于开发环境
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(90)
@ConditionalOnProperty(name = "law-firm.database.enabled", havingValue = "false")
public class SimpleSecurityConfig extends BaseSecurityConfig {
    
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    // 公开路径列表 - 在无数据库模式下，所有路径都允许访问
    private static final String[] PERMIT_ALL_PATHS = {
        "/**", // 允许所有路径，适用于开发环境
        "/auth/login", "/auth/register", "/auth/refreshToken", 
        "/error", "/actuator/**", "/favicon.ico"
    };
    
    /**
     * 配置密码编码器
     */
    @Bean("simplePasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        log.info("创建简单密码编码器");
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 配置安全过滤链
     * 简化版，适用于无数据库模式的开发环境
     */
    @Bean("simpleFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置简单安全过滤链 - 适用于开发环境（无数据库模式）");
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // 允许所有请求
            )
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
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
            .build();
    }
} 