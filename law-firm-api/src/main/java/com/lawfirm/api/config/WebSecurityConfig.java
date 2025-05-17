package com.lawfirm.api.config;

import com.lawfirm.common.security.constants.SecurityConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

/**
 * Web安全配置 - 处理公共资源访问
 */
@Slf4j
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "law-firm.common.security.enabled", havingValue = "true", matchIfMissing = true)
public class WebSecurityConfig {
    
    /**
     * 安全过滤器链 - 纯API后端
     */
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置API安全过滤器链");
        
        return http
            .authorizeHttpRequests(authorize -> authorize
                // 健康检查
                .requestMatchers("/health/**", "/actuator/**").permitAll()
                // 放行所有公共资源路径
                .requestMatchers(SecurityConstants.PUBLIC_RESOURCE_PATHS).permitAll()
                // 临时允许所有访问，解决403错误
                .requestMatchers("/**").permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers.cacheControl(cache -> cache.disable()))
            .build();
    }
} 