package com.lawfirm.finance.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 财务模块缓存配置
 */
@Configuration("financeCacheConfig")
@EnableCaching
public class CacheConfig {
    
    @Bean("financeCacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, FinanceConfig financeConfig) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(financeConfig.getCache().getExpireTime()))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();
        
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
} 