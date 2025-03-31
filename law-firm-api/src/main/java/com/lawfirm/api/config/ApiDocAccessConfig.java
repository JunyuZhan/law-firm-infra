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

import lombok.extern.slf4j.Slf4j;

/**
 * API文档无障碍访问配置类
 * <p>
 * 使用最高优先级(-1000)确保API文档可以无障碍访问，覆盖所有其他安全配置
 * </p>
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(-1000) // 超高优先级，确保覆盖所有其他安全配置
public class ApiDocAccessConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 创建API文档无障碍安全过滤链
     * <p>
     * 明确放行所有API文档相关的路径，禁用一切可能的拦截
     * </p>
     */
    @Bean(name = "masterApiDocAccessFilterChain")
    @Primary // 声明为主要Bean，避免被其他同名Bean覆盖
    @Order(-1000) // 超高优先级，确保覆盖所有其他安全过滤链
    public SecurityFilterChain masterApiDocAccessFilterChain(HttpSecurity http) throws Exception {
        log.info("⭐⭐⭐ 配置主API文档无障碍访问过滤链，最高优先级-1000，确保绝对可访问性，上下文路径: {} ⭐⭐⭐", contextPath);
        
        // 规范化上下文路径
        String pathPrefix = "";
        if (contextPath != null && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        
        // 创建匹配所有API文档相关路径的请求匹配器
        RequestMatcher apiDocsMatcher = new OrRequestMatcher(
            // Knife4j相关路径
            new AntPathRequestMatcher(pathPrefix + "/doc.html"),
            new AntPathRequestMatcher(pathPrefix + "/doc.html/**"),
            
            // Swagger UI相关路径
            new AntPathRequestMatcher(pathPrefix + "/swagger-ui.html"),
            new AntPathRequestMatcher(pathPrefix + "/swagger-ui/**"),
            
            // OpenAPI规范相关路径
            new AntPathRequestMatcher(pathPrefix + "/v3/api-docs"),
            new AntPathRequestMatcher(pathPrefix + "/v3/api-docs/**"),
            new AntPathRequestMatcher(pathPrefix + "/v3/api-docs-ext"),
            new AntPathRequestMatcher(pathPrefix + "/v3/api-docs-ext/**"),
            
            // 通用资源路径
            new AntPathRequestMatcher(pathPrefix + "/swagger-resources/**"),
            new AntPathRequestMatcher(pathPrefix + "/webjars/**"),
            new AntPathRequestMatcher(pathPrefix + "/knife4j/**"),
            
            // 配置相关路径
            new AntPathRequestMatcher(pathPrefix + "/configuration/ui"),
            new AntPathRequestMatcher(pathPrefix + "/configuration/security"),
            
            // 其他可能的文档路径
            new AntPathRequestMatcher(pathPrefix + "/api-docs"),
            new AntPathRequestMatcher(pathPrefix + "/api-docs/**"),
            new AntPathRequestMatcher(pathPrefix + "/v2/api-docs"),
            new AntPathRequestMatcher(pathPrefix + "/v2/api-docs/**"),
            new AntPathRequestMatcher(pathPrefix + "/swagger-config/**"),
            
            // 静态资源
            new AntPathRequestMatcher(pathPrefix + "/favicon.ico")
        );
        
        return http
            .securityMatcher(apiDocsMatcher) // 只对API文档路径应用此过滤链
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .anonymous(anon -> anon.disable())
            .sessionManagement(session -> session.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .build();
    }
} 