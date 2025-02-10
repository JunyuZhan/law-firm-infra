package com.lawfirm.document.service.impl;

import com.lawfirm.model.document.repository.DocumentPreviewRepository;
import com.lawfirm.model.document.entity.DocumentPreview;
import com.lawfirm.model.document.service.DocumentPreviewCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 文档预览缓存服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentPreviewCacheServiceImpl implements DocumentPreviewCacheService {

    private final DocumentPreviewRepository documentPreviewRepository;
    private final AtomicLong cacheHits = new AtomicLong(0);
    private final AtomicLong cacheMisses = new AtomicLong(0);

    @Override
    @Cacheable(value = "documentPreview", key = "#documentId", unless = "#result == null")
    public DocumentPreview getPreviewFromCache(Long documentId) {
        DocumentPreview preview = documentPreviewRepository.findByDocumentId(documentId)
                .orElse(null);
        if (preview != null) {
            cacheHits.incrementAndGet();
        } else {
            cacheMisses.incrementAndGet();
        }
        return preview;
    }

    @Override
    @CacheEvict(value = "documentPreview", key = "#documentId")
    public void invalidateCache(Long documentId) {
        log.debug("清除文档预览缓存：documentId={}", documentId);
    }

    @Override
    @CacheEvict(value = "documentPreview", allEntries = true)
    public void clearAllCache() {
        log.info("清除所有文档预览缓存");
    }

    @Override
    public double getCacheHitRate() {
        long hits = cacheHits.get();
        long total = hits + cacheMisses.get();
        return total == 0 ? 0 : (double) hits / total;
    }

    @Override
    public void resetCacheStats() {
        cacheHits.set(0);
        cacheMisses.set(0);
    }

    /**
     * 定时清理过期缓存（每天凌晨2点执行）
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredCache() {
        try {
            log.info("开始清理过期预览缓存");
            LocalDateTime expireTime = LocalDateTime.now().minusDays(7); // 7天前的缓存视为过期
            List<DocumentPreview> expiredPreviews = documentPreviewRepository.findPreviewsToClean(expireTime);
            
            for (DocumentPreview preview : expiredPreviews) {
                invalidateCache(preview.getDocumentId());
            }
            log.info("清理过期预览缓存完成，共清理{}条", expiredPreviews.size());
        } catch (Exception e) {
            log.error("清理过期预览缓存失败", e);
        }
    }

    /**
     * 定时记录缓存统计信息（每小时执行）
     */
    @Scheduled(fixedRate = 3600000)
    public void logCacheStats() {
        double hitRate = getCacheHitRate();
        log.info("预览缓存命中率：{:.2f}%, 命中次数：{}, 未命中次数：{}", 
                hitRate * 100, cacheHits.get(), cacheMisses.get());
    }
} 