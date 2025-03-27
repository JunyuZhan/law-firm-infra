package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 简单认证配置
 * 用于开发环境提供内存中的测试用户
 */
@Configuration
@EnableWebSecurity
@Order(70)  // 高优先级，确保在其他安全配置之前加载
@ConditionalOnProperty(name = "dev.auth.simplified-security", havingValue = "true")
public class SimpleAuthConfig {

    /**
     * 密码编码器
     */
    @Bean("simplePasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 提供内存中的用户详情服务
     */
    @Bean("inMemoryUserDetailsManager")
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2") // admin123的BCrypt加密
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password("$2a$10$lJ6SXFXzfALJTtjQsROBSeKIAO3qbLIY3EYSrY8EGS5c5YIBpCYzi") // password123的BCrypt加密
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
} 