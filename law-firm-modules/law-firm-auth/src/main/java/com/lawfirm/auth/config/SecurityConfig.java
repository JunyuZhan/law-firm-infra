package com.lawfirm.auth.config;

import com.lawfirm.auth.security.filter.JwtAuthenticationFilter;
import com.lawfirm.auth.security.handler.LoginFailureHandler;
import com.lawfirm.auth.security.handler.LoginSuccessHandler;
import com.lawfirm.auth.security.provider.JwtTokenProvider;
import com.lawfirm.common.security.config.BaseSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
 */
@Configuration("authSecurityConfig")
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(90)  // 确保此配置在通用安全配置之前加载
public class SecurityConfig extends BaseSecurityConfig {
    
    private final JwtTokenProvider tokenProvider;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    
    /**
     * 配置密码编码器，覆盖父类方法
     * 
     * 注意：此Bean只有在缺少passwordEncoder Bean时才会创建
     * 在API模块中，会由PasswordEncoderConfig提供主要的passwordEncoder
     */
    @Bean("authPasswordEncoder")
    @ConditionalOnMissingBean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 配置认证管理器
     */
    @Bean("authAuthenticationManager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    /**
     * 配置JWT认证过滤器
     */
    @Bean("jwtAuthFilter")
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider);
    }
    
    /**
     * 配置安全过滤链，覆盖父类方法
     */
    @Bean("authFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 先配置基础安全设置
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 添加JWT过滤器
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            // 配置登录成功处理器
            .formLogin(form -> form
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
            )
            // 配置路径权限
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/register").permitAll()
                .requestMatchers("/doc.html", "/doc.html/**").permitAll()
                .requestMatchers("/webjars/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-resources/**", "/swagger-ui/**").permitAll()
                .requestMatchers("/knife4j/**").permitAll()
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}

