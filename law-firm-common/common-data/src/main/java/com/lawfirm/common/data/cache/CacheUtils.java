package com.lawfirm.common.data.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;

/**
 * 缓存工具类
 */
@Component
public class CacheUtils {

    private static final Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取缓存的基本对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getCacheObject(String key) {
        try {
            ValueOperations<String, T> ops = (ValueOperations<String, T>) redisTemplate.opsForValue();
            return ops.get(key);
        } catch (Exception e) {
            logger.error("获取缓存失败: key={}, error={}", key, e.getMessage());
            return null;
        }
    }

    /**
     * 设置缓存的基本对象
     */
    @SuppressWarnings("unchecked")
    public <T> void setCacheObject(String key, T value) {
        try {
            ValueOperations<String, T> ops = (ValueOperations<String, T>) redisTemplate.opsForValue();
            ops.set(key, value);
        } catch (Exception e) {
            logger.error("设置缓存失败: key={}, value={}, error={}", key, value, e.getMessage());
        }
    }

    /**
     * 设置缓存的基本对象，并设置过期时间
     */
    @SuppressWarnings("unchecked")
    public <T> void setCacheObject(String key, T value, long timeout, TimeUnit timeUnit) {
        try {
            ValueOperations<String, T> ops = (ValueOperations<String, T>) redisTemplate.opsForValue();
            ops.set(key, value, timeout, timeUnit);
        } catch (Exception e) {
            logger.error("设置缓存失败: key={}, value={}, timeout={}, timeUnit={}, error={}", 
                key, value, timeout, timeUnit, e.getMessage());
        }
    }

    /**
     * 删除单个对象
     */
    public boolean deleteObject(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.delete(key));
        } catch (Exception e) {
            logger.error("删除缓存失败: key={}, error={}", key, e.getMessage());
            return false;
        }
    }

    /**
     * 删除集合对象
     */
    public long deleteObject(Collection<String> collection) {
        try {
            return redisTemplate.delete(collection);
        } catch (Exception e) {
            logger.error("删除缓存集合失败: collection={}, error={}", collection, e.getMessage());
            return 0;
        }
    }

    /**
     * 获取缓存的基本对象列表
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getCacheList(String key) {
        try {
            ListOperations<String, T> ops = (ListOperations<String, T>) redisTemplate.opsForList();
            return ops.range(key, 0, -1);
        } catch (Exception e) {
            logger.error("获取缓存列表失败: key={}, error={}", key, e.getMessage());
            return null;
        }
    }

    /**
     * 设置缓存的基本对象列表
     */
    @SuppressWarnings("unchecked")
    public <T> void setCacheList(String key, List<T> dataList) {
        try {
            ListOperations<String, T> ops = (ListOperations<String, T>) redisTemplate.opsForList();
            ops.rightPushAll(key, dataList.toArray((T[]) new Object[0]));
        } catch (Exception e) {
            logger.error("设置缓存列表失败: key={}, dataList={}, error={}", key, dataList, e.getMessage());
        }
    }

    /**
     * 获取缓存的时间
     */
    public long getExpire(String key, TimeUnit unit) {
        try {
            return redisTemplate.getExpire(key, unit);
        } catch (Exception e) {
            logger.error("获取缓存过期时间失败: key={}, unit={}, error={}", key, unit, e.getMessage());
            return -1;
        }
    }

    /**
     * 缓存Set
     */
    @SuppressWarnings("unchecked")
    public <T> long setCacheSet(String key, Set<T> dataSet) {
        try {
            Long count = redisTemplate.opsForSet().add(key, dataSet.toArray());
            return count == null ? 0 : count;
        } catch (Exception e) {
            logger.error("设置缓存Set失败: key={}, dataSet={}, error={}", key, dataSet, e.getMessage());
            return 0;
        }
    }

    /**
     * 获取缓存的set
     */
    @SuppressWarnings("unchecked")
    public <T> Set<T> getCacheSet(String key) {
        try {
            return (Set<T>) redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error("获取缓存Set失败: key={}, error={}", key, e.getMessage());
            return null;
        }
    }

    /**
     * 缓存Map
     */
    @SuppressWarnings("unchecked")
    public <T> void setCacheMap(String key, Map<String, T> dataMap) {
        try {
            redisTemplate.opsForHash().putAll(key, new HashMap<>(dataMap));
        } catch (Exception e) {
            logger.error("设置缓存Map失败: key={}, dataMap={}, error={}", key, dataMap, e.getMessage());
        }
    }

    /**
     * 获取缓存的Map
     */
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getCacheMap(String key) {
        try {
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
            Map<String, T> result = new HashMap<>();
            entries.forEach((k, v) -> result.put(k.toString(), (T) v));
            return result;
        } catch (Exception e) {
            logger.error("获取缓存Map失败: key={}, error={}", key, e.getMessage());
            return null;
        }
    }

    /**
     * 获取缓存的基本对象列表
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 判断key是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }
} 