package com.lawfirm.api.config.dev;

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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Profile;
import lombok.extern.slf4j.Slf4j;

/**
 * 开发环境安全过滤器配置
 * <p>
 * 用于在开发环境中简化安全配置，允许所有请求，避免因数据库表缺失导致的认证失败
 * </p>
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(70)  // 提高优先级，确保它在所有安全配置之前执行
@ConditionalOnProperty(name = "dev.auth.simplified-security", havingValue = "true")
@Profile({"dev", "dev-mysql"}) // 添加默认的dev环境
public class DevSecurityFilterConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 开发环境简化安全过滤链
     * <p>
     * 允许所有请求，禁用CSRF保护
     * </p>
     * 
     * @param http HttpSecurity构建器
     * @return 安全过滤链
     * @throws Exception 配置异常
     */
    @Bean(name = "devSecurityFilterChain")
    @Order(50) // 提高优先级
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置开发环境简化安全过滤链 - 最高优先级，允许所有请求，上下文路径: {}", contextPath);
        
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            // 禁用认证请求
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            // 禁用表单登录
            .formLogin(form -> form.disable())
            // 禁用基本认证
            .httpBasic(basic -> basic.disable())
            // 禁用登出
            .logout(logout -> logout.disable())
            // 禁用匿名过滤器
            .anonymous(anon -> anon.disable())
            // 禁用会话管理
            .sessionManagement(session -> session.disable())
            .build();
    }
    
    /**
     * API文档专用安全过滤链(开发环境专用)
     * 
     * 注意：修改了方法名以避免与ApiDocSecurityConfig中的Bean冲突
     */
    @Bean(name = "devApiDocSecurityFilterChain")
    @Order(40) // 比常规过滤链更高优先级
    public SecurityFilterChain devApiDocSecurityFilterChain(HttpSecurity http) throws Exception {
        // API文档相关的路径，这里不需要添加上下文路径前缀，Spring Security会自动处理
        String[] docPaths = {
            // Swagger相关路径
            "/doc.html", "/doc.html/**", "/doc/**",
            "/swagger-ui.html", "/swagger-ui/**", 
            "/v3/api-docs/**", "/swagger-resources/**",
            "/webjars/**", "/swagger-config/**",
            "/api-docs/**", "/api-docs",
            "/v2/api-docs/**",
            
            // Knife4j专用路径
            "/favicon.ico", 
            "/v3/api-docs-ext/**",
            "/configuration/ui",
            "/configuration/security",
            
            // 新增Knife4j路径
            "/knife4j/**",
            "/markdown/**"
        };
        
        log.info("配置API文档专用安全过滤链(开发环境) - 确保文档可匿名访问 - 上下文路径: {}", contextPath);
        
        // 创建包含上下文路径的完整路径
        http
            .securityMatcher(docPaths)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable());
            // 默认情况下，匿名访问是启用的，不需要特别配置
        
        return http.build();
    }
    
    /**
     * 禁用方法级别安全检查
     * <p>
     * 用于开发测试环境
     * </p>
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