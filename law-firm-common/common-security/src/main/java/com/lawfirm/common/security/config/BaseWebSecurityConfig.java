package com.lawfirm.common.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 基础Web安全配置类
 * 提供通用的安全配置，可被各模块继承和扩展
 */
@Configuration("commonBaseWebSecurityConfig")
public class BaseWebSecurityConfig {

    /**
     * Web安全过滤器链配置方法
     * 
     * 使用条件注解，仅在缺少webSecurityFilterChain Bean时创建
     * 避免与模块中定义的Bean冲突
     */
    @Bean("commonWebSecurityFilterChain")
    @ConditionalOnMissingBean(name = {"webSecurityFilterChain"})
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        // 提供默认的安全配置，通常用于开发环境或简单场景
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                .requestMatchers("/actuator/**", "/health/**", "/info/**").permitAll()
                .anyRequest().authenticated());
                
        return http.build();
    }
} 