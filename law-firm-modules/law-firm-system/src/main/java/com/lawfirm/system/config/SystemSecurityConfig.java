package com.lawfirm.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.lawfirm.system.config.SystemModuleConfig.SecurityProperties;

/**
 * 系统管理模块安全配置
 */
@Configuration
@EnableWebSecurity
public class SystemSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 配置安全过滤链
     * 
     * 使用唯一Bean名称"systemSecurityFilterChain"，避免与auth模块的securityFilterChain冲突
     */
    @Bean("systemSecurityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 获取IP白名单
        String ipWhitelist = securityProperties.getIpWhitelist();
        
        http
            .authorizeHttpRequests(authorize -> authorize
                // 系统管理模块的所有请求都需要ADMIN角色
                .requestMatchers("/system/**").hasRole(securityProperties.getAdminRole().replace("ROLE_", ""))
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()); // 禁用CSRF，因为是API接口
        
        return http.build();
    }
} 