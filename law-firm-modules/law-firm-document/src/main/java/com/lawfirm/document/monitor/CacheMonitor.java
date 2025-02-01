package com.lawfirm.document.monitor;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存监控器
 * 负责收集和报告缓存使用情况
 */
@Slf4j
@Component
public class CacheMonitor {

    private final Map<String, Cache<?, ?>> caches = new ConcurrentHashMap<>();

    public void registerCache(String name, Cache<?, ?> cache) {
        caches.put(name, cache);
    }

    @Scheduled(fixedRate = 300000) // 每5分钟执行一次
    public void monitorCacheStats() {
        caches.forEach((name, cache) -> {
            CacheStats stats = cache.stats();
            log.info("Cache '{}' 统计信息:", name);
            log.info("  命中率: {}", stats.hitRate());
            log.info("  平均加载惩罚: {}ms", stats.averageLoadPenalty() / 1_000_000);
            log.info("  驱逐数量: {}", stats.evictionCount());
            log.info("  当前大小: {}", cache.estimatedSize());
        });
    }
} 