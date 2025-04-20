package com.lawfirm.system.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

/**
 * 监控模块缓存配置
 */
@Configuration
@EnableCaching
public class MonitorCacheConfig {

    /**
     * 创建监控缓存管理器
     */
    @Bean(name = "monitorCacheManager")
    @Primary
    public CacheManager monitorCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("monitorCache")
        ));
        return cacheManager;
    }
} 