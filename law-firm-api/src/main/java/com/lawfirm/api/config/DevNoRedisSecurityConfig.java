package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 开发环境无Redis的安全配置
 * 这个配置类仅在dev-noredis环境下激活
 * 提供一个简化的安全配置，避免循环依赖问题
 */
@Configuration
@EnableWebSecurity
@Profile("dev-noredis")
@Order(-1) // 确保这个配置优先于其他安全配置
@Primary // 标记为主要的安全配置
public class DevNoRedisSecurityConfig {

    @Bean
    @Primary // 确保这个Bean是主要的SecurityFilterChain
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 开发环境允许所有请求，简化配置避免循环依赖
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()
            )
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .sessionManagement(session -> session.disable());
        
        return http.build();
    }
} 