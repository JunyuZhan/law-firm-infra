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
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.time.Duration;

/**
 * 财务模块缓存配置
 */
@Configuration("financeCacheConfig")
@EnableCaching
@ConditionalOnProperty(name = "spring.data.redis.enabled", havingValue = "true", matchIfMissing = false)
public class CacheConfig {
    
    // 默认缓存过期时间（1小时）
    private static final long DEFAULT_EXPIRE_TIME = 3600L;
    
    @Bean("financeCacheManager")
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, FinanceConfig financeConfig) {
        // 处理配置为null的情况
        long expireTime = DEFAULT_EXPIRE_TIME;
        if (financeConfig.getCache() != null) {
            expireTime = financeConfig.getCache().getExpireTime();
        }
        
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(expireTime))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();
        
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
} 