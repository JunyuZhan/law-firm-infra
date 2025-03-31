package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

/**
 * 禁用安全配置类
 * <p>
 * 用于在开发阶段临时禁用安全校验，允许所有请求通过
 * </p>
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(-90) // 低于API文档安全配置的优先级
public class DisableSecurityConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 配置禁用安全的过滤链
     */
    @Bean("disableSecurityFilterChain")
    @Primary
    @Order(-90) // 低于API文档安全配置的优先级
    public SecurityFilterChain disableSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置全局禁用安全过滤链，允许所有非API文档请求无需认证，上下文路径: {}", contextPath);
        
        // 处理上下文路径
        String pathPrefix = "";
        if (contextPath != null && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        
        return http
            // 除了API文档路径外的所有请求
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .anonymous(anon -> anon.disable())
            .sessionManagement(session -> session.disable())
            .build();
    }
} 