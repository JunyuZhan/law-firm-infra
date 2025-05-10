package com.lawfirm.common.cache.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 应用缓存配置类
 * 
 * 职责：
 * 1. 提供应用层使用的缓存管理器
 * 2. 作为上层模块使用的缓存设施
 */
@Configuration("commonAppCacheConfig")
@EnableCaching
@Order(20) // 在基础缓存配置之后加载
public class AppCacheConfig {

    private static final Logger log = LoggerFactory.getLogger(AppCacheConfig.class);

    /**
     * 提供应用缓存属性配置
     */
    @Bean(name = "commonAppCacheProperties")
    @ConditionalOnMissingBean(name = "commonAppCacheProperties")
    public CacheProperties commonAppCacheProperties() {
        log.info("初始化应用缓存配置");
        CacheProperties props = new CacheProperties();
        props.setEnabled(true);
        props.setType(CacheProperties.CacheType.LOCAL); // 默认使用本地缓存
        props.setExpiration(30); // 过期时间30分钟
        props.setRefreshTime(10); // 刷新时间10分钟
        return props;
    }
    
    /**
     * 提供Redis缓存管理器，用于应用层
     * 仅在配置了Redis时创建
     */
    @Bean("commonAppCacheManager")
    @Primary
    @ConditionalOnProperty(name = "spring.data.redis.host")
    @ConditionalOnMissingBean(name = "cacheManager")
    public CacheManager commonAppCacheManager(RedisConnectionFactory connectionFactory) {
        log.info("初始化应用层Redis缓存管理器");
        
        // 默认的Redis缓存配置
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(2))  // 默认2小时过期
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
                )
                .disableCachingNullValues();  // 不缓存空值
        
        log.info("应用缓存默认过期时间: 2小时");
        
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .build();
    }
    
    /**
     * 提供备用Redis缓存管理器
     * 仅在未被其他Bean覆盖且配置了Redis时创建
     */
    @Bean("commonFallbackRedisCacheManager")
    @ConditionalOnProperty(name = "spring.data.redis.enabled", havingValue = "true", matchIfMissing = false)
    @ConditionalOnMissingBean(name = {"commonAppRedisCacheManager", "commonAppCacheManager"})
    public CacheManager fallbackRedisCacheManager(RedisConnectionFactory connectionFactory) {
        log.info("初始化备用Redis缓存管理器");
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