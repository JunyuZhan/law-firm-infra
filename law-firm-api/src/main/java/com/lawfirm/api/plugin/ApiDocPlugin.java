package com.lawfirm.api.plugin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * API文档插件
 * <p>
 * 这是一个独立的API文档插件，集成了所有API文档相关的配置：
 * 1. 安全过滤链配置 - 确保文档路径可以访问
 * 2. MVC配置 - 添加视图控制器将根路径重定向到文档
 * 3. 编码配置 - 确保文档正确显示中文
 * </p>
 * <p>
 * 该插件设计为完全独立，不依赖于其他配置，使用最高优先级确保覆盖其他配置
 * </p>
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = "api.doc.enabled", havingValue = "true", matchIfMissing = true)
public class ApiDocPlugin implements WebMvcConfigurer {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    /**
     * API文档安全过滤链
     * <p>
     * 使用最高优先级，确保所有API文档路径可以无障碍访问
     * </p>
     */
    @Bean("apiDocFilterChain")
    @Primary
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain apiDocFilterChain(HttpSecurity http) throws Exception {
        log.info("🔥🔥🔥 配置API文档独立安全过滤链，确保文档绝对可访问 🔥🔥🔥");
        
        // 规范化上下文路径
        String pathPrefix = "";
        if (contextPath != null && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        
        // 创建所有API文档路径匹配器
        List<RequestMatcher> matchers = new ArrayList<>();
        
        // 添加所有可能的API文档路径
        String[] docPaths = {
            "/doc.html", "/doc.html/**", "/doc/**",
            "/swagger-ui.html", "/swagger-ui/**", 
            "/v3/api-docs", "/v3/api-docs/**", "/v3/api-docs-ext/**",
            "/swagger-resources/**", "/swagger-resources",
            "/swagger-config/**", "/swagger-config",
            "/webjars/**", "/webjars",
            "/knife4j/**", "/knife4j",
            "/api-docs/**", "/api-docs",
            "/v2/api-docs/**", "/v2/api-docs",
            "/configuration/ui", "/configuration/security",
            "/favicon.ico", "/markdown/**",
            // 添加根路径，确保首页可以访问
            "/"
        };
        
        // 为每个路径创建匹配器
        for (String path : docPaths) {
            matchers.add(new AntPathRequestMatcher(pathPrefix + path));
        }
        
        // 创建组合匹配器
        RequestMatcher docMatcher = new OrRequestMatcher(matchers);
        
        http
            .securityMatcher(docMatcher)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .logout(logout -> logout.disable())
            .anonymous(anon -> anon.disable())
            .sessionManagement(session -> session.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        
        return http.build();
    }
    
    /**
     * 视图控制器配置
     * <p>
     * 将根路径重定向到文档页面
     * </p>
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("🔥🔥🔥 配置API文档视图控制器，将根路径重定向到文档页面 🔥🔥🔥");
        
        // 添加根路径到文档的重定向
        registry.addViewController("/").setViewName("forward:/doc.html");
        registry.addViewController("/doc").setViewName("forward:/doc.html");
        registry.addViewController("/swagger").setViewName("forward:/swagger-ui.html");
        registry.addViewController("/api-docs").setViewName("forward:/doc.html");
    }
    
    /**
     * 字符串消息转换器
     * <p>
     * 确保API文档使用UTF-8编码
     * </p>
     */
    @Bean("apiDocStringConverter")
    @Primary
    public StringHttpMessageConverter stringConverter() {
        log.info("🔥🔥🔥 配置API文档字符串转换器，确保使用UTF-8编码 🔥🔥🔥");
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }
} 