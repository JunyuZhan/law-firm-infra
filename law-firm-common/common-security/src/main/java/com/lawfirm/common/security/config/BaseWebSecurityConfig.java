package com.lawfirm.common.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 基础Web安全配置类
 * 提供通用的安全配置，可被各模块继承和扩展
 */
public class BaseWebSecurityConfig {

    /**
     * Web安全过滤器链配置方法
     * 
     * 使用条件注解，仅在缺少webSecurityFilterChain Bean时创建
     * 避免与模块中定义的Bean冲突
     */
    @Bean("webSecurityFilterChain")
    @ConditionalOnMissingBean(name = "webSecurityFilterChain")
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 放行API文档路径
                .requestMatchers("/doc.html", "/doc.html/**").permitAll()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/knife4j/**").permitAll()
                // 其他请求需要认证
                .anyRequest().authenticated()
            );
            
        return http.build();
    }
} 