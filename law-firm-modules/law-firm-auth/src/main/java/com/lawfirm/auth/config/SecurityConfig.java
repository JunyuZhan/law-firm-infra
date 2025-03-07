package com.lawfirm.auth.config;

import com.lawfirm.common.security.config.BaseSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Auth模块安全配置
 * 继承自common-security模块的基础配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends BaseSecurityConfig {
    // 这里保留当前SecurityConfig中特定于auth模块的方法和配置
    // 例如与认证授权、安全过滤器链相关的特定配置
}