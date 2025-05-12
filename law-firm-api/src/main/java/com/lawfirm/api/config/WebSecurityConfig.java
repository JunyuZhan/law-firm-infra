package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Web安全配置 - 只处理公共资源访问
 * 与系统自带的安全配置共存
 */
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "law-firm.common.security.enabled", havingValue = "true", matchIfMissing = true)
public class WebSecurityConfig {

    /**
     * 配置安全过滤器链 - 只处理公共路径
     */
    @Bean
    @Order(1)
    public SecurityFilterChain publicResourcesSecurityFilterChain(HttpSecurity http) throws Exception {
        // 仅配置文档和静态资源路径
        return http
            .securityMatcher("/", "/api", "/error", "/swagger-ui.html", "/swagger-ui/**", 
                           "/v3/api-docs/**", "/assets/**", "/css/**", "/js/**", "/images/**", "/webjars/**")
            .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
            .csrf(csrf -> csrf.ignoringRequestMatchers("/**"))
            .build();
    }
} 