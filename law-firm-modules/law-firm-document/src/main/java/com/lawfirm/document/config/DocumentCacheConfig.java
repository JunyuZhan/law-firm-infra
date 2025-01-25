package com.lawfirm.document.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class DocumentCacheConfig {

    @Value("${document.preview.cache-enabled:true}")
    private boolean cacheEnabled;
    
    @Value("${document.preview.cache-max-size:1000}")
    private int cacheMaxSize;
    
    @Value("${document.preview.cache-ttl:3600}")
    private int cacheTtl;

    @Bean
    public CacheManager documentCacheManager() {
        if (!cacheEnabled) {
            return new CaffeineCacheManager();
        }
        
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(Arrays.asList(
            "document_preview",
            "document_preview_content",
            "document_preview_support",
            "document_thumbnail"
        ));
        
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(cacheMaxSize)
            .expireAfterWrite(cacheTtl, TimeUnit.SECONDS)
            .recordStats());
        
        return cacheManager;
    }
} 