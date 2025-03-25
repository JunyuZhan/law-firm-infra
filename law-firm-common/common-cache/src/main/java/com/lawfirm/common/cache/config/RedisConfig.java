package com.lawfirm.common.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Redis配置类 - 兼容旧版引用
 * 
 * 此类仅用于兼容旧的引用，实际功能已移至RedisTemplateConfig
 * 这是一个临时过渡方案，后续应统一使用RedisTemplateConfig
 */
@Configuration("cacheRedisConfig")
@Import(RedisTemplateConfig.class)
public class RedisConfig {
    // 空实现，功能已移至RedisTemplateConfig
} 