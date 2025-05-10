package com.lawfirm.client.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.lawfirm.client.constant.CacheConstant;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户模块缓存配置类
 */
@Configuration("clientCacheConfig")
@EnableCaching
@ConditionalOnProperty(name = "lawfirm.client.cache.enabled", havingValue = "true", matchIfMissing = false)
public class CacheConfig {

    /**
     * 配置Redis缓存管理器
     */
    @Bean("clientCacheManager")
    @ConditionalOnMissingBean(name = "commonCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 配置Redis缓存管理器
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(defaultCacheConfig())
                .withInitialCacheConfigurations(customCacheConfigs())
                .build();
        return cacheManager;
    }
    
    /**
     * 默认缓存配置
     */
    private RedisCacheConfiguration defaultCacheConfig() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // 默认1小时过期
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
    
    /**
     * 自定义缓存配置
     */
    private Map<String, RedisCacheConfiguration> customCacheConfigs() {
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        
        // 客户信息缓存配置
        configMap.put("clientCache", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheConstant.ExpireTime.CLIENT_INFO)));
        
        // 列表缓存配置
        configMap.put("listCache", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheConstant.ExpireTime.LIST)));
        
        // 分类标签缓存配置
        configMap.put("categoryCache", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheConstant.ExpireTime.CATEGORY_AND_TAG)));
        
        return configMap;
    }
    
    /**
     * 获取客户端Redis模板
     * 复用通用缓存模板，避免创建多个RedisTemplate导致冲突
     */
    @Bean("clientRedisTemplate")
    public RedisTemplate<String, Object> clientRedisTemplate(@Qualifier("cacheRedisTemplate") RedisTemplate<String, Object> cacheRedisTemplate) {
        return cacheRedisTemplate;
    }
}
