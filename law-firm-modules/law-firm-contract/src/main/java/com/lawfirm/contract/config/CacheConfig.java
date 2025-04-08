package com.lawfirm.contract.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 合同缓存配置类
 */
@Configuration("contractCacheConfig")
@EnableCaching
public class CacheConfig {

    /**
     * Redis缓存管理器
     */
    @Bean("contractCacheManager")
    public RedisCacheManager contractCacheManager(RedisConnectionFactory connectionFactory) {
        // 默认配置
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(2))  // 默认缓存2小时
                .prefixCacheNameWith("contract:")  // 添加缓存键前缀
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()
                        )
                );

        // 特定缓存配置
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // 合同基本信息缓存 - 缓存4小时
        cacheConfigurations.put("contract", defaultConfig.entryTtl(Duration.ofHours(4)));
        
        // 合同列表缓存 - 缓存30分钟
        cacheConfigurations.put("contractList", defaultConfig.entryTtl(Duration.ofMinutes(30)));
        
        // 合同详情缓存 - 缓存2小时
        cacheConfigurations.put("contractDetail", defaultConfig.entryTtl(Duration.ofHours(2)));
        
        // 合同统计数据缓存 - 缓存15分钟
        cacheConfigurations.put("contractStats", defaultConfig.entryTtl(Duration.ofMinutes(15)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
} 