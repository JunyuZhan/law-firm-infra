package com.lawfirm.document.monitor;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 缓存监控器
 * 负责收集和报告缓存使用情况
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheMonitor {

    private final CacheManager cacheManager;
    
    /**
     * 每分钟收集一次缓存统计信息
     */
    @Scheduled(fixedRate = 60_000)
    public void collectCacheStats() {
        Map<String, CacheStats> statsMap = new HashMap<>();
        
        cacheManager.getCacheNames().forEach(cacheName -> {
            CaffeineCache cache = (CaffeineCache) cacheManager.getCache(cacheName);
            if (cache != null) {
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = 
                    cache.getNativeCache();
                statsMap.put(cacheName, nativeCache.stats());
            }
        });
        
        logCacheStats(statsMap);
    }
    
    /**
     * 获取缓存统计信息
     */
    public Map<String, Map<String, Object>> getCacheStats() {
        Map<String, Map<String, Object>> result = new HashMap<>();
        
        cacheManager.getCacheNames().forEach(cacheName -> {
            CaffeineCache cache = (CaffeineCache) cacheManager.getCache(cacheName);
            if (cache != null) {
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = 
                    cache.getNativeCache();
                CacheStats stats = nativeCache.stats();
                
                Map<String, Object> cacheStats = new HashMap<>();
                cacheStats.put("hitCount", stats.hitCount());
                cacheStats.put("missCount", stats.missCount());
                cacheStats.put("hitRate", stats.hitRate());
                cacheStats.put("missRate", stats.missRate());
                cacheStats.put("evictionCount", stats.evictionCount());
                cacheStats.put("estimatedSize", nativeCache.estimatedSize());
                
                result.put(cacheName, cacheStats);
            }
        });
        
        return result;
    }
    
    private void logCacheStats(Map<String, CacheStats> statsMap) {
        statsMap.forEach((cacheName, stats) -> {
            log.info("Cache '{}' 统计信息:", cacheName);
            log.info("  命中次数: {}", stats.hitCount());
            log.info("  未命中次数: {}", stats.missCount());
            log.info("  命中率: {}", String.format("%.2f%%", stats.hitRate() * 100));
            log.info("  平均加载时间: {}ms", TimeUnit.NANOSECONDS.toMillis(stats.totalLoadTime() / stats.loadCount()));
            log.info("  驱逐次数: {}", stats.evictionCount());
        });
    }
} 