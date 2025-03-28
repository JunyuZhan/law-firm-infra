package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Value;

/**
 * 开发环境安全配置
 * 简化安全规则，便于开发测试
 */
@Configuration
@EnableWebSecurity
@Order(80)  // 给予较高优先级，确保它在大多数安全配置之前执行
@ConditionalOnProperty(name = "dev.auth.simplified-security", havingValue = "true")
public class DevSecurityConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 开发环境下简化的安全配置
     * 禁用大部分安全特性，仅提供最基本的安全控制
     */
    @Bean
    @Primary  // 确保此SecurityFilterChain是主要的
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        // 禁用 CSRF
        http.csrf(AbstractHttpConfigurer::disable);
        
        // 禁用 CORS
        http.cors(AbstractHttpConfigurer::disable);
        
        // 设置会话管理
        http.sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );
        
        // 定义白名单路径，不需要认证
        String[] AUTH_WHITELIST = {
            // API文档相关路径
            contextPath + "/doc.html",
            contextPath + "/v3/api-docs/**",
            contextPath + "/swagger-resources/**",
            contextPath + "/swagger-ui/**",
            contextPath + "/webjars/**",
            
            // 登录相关
            contextPath + "/login",
            contextPath + "/logout",
            contextPath + "/register",
            
            // 其他公开路径
            contextPath + "/direct/**",
            contextPath + "/auth/**",
            contextPath + "/actuator/**",
            
            // 所有静态资源
            contextPath + "/*.html",
            contextPath + "/*.js",
            contextPath + "/*.css",
            contextPath + "/*.ico",
            contextPath + "/*.png"
        };
        
        // 配置请求授权
        http.authorizeHttpRequests(authorize -> {
            // 白名单路径 - 全部放行
            for (String path : AUTH_WHITELIST) {
                authorize.requestMatchers(AntPathRequestMatcher.antMatcher(path)).permitAll();
            }
            
            // 开发环境下允许所有请求通过，便于测试
            authorize.anyRequest().permitAll();
        });
        
        // 配置头部
        http.headers(headers -> 
            headers.frameOptions(frameOptions -> frameOptions.sameOrigin())  // 允许同源iframe
        );
        
        // 表单登录配置
        http.formLogin(form -> 
            form.loginPage(contextPath + "/login")
                .loginProcessingUrl(contextPath + "/login")
                .defaultSuccessUrl(contextPath + "/index")
                .permitAll()
        );
        
        return http.build();
    }
    
    /**
     * 禁用方法级别安全检查
     * 用于开发测试环境
     */
    @Configuration
    @Order(75)  // 高优先级
    @ConditionalOnProperty(name = "method.security.enabled", havingValue = "false")
    public static class DisableMethodSecurity {
        // 这个内部类的存在会使@EnableMethodSecurity失效
        // 不需要额外的Bean方法
    }
    
    /**
     * 当method.security.enabled=true时启用方法级别安全
     */
    @Configuration
    @EnableMethodSecurity
    @Order(90)  // 较低优先级
    @ConditionalOnProperty(name = "method.security.enabled", havingValue = "true", matchIfMissing = true)
    public static class EnableMethodSecurityConfig {
        // 这个内部类启用方法级别安全
    }
} 