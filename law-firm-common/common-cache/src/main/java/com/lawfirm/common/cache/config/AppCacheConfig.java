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
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 应用缓存配置类
 * <p>
 * 提供API层和业务模块使用的缓存配置和管理器
 * </p>
 */
@Configuration
@EnableCaching
public class AppCacheConfig {

    private static final Logger log = LoggerFactory.getLogger(AppCacheConfig.class);

    /**
     * 提供缓存属性Bean
     */
    @Bean(name = "appCacheProperties")
    public CacheProperties cacheProperties() {
        log.info("初始化缓存配置：默认使用本地缓存");
        CacheProperties props = new CacheProperties();
        props.setEnabled(true);
        props.setType(CacheProperties.CacheType.LOCAL); // 默认使用本地缓存
        props.setExpiration(30); // 过期时间30分钟
        props.setRefreshTime(10); // 刷新时间10分钟
        return props;
    }
    
    /**
     * 配置Redis缓存管理器
     */
    @Bean("appRedisCacheManager")
    @ConditionalOnProperty(name = "spring.data.redis.enabled", havingValue = "true", matchIfMissing = false)
    @ConditionalOnMissingBean(name = "appRedisCacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        log.info("初始化Redis缓存管理器");
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

    /**
     * 创建应用层的缓存管理器
     */
    @Bean("appCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        log.info("初始化应用层的Redis缓存管理器");
        
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
} 