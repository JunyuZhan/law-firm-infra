package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.slf4j.Slf4j;

/**
 * Knife4j专用配置类
 * 解决API文档访问安全问题
 */
@Slf4j
@Configuration("knife4jApiConfig")
@ConditionalOnProperty(name = "knife4j.enable", havingValue = "true", matchIfMissing = true)
public class Knife4jConfig {

    @Value("${knife4j.enable:true}")
    private boolean knife4jEnabled;

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

    /**
     * 配置API文档专用的安全过滤器链
     * 优先级高于其他安全配置，确保文档路径可访问
     */
    @Bean("apiDocSecurityFilterChain")
    @Order(89) // 高于WebSecurityConfig的优先级(90)
    public SecurityFilterChain apiDocSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置API文档安全过滤器链 (apiDocSecurityFilterChain) - Knife4j状态: {}", knife4jEnabled ? "启用" : "禁用");

        if (!knife4jEnabled) {
            return http.build();
        }

        return http
            .securityMatcher(API_DOCS_PATHS) // 直接使用字符串数组
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(API_DOCS_PATHS) // 直接使用字符串数组
                .permitAll()
                .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable) // 禁用CSRF
            .cors(cors -> cors.disable()) // 禁用CORS
            .build();
    }
} 