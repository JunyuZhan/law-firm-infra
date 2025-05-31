package com.lawfirm.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.auth.security.filter.JsonLoginFilter;
import com.lawfirm.auth.security.filter.JwtAuthenticationFilter;
import com.lawfirm.auth.security.handler.LoginFailureHandler;
import com.lawfirm.auth.security.handler.LoginSuccessHandler;
import com.lawfirm.auth.security.provider.JwtTokenProvider;
import com.lawfirm.common.security.config.BaseSecurityConfig;
import com.lawfirm.common.security.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证模块安全配置类
 * <p>
 * 提供认证授权相关的安全配置，包括:
 * - JWT认证过滤器
 * - 密码编码器
 * - 认证管理器
 * - 安全过滤链
 * </p>
 * 
 * 注意: 该配置类优先级高于通用安全配置，Bean名称唯一，避免与其他安全配置冲突
 * 只有在lawfirm.database.enabled=true时才启用完整功能
 */
@Slf4j
@Configuration("authSecurityConfig")
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(90)  // 确保此配置在通用安全配置之前加载
@ConditionalOnProperty(name = "law-firm.database.enabled", havingValue = "true", matchIfMissing = true)
public class SecurityConfig extends BaseSecurityConfig {
    
    @Autowired
    @Qualifier("auth_jwtTokenProvider")
    private JwtTokenProvider tokenProvider;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    @Qualifier("commonWebObjectMapper")
    private ObjectMapper objectMapper;
    
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    private AuthenticationManager authenticationManager;
    
    @Autowired
    public void setAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }
    
    // 公开路径列表
    private static final String[] PERMIT_ALL_PATHS = {
        "/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/auth/refreshToken", 
        "/error", "/actuator/**", "/favicon.ico"
    };
    
    /**
     * 配置密码编码器，覆盖父类方法
     * 
     * 注意：此Bean只有在缺少passwordEncoder Bean时才会创建
     * 在API模块中，会由PasswordEncoderConfig提供主要的passwordEncoder
     */
    @Bean("authPasswordEncoder")
    @Primary
    @ConditionalOnMissingBean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        log.info("创建密码编码器");
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 配置认证管理器
     */
    @Bean("authAuthenticationManager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        log.info("创建认证管理器");
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    /**
     * 配置JWT认证过滤器
     */
    @Bean("jwtAuthFilter")
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        log.info("创建JWT认证过滤器");
        return new JwtAuthenticationFilter(tokenProvider);
    }
    
    /**
     * 配置JSON登录过滤器
     * 使用限定的ObjectMapper，避免依赖注入冲突
     */
    @Bean("jsonLoginFilter")
    public JsonLoginFilter jsonLoginFilter() {
        log.info("创建JSON登录过滤器");
        JsonLoginFilter filter = JsonLoginFilter.create("/api/v1/auth/login", authenticationManager, objectMapper);
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(loginFailureHandler);
        return filter;
    }
    
    /**
     * 配置安全过滤链，覆盖父类方法
     */
    @Bean(name = "authSecurityFilterChain")
    public SecurityFilterChain baseSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置认证模块安全过滤链 - 自定义配置");
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(request -> {
                    String uri = request.getRequestURI();
                    // 检查是否是公共资源路径
                    for (String pattern : SecurityConstants.PUBLIC_RESOURCE_PATHS) {
                        if (pathMatcher.match(pattern, uri)) {
                            return true;
                        }
                    }
                    // 检查是否是公开路径
                    for (String pattern : PERMIT_ALL_PATHS) {
                        if (pathMatcher.match(pattern, uri)) {
                            return true;
                        }
                    }
                    return false;
                }).permitAll()
                .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable()) // 禁用表单登录，使用JsonLoginFilter处理JSON格式的登录请求
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"code\":401,\"message\":\"未授权访问\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\"code\":403,\"message\":\"访问被拒绝\"}");
                })
            )
            .addFilterBefore(jsonLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}

