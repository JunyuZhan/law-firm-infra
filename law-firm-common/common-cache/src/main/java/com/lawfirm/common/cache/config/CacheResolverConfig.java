package com.lawfirm.common.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 缓存解析器配置
 * 为缓存注解提供默认的缓存管理器
 */
@Slf4j
@Configuration("commonCacheResolverConfig")
@EnableCaching
@Order(30) // 在缓存管理器之后加载
public class CacheResolverConfig {

    private final CacheManager commonAppCacheManager;

    public CacheResolverConfig(@Qualifier("commonAppCacheManager") CacheManager commonAppCacheManager) {
        this.commonAppCacheManager = commonAppCacheManager;
    }

    /**
     * 配置默认缓存解析器
     * 指定使用commonAppCacheManager作为默认缓存管理器
     * 
     * 这样当@Cacheable等注解没有指定cacheManager时，
     * 会自动使用这个默认的缓存解析器
     * 
     * @return 默认缓存解析器
     */
    @Bean("defaultCacheResolver")
    public CacheResolver defaultCacheResolver() {
        log.info("配置默认缓存解析器，使用commonAppCacheManager");
        return new SimpleCacheResolver(commonAppCacheManager);
    }
} 