package com.lawfirm.auth.config;

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
import lombok.extern.slf4j.Slf4j;

/**
 * 开发环境简单认证配置
 * <p>
 * 用于开发环境提供内存中的测试用户
 * </p>
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(70)  // 高优先级，确保在其他安全配置之前加载
@ConditionalOnProperty(name = "dev.auth.simplified-security", havingValue = "true")
public class DevAuthConfig {

    /**
     * 密码编码器
     */
    @Bean("simplePasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        log.info("初始化开发环境简单密码编码器");
        return new BCryptPasswordEncoder();
    }

    /**
     * 提供内存中的用户详情服务
     */
    @Bean("inMemoryUserDetailsManager")
    public UserDetailsService userDetailsService() {
        log.info("初始化开发环境内存用户服务");
        
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
                
        UserDetails lawyer = User.builder()
                .username("lawyer")
                .password("$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2") // admin123的BCrypt加密
                .roles("LAWYER")
                .build();

        log.info("创建了3个开发环境测试用户: admin, user, lawyer");
        return new InMemoryUserDetailsManager(admin, user, lawyer);
    }
} 