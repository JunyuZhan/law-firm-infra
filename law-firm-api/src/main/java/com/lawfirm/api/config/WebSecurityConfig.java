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
     * 配置安全过滤器链 - 处理公共路径
     */
    @Bean
    @Order(1)
    public SecurityFilterChain publicResourcesSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置公共路径安全访问策略");
        
        return http
            // 匹配公共资源路径和API文档路径
            .securityMatcher(
                "/doc.html", "/swagger-ui.html", "/swagger-ui/**", 
                "/v3/api-docs/**", "/webjars/**", "/swagger-resources/**",
                "/swagger-resources", "/swagger-config", "/v3/api-docs/swagger-config", 
                "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security"
            )
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
                .cacheControl(cache -> cache.disable()))
            .build();
    }
    
    /**
     * 全局默认安全过滤器链 - 处理其他所有路径
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置默认安全过滤器链");
        
        return http
            .authorizeHttpRequests(authorize -> authorize
                // 放行所有公共资源路径
                .requestMatchers(SecurityConstants.PUBLIC_RESOURCE_PATHS).permitAll()
                // 临时允许所有访问，解决400错误
                .requestMatchers("/**").permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers
                .cacheControl(cache -> cache.disable()))
            .build();
    }
} 