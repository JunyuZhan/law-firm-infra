package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.extern.slf4j.Slf4j;

/**
 * 安全配置禁用类
 * 用于开发环境中彻底禁用Spring Security，简化开发调试
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class DisableSecurityConfig {
    
    /**
     * 全局禁用安全过滤链
     */
    @Bean
    @Primary
    @Order(-100) // 绝对最高优先级
    public SecurityFilterChain disableSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置全局禁用安全过滤链 - 允许所有请求无需认证");
        
        return http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable) 
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .sessionManagement(session -> session.disable())
            .build();
    }
} 