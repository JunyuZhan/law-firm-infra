package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

/**
 * 生产环境增强安全配置
 * <p>
 * 提供适用于生产环境的增强安全特性
 * 包括:
 * - CSRF保护
 * - 安全头部配置
 * - XSS防护
 * - 内容类型选项
 * - 引用策略
 * </p>
 */
@Configuration
@EnableWebSecurity
@Profile("prod")
public class SecurityEnhancementConfig {

    /**
     * 生产环境的安全过滤链配置
     */
    @Bean(name = "productionSecurityFilterChain")
    public SecurityFilterChain productionSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            // 启用CSRF保护
            .csrf(csrf -> csrf
                // 使用Cookie存储CSRF令牌
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // API文档路径禁用CSRF
                .ignoringRequestMatchers("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**")
            )
            // 配置安全头部
            .headers(headers -> headers
                // 启用内容安全策略
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:; frame-ancestors 'self'")
                )
                // 启用引用策略
                .referrerPolicy(referrer -> referrer
                    .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                )
                // XSS保护
                .xssProtection(xss -> {})
                // 内容类型选项
                .contentTypeOptions(contentType -> {})
                // 禁止在frame中显示
                .frameOptions(frame -> frame.deny())
            )
            // 会话管理
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
        
        return http.build();
    }
} 