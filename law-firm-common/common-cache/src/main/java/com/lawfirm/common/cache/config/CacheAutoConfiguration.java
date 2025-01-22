package com.lawfirm.common.cache.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 缓存自动配置类
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.redis", name = "host")
@ComponentScan(basePackages = "com.lawfirm.common.cache")
@Import({RedisConfig.class, CacheConfig.class})
public class CacheAutoConfiguration {
} 