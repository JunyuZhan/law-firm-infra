package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码编码器配置
 * <p>
 * 解决多个PasswordEncoder Bean冲突问题
 * </p>
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * 提供API模块专用的PasswordEncoder实现
     * <p>
     * 使用明确不同的Bean名称，避免与其他模块冲突
     * </p>
     */
    @Bean("apiPasswordEncoder")
    @Primary
    public PasswordEncoder apiPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 