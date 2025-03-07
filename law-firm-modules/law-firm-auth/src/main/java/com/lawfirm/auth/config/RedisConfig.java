package com.lawfirm.auth.config;

import com.lawfirm.common.cache.config.RedisConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Auth模块 Redis 配置
 * 继承自common-cache模块的基础配置
 */
@Configuration
public class AuthRedisConfig extends RedisConfig {
    // 父类已包含基础配置
}