package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
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
@Order(80)  // 给予较高优先级，确保它在大多数安全配置之前执行
@ConditionalOnProperty(name = "dev.auth.simplified-security", havingValue = "true")
public class DevSecurityConfig {

    /**
     * 开发环境下简化的安全配置
     * 禁用大部分安全特性，仅提供最基本的安全控制
     */
    @Bean
    @Primary  // 确保此SecurityFilterChain是主要的
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)  // 禁用CSRF
            .cors(AbstractHttpConfigurer::disable)  // 禁用CORS
            .authorizeHttpRequests(authorize -> authorize
                // 明确允许/direct/**路径的所有请求
                .requestMatchers("/direct/**").permitAll()
                // 允许API文档访问
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/webjars/**",
                    "/doc.html",
                    "/swagger-resources/**"
                ).permitAll()
                // 允许其他所有请求
                .anyRequest().permitAll()  // 允许所有请求
            )
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())  // 允许同源iframe
            )
            .formLogin(form -> form
                .loginPage("/api/login")
                .loginProcessingUrl("/api/login")
                .defaultSuccessUrl("/api/index")
                .permitAll()
            )
            .build();
    }
    
    /**
     * 禁用方法级别安全检查
     * 用于开发测试环境
     */
    @Configuration
    @Order(75)  // 高优先级
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
    @Order(90)  // 较低优先级
    @ConditionalOnProperty(name = "method.security.enabled", havingValue = "true", matchIfMissing = true)
    public static class EnableMethodSecurityConfig {
        // 这个内部类启用方法级别安全
    }
} 