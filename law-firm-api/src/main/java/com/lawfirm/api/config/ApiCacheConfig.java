package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * API层缓存配置
 * 为只读操作提供缓存支持，提升系统性能
 */
@Configuration("apiCacheConfig")
@EnableCaching
@Slf4j
@ConditionalOnProperty(name = "law-firm.api.cache.enabled", havingValue = "true", matchIfMissing = true)
public class ApiCacheConfig {

    @Value("${law-firm.api.cache.default-ttl:3600}")
    private long defaultTtl;
    
    @Value("${law-firm.api.cache.enabled:true}")
    private boolean cacheEnabled;
    
    /**
     * API层专用缓存管理器
     * 为不同类型的数据配置不同的过期时间
     */
    @Bean("apiCacheManager")
    public CacheManager apiCacheManager(RedisConnectionFactory connectionFactory) {
        log.info("初始化API层缓存管理器，默认TTL: {}秒", defaultTtl);
        
        // 默认缓存配置
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(defaultTtl))
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
                )
                .disableCachingNullValues()
                .prefixCacheNameWith("api:");
        
        // 特定缓存配置
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // 常用配置项
        cacheConfigurations.put("dict", defaultConfig.entryTtl(Duration.ofHours(24))); // 字典数据缓存24小时
        cacheConfigurations.put("menu", defaultConfig.entryTtl(Duration.ofHours(12))); // 菜单数据缓存12小时
        cacheConfigurations.put("user", defaultConfig.entryTtl(Duration.ofHours(2))); // 用户数据缓存2小时
        cacheConfigurations.put("role", defaultConfig.entryTtl(Duration.ofHours(6))); // 角色数据缓存6小时
        cacheConfigurations.put("permission", defaultConfig.entryTtl(Duration.ofHours(6))); // 权限数据缓存6小时
        cacheConfigurations.put("client", defaultConfig.entryTtl(Duration.ofHours(1))); // 客户数据缓存1小时
        cacheConfigurations.put("case", defaultConfig.entryTtl(Duration.ofMinutes(30))); // 案件数据缓存30分钟
        cacheConfigurations.put("contract", defaultConfig.entryTtl(Duration.ofMinutes(30))); // 合同数据缓存30分钟
        cacheConfigurations.put("document", defaultConfig.entryTtl(Duration.ofMinutes(30))); // 文档数据缓存30分钟
        cacheConfigurations.put("statistics", defaultConfig.entryTtl(Duration.ofHours(1))); // 统计数据缓存1小时
        
        // 构建缓存管理器
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations);
        
        // 如果缓存被禁用，则设置所有缓存的过期时间为0
        if (!cacheEnabled) {
            log.warn("API层缓存已禁用，所有缓存将立即过期");
            builder.cacheDefaults(defaultConfig.entryTtl(Duration.ZERO));
        }
        
        return builder.build();
    }
} 