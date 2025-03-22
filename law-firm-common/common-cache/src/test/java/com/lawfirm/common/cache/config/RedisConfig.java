package com.lawfirm.common.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 仅为测试存在的空RedisConfig类
 * 因为有些测试可能通过类路径扫描引用它
 * 实际功能已移至RedisTemplateConfig
 */
@Configuration("cacheRedisConfig")
@Profile("test")
public class RedisConfig {
    // 空类，只为满足引用
} 