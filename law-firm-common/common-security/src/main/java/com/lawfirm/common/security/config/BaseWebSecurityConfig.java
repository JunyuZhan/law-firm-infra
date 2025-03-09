package com.lawfirm.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Web安全配置基类
 * 提供Web安全相关的基础配置
 */
public class BaseWebSecurityConfig {

    /**
     * Web安全过滤器链配置
     */
    @Bean
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/public/**", "/resources/**").permitAll()
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
} 