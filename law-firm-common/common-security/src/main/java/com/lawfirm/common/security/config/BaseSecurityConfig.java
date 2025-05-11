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
import lombok.extern.slf4j.Slf4j;

/**
 * 基础安全配置类
 * 提供通用的安全配置，可被各模块继承和扩展
 */
@Configuration("commonBaseSecurityConfig")
@Slf4j
public class BaseSecurityConfig {

    /**
     * 密码编码器
     * 
     * 使用条件注解，仅在缺少passwordEncoder Bean时创建
     * 避免与API模块中定义的Bean冲突
     * 
     * @return BCryptPasswordEncoder实例
     */
    @Bean(name = "commonBasePasswordEncoder")
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder commonBasePasswordEncoder() {
        log.info("创建基础密码编码器");
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 安全过滤器链配置方法，子类可以覆盖此方法提供自定义配置
     * 
     * 使用条件注解，仅在缺少securityFilterChain Bean时创建
     * 避免与模块中定义的Bean冲突
     * 
     * @param http HttpSecurity实例
     * @return 配置好的SecurityFilterChain
     * @throws Exception 配置过程中可能抛出的异常
     */
    @Bean(name = "commonBaseSecurityFilterChain")
    @ConditionalOnMissingBean(name = {"securityFilterChain", "apiDocSecurityFilterChain", "authSecurityFilterChain"})
    public SecurityFilterChain commonBaseSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置基础安全过滤链 - 默认配置");
        
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error", "/actuator/**", "/favicon.ico").permitAll()
                .anyRequest().authenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
} 