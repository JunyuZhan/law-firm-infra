package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * API文档安全配置类
 * 专门处理API文档相关的安全配置，确保API文档路径不受安全拦截
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(-110) // 确保这个配置比DisableSecurityConfig的优先级更高
public class ApiDocSecurityConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 创建API文档安全过滤链，明确放行所有API文档相关的路径
     */
    @Bean
    @Order(-110) // 确保这个过滤链比其他所有过滤链的优先级更高
    public SecurityFilterChain apiDocSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置API文档安全过滤链 - 最高优先级，确保所有API文档路径可访问");
        
        // 处理上下文路径
        String pathPrefix = "";
        if (contextPath != null && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        log.info("API文档上下文路径: {}", pathPrefix);
        
        // 创建匹配所有API文档相关路径的RequestMatcher
        OrRequestMatcher apiDocsMatcher = new OrRequestMatcher(
            // 带上下文路径的匹配
            new AntPathRequestMatcher(pathPrefix + "/doc.html"),
            new AntPathRequestMatcher(pathPrefix + "/doc.html/**"),
            new AntPathRequestMatcher(pathPrefix + "/swagger-ui.html"),
            new AntPathRequestMatcher(pathPrefix + "/swagger-ui/**"),
            new AntPathRequestMatcher(pathPrefix + "/v3/api-docs/**"),
            new AntPathRequestMatcher(pathPrefix + "/swagger-resources/**"),
            new AntPathRequestMatcher(pathPrefix + "/webjars/**"),
            new AntPathRequestMatcher(pathPrefix + "/knife4j/**"),
            new AntPathRequestMatcher(pathPrefix + "/v3/api-docs-ext/**"),
            
            // 不带上下文路径的匹配(作为备用)
            new AntPathRequestMatcher("/doc.html"),
            new AntPathRequestMatcher("/doc.html/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/webjars/**"),
            new AntPathRequestMatcher("/knife4j/**"),
            new AntPathRequestMatcher("/v3/api-docs-ext/**")
        );
        
        return http
            .securityMatcher(apiDocsMatcher) // 只对API文档路径应用此过滤链
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .sessionManagement(session -> session.disable())
            .build();
    }
} 