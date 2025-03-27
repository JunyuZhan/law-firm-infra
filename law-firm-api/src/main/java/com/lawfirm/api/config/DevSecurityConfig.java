package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 开发环境安全配置
 * 简化安全规则，便于开发测试
 */
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "dev.auth.simplified-security", havingValue = "true")
public class DevSecurityConfig {

    /**
     * 开发环境下简化的安全配置
     * 禁用大部分安全特性，仅提供最基本的安全控制
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)  // 禁用CSRF
            .cors(AbstractHttpConfigurer::disable)  // 禁用CORS
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()  // 允许所有请求
            )
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())  // 允许同源iframe
            )
            .build();
    }
    
    /**
     * 禁用方法级别安全检查
     * 用于开发测试环境
     */
    @Configuration
    @ConditionalOnProperty(name = "method.security.enabled", havingValue = "false")
    public static class DisableMethodSecurity {
        // 这个内部类的存在会使@EnableMethodSecurity失效
        // 不需要额外的Bean方法
    }
    
    /**
     * 当method.security.enabled=true时启用方法级别安全
     */
    @Configuration
    @EnableMethodSecurity
    @ConditionalOnProperty(name = "method.security.enabled", havingValue = "true", matchIfMissing = true)
    public static class EnableMethodSecurityConfig {
        // 这个内部类启用方法级别安全
    }
} 