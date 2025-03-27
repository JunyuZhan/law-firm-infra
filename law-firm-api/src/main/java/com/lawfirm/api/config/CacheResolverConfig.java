package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 缓存解析器配置
 * 用于解决多个CacheManager导致的冲突问题
 */
@Configuration
@EnableCaching
public class CacheResolverConfig implements CachingConfigurer {

    private final CacheManager commonCacheManager;

    public CacheResolverConfig(@Qualifier("commonCacheManager") CacheManager commonCacheManager) {
        this.commonCacheManager = commonCacheManager;
    }

    /**
     * 配置默认的缓存解析器
     * 指定使用commonCacheManager作为默认缓存管理器
     */
    @Bean
    @Primary
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(commonCacheManager);
    }

    @Override
    public KeyGenerator keyGenerator() {
        return null;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
} 