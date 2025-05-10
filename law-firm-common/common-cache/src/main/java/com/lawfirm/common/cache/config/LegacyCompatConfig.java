package com.lawfirm.common.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 向后兼容配置类
 * 
 * 职责：
 * 1. 提供向后兼容的Bean定义
 * 2. 确保系统中现有依赖不会因改名而中断
 */
@Configuration
public class LegacyCompatConfig {

    @Autowired
    private RedisTemplate<String, Object> cacheRedisTemplate;

    /**
     * 提供向后兼容的dataRedisTemplate
     * 直接转发到cacheRedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> dataRedisTemplate() {
        return cacheRedisTemplate;
    }
} 