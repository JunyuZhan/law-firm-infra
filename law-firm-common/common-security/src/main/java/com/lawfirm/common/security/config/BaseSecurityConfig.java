package com.lawfirm.common.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 基础安全配置类
 * 提供通用的安全配置，可被各模块继承和扩展
 */
public class BaseSecurityConfig {

    /**
     * 密码编码器
     * 
     * 使用条件注解，仅在缺少passwordEncoder Bean时创建
     * 避免与API模块中定义的Bean冲突
     */
    @Bean("basePasswordEncoder")
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder basePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 安全过滤器链配置方法，子类可以覆盖此方法提供自定义配置
     * 
     * 使用条件注解，仅在缺少securityFilterChain Bean时创建
     * 避免与模块中定义的Bean冲突
     */
    @Bean("baseSecurityFilterChain")
    @ConditionalOnMissingBean(name = {"securityFilterChain", "apiDocSecurityFilterChain"})
    public SecurityFilterChain baseSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> {})
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/auth/**").permitAll()
                // Knife4j 和 Swagger 相关路径
                .requestMatchers("/doc.html", "/doc.html/**").permitAll()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/knife4j/**").permitAll()
                .requestMatchers("/v3/api-docs-ext/**").permitAll()
                .requestMatchers("/swagger-config/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            );
                
        return http.build();
    }
} 