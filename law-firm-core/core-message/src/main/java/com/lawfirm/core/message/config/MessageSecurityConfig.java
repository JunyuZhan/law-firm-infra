package com.lawfirm.core.message.config;

import com.lawfirm.common.security.config.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * 消息安全配置
 */
@Configuration
@EnableMethodSecurity
@Import(SecurityConfig.class)
public class MessageSecurityConfig {
    // 继承通用安全配置
} 