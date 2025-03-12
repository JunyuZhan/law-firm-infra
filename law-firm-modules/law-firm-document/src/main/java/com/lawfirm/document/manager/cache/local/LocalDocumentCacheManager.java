package com.lawfirm.document.manager.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.lawfirm.document.manager.cache.DocumentCacheManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于Caffeine的本地文档缓存管理器
 */
@Component("localDocumentCacheManager")
public class LocalDocumentCacheManager implements DocumentCacheManager {

    /**
     * 文档元数据缓存
     */
    private final Cache<String, Object> documentMetaCache;

    /**
     * 文档权限缓存
     */
    private final Cache<String, Object> documentPermissionCache;

    /**
     * 文档编辑锁定缓存
     */
    private final Cache<String, Object> documentLockCache;

    /**
     * 文档会话缓存
     */
    private final Cache<String, Object> documentSessionCache;

    public LocalDocumentCacheManager() {
        // 文档元数据缓存配置：最大容量1000，过期时间30分钟
        this.documentMetaCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();

        // 文档权限缓存配置：最大容量2000，过期时间10分钟
        this.documentPermissionCache = Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();

        // 文档编辑锁定缓存配置：最大容量500，过期时间5分钟
        this.documentLockCache = Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build();

        // 文档会话缓存配置：最大容量1000，过期时间1小时
        this.documentSessionCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }

    @Override
    public void put(String key, Object value) {
        if (key.startsWith(DOCUMENT_META_PREFIX)) {
            documentMetaCache.put(key, value);
        } else if (key.startsWith(DOCUMENT_PERMISSION_PREFIX)) {
            documentPermissionCache.put(key, value);
        } else if (key.startsWith(DOCUMENT_LOCK_PREFIX)) {
            documentLockCache.put(key, value);
        } else if (key.startsWith(DOCUMENT_SESSION_PREFIX)) {
            documentSessionCache.put(key, value);
        }
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit unit) {
        put(key, value);  // Caffeine已经配置了过期时间，这里直接使用put
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (key.startsWith(DOCUMENT_META_PREFIX)) {
            return (T) documentMetaCache.getIfPresent(key);
        } else if (key.startsWith(DOCUMENT_PERMISSION_PREFIX)) {
            return (T) documentPermissionCache.getIfPresent(key);
        } else if (key.startsWith(DOCUMENT_LOCK_PREFIX)) {
            return (T) documentLockCache.getIfPresent(key);
        } else if (key.startsWith(DOCUMENT_SESSION_PREFIX)) {
            return (T) documentSessionCache.getIfPresent(key);
        }
        return null;
    }

    @Override
    public void delete(String key) {
        if (key.startsWith(DOCUMENT_META_PREFIX)) {
            documentMetaCache.invalidate(key);
        } else if (key.startsWith(DOCUMENT_PERMISSION_PREFIX)) {
            documentPermissionCache.invalidate(key);
        } else if (key.startsWith(DOCUMENT_LOCK_PREFIX)) {
            documentLockCache.invalidate(key);
        } else if (key.startsWith(DOCUMENT_SESSION_PREFIX)) {
            documentSessionCache.invalidate(key);
        }
    }

    @Override
    public void clear() {
        documentMetaCache.invalidateAll();
        documentPermissionCache.invalidateAll();
        documentLockCache.invalidateAll();
        documentSessionCache.invalidateAll();
    }
} 