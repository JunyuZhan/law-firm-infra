package com.lawfirm.model.base.support.cache;

/**
 * 缓存管理器
 */
public interface CacheManager {

    /**
     * 设置缓存
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    void set(String key, Object value);

    /**
     * 设置缓存并设置过期时间
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param timeout 过期时间(秒)
     */
    void set(String key, Object value, long timeout);

    /**
     * 获取缓存
     *
     * @param key   缓存键
     * @param clazz 返回类型
     * @return 缓存值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 删除缓存
     *
     * @param key 缓存键
     */
    void delete(String key);

    /**
     * 判断缓存是否存在
     *
     * @param key 缓存键
     * @return 是否存在
     */
    boolean exists(String key);

    /**
     * 设置过期时间
     *
     * @param key     缓存键
     * @param timeout 过期时间(秒)
     */
    void expire(String key, long timeout);
} 