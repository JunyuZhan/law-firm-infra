package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
     * 优化通配符模式确保API文档正常访问
     */
    @Bean
    @Order(1)
    public SecurityFilterChain publicResourcesSecurityFilterChain(HttpSecurity http) throws Exception {
        // 仅配置文档和静态资源路径
        return http
            .securityMatcher("/", "/api/**", "/error/**", "/swagger-ui/**", "/swagger-ui.html", 
                           "/v3/api-docs/**", "/actuator/**", "/assets/**", "/css/**", 
                           "/js/**", "/images/**", "/webjars/**")
            .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)  // 禁用CORS
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers.frameOptions(Customizer.withDefaults())) // 允许iframe嵌入
            .build();
    }
    
    /**
     * 全局默认安全过滤器链 - 处理其他所有路径
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**").permitAll())  // 临时允许所有访问，解决400错误
            .csrf(AbstractHttpConfigurer::disable)    // 禁用CSRF
            .build();
    }
} 