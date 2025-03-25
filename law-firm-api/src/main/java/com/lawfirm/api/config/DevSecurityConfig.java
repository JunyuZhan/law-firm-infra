package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 开发模式安全配置
 * 当设置dev.use-redis=false时使用的简化安全配置
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ConditionalOnProperty(name = "dev.use-redis", havingValue = "false")
public class DevSecurityConfig {

    /**
     * 提供用于开发的认证管理器
     */
    @Bean
    @Primary
    public AuthenticationManager devAuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        log.info("======================================================");
        log.info("正在使用开发模式认证管理器，仅用于开发/测试环境");
        log.info("======================================================");
        return authConfig.getAuthenticationManager();
    }

    /**
     * 开发模式安全过滤器链配置
     * 允许所有端点访问，仅用于开发环境
     */
    @Bean
    @Primary
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("======================================================");
        log.info("正在使用开发模式安全配置，仅用于开发/测试环境");
        log.info("所有接口均可无认证访问，请勿在生产环境使用此配置");
        log.info("======================================================");
        
        // 禁用CSRF保护
        http.csrf(csrf -> csrf.disable())
            // 开发环境允许所有请求访问
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
} 