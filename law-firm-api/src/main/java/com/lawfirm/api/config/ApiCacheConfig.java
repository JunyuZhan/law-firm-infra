package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * API层缓存配置
 */
@Configuration
@EnableCaching
public class ApiCacheConfig {

    /**
     * 配置缓存管理器
     */
    @Bean("apiCacheManager")
    @Primary
    @ConditionalOnMissingBean(name = "apiCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 默认配置
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                // 设置key为String
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value为json
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不缓存null
                .disableCachingNullValues()
                // 默认缓存过期时间
                .entryTtl(Duration.ofMinutes(30));

        // 创建Redis缓存管理器
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .transactionAware()
                .build();
    }
}