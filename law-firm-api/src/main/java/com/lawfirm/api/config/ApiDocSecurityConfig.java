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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * API文档安全配置类
 * <p>
 * 确保API文档相关的路径可以无需认证访问，优先级高于其他安全配置
 * </p>
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(-110) // 确保这个配置比所有其他安全配置优先级更高
public class ApiDocSecurityConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 配置API文档专用安全过滤链
     */
    @Bean("apiDocSecurityChain")
    @Order(-110) // 确保这个过滤链比其他所有过滤链的优先级更高
    public SecurityFilterChain apiDocSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置API文档安全过滤链，确保所有API文档路径可访问，上下文路径: {}", contextPath);
        
        // 处理上下文路径
        String pathPrefix = "";
        if (contextPath != null && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        
        // API文档相关路径列表
        String[] apiDocPaths = {
            "/doc.html", "/doc.html/**",
            "/swagger-ui.html", "/swagger-ui/**",
            "/v3/api-docs", "/v3/api-docs/**", "/v3/api-docs-ext", "/v3/api-docs-ext/**",
            "/swagger-resources/**", "/webjars/**", "/knife4j/**",
            "/api-docs/**", "/configuration/ui", "/configuration/security",
            "/swagger-config/**", "/favicon.ico"
        };
        
        // 创建匹配器
        List<RequestMatcher> matchers = new ArrayList<>();
        for (String path : apiDocPaths) {
            matchers.add(new AntPathRequestMatcher(pathPrefix + path));
        }
        
        OrRequestMatcher matcher = new OrRequestMatcher(matchers);
        
        return http
            .securityMatcher(matcher)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .sessionManagement(session -> session.disable())
            .build();
    }
} 