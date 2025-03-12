package com.lawfirm.document.manager.cache.distributed;

import com.lawfirm.document.manager.cache.DocumentCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的分布式文档缓存管理器
 */
@Component("redisDocumentCacheManager")
public class RedisDocumentCacheManager implements DocumentCacheManager {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisDocumentCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String key, Object value) {
        // 根据不同类型的缓存设置不同的默认过期时间
        if (key.startsWith(DOCUMENT_META_PREFIX)) {
            redisTemplate.opsForValue().set(key, value, 30, TimeUnit.MINUTES);
        } else if (key.startsWith(DOCUMENT_PERMISSION_PREFIX)) {
            redisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
        } else if (key.startsWith(DOCUMENT_LOCK_PREFIX)) {
            redisTemplate.opsForValue().set(key, value, 5, TimeUnit.MINUTES);
        } else if (key.startsWith(DOCUMENT_SESSION_PREFIX)) {
            redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
        }
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void clear() {
        // 按照不同的缓存前缀清除缓存
        redisTemplate.delete(redisTemplate.keys(DOCUMENT_META_PREFIX + "*"));
        redisTemplate.delete(redisTemplate.keys(DOCUMENT_PERMISSION_PREFIX + "*"));
        redisTemplate.delete(redisTemplate.keys(DOCUMENT_LOCK_PREFIX + "*"));
        redisTemplate.delete(redisTemplate.keys(DOCUMENT_SESSION_PREFIX + "*"));
    }
} 