package com.lawfirm.document.manager.cache;

import com.lawfirm.common.cache.CacheManager;

/**
 * 文档缓存管理器
 */
public interface DocumentCacheManager extends CacheManager {
    
    /**
     * 缓存命名空间
     */
    String CACHE_NAMESPACE = "document";
    
    /**
     * 文档元数据缓存前缀
     */
    String DOCUMENT_META_PREFIX = CACHE_NAMESPACE + ":meta:";
    
    /**
     * 文档权限缓存前缀
     */
    String DOCUMENT_PERMISSION_PREFIX = CACHE_NAMESPACE + ":permission:";
    
    /**
     * 文档编辑锁定缓存前缀
     */
    String DOCUMENT_LOCK_PREFIX = CACHE_NAMESPACE + ":lock:";
    
    /**
     * 文档会话缓存前缀
     */
    String DOCUMENT_SESSION_PREFIX = CACHE_NAMESPACE + ":session:";
    
    /**
     * 获取文档元数据缓存key
     */
    default String getDocumentMetaCacheKey(Long documentId) {
        return DOCUMENT_META_PREFIX + documentId;
    }
    
    /**
     * 获取文档权限缓存key
     */
    default String getDocumentPermissionCacheKey(Long documentId, Long userId) {
        return DOCUMENT_PERMISSION_PREFIX + documentId + ":" + userId;
    }
    
    /**
     * 获取文档编辑锁定缓存key
     */
    default String getDocumentLockCacheKey(Long documentId) {
        return DOCUMENT_LOCK_PREFIX + documentId;
    }
    
    /**
     * 获取文档会话缓存key
     */
    default String getDocumentSessionCacheKey(Long documentId, String sessionId) {
        return DOCUMENT_SESSION_PREFIX + documentId + ":" + sessionId;
    }
} 