package com.lawfirm.document.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.lawfirm.document.monitor.CacheMonitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class DocumentCacheConfig {

    @Bean
    public Cache<String, byte[]> documentPreviewCache(CacheMonitor cacheMonitor) {
        Cache<String, byte[]> cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .recordStats()
                .build();
        
        cacheMonitor.registerCache("documentPreview", cache);
        return cache;
    }

    @Bean
    public Cache<String, String> documentUrlCache(CacheMonitor cacheMonitor) {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .recordStats()
                .build();
        
        cacheMonitor.registerCache("documentUrl", cache);
        return cache;
    }
} 