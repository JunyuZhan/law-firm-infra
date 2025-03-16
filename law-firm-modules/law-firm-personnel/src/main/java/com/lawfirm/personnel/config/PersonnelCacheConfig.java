package com.lawfirm.personnel.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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

/**
 * 人事模块缓存配置
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "personnel.cache", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PersonnelCacheConfig {

    /**
     * 员工缓存名称
     */
    public static final String EMPLOYEE_CACHE = "personnel:employee";
    
    /**
     * 部门缓存名称
     */
    public static final String DEPARTMENT_CACHE = "personnel:department";
    
    /**
     * 职位缓存名称
     */
    public static final String POSITION_CACHE = "personnel:position";
    
    /**
     * 缓存配置
     */
    @Bean
    public CacheManager personnelCacheManager(RedisConnectionFactory redisConnectionFactory) {
        log.info("初始化人事模块缓存管理器");
        
        // 默认缓存配置
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))  // 默认过期时间1小时
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();  // 不缓存空值
        
        // 自定义不同缓存名称的配置
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // 员工缓存配置 - 过期时间1小时
        cacheConfigurations.put(EMPLOYEE_CACHE, defaultCacheConfig.entryTtl(Duration.ofHours(1)));
        
        // 部门缓存配置 - 过期时间2小时
        cacheConfigurations.put(DEPARTMENT_CACHE, defaultCacheConfig.entryTtl(Duration.ofHours(2)));
        
        // 职位缓存配置 - 过期时间2小时
        cacheConfigurations.put(POSITION_CACHE, defaultCacheConfig.entryTtl(Duration.ofHours(2)));
        
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
} 