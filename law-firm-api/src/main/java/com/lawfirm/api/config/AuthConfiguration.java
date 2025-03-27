package com.lawfirm.api.config;

import com.lawfirm.auth.config.AuthAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * API模块认证配置类
 * 导入Auth模块的自动配置类，确保认证相关服务可用
 */
@Configuration
@Import(AuthAutoConfiguration.class)
public class AuthConfiguration {
    // 导入AuthAutoConfiguration类已经足够，不需要其他配置
} 