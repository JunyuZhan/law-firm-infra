package com.lawfirm.common.cache.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务接口
 * 提供统一的缓存操作接口，支持多种缓存实现（如Redis、Caffeine等）
 */
public interface CacheService {
    
    /**
     * 设置缓存
     *
     * @param key 缓存键
     * @param value 缓存值
     */
    <T> void set(String key, T value);
    
    /**
     * 设置缓存并指定过期时间
     *
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    <T> void set(String key, T value, long timeout, TimeUnit unit);
    
    /**
     * 获取缓存
     *
     * @param key 缓存键
     * @return 缓存值
     */
    <T> T get(String key);
    
    /**
     * 删除缓存
     *
     * @param key 缓存键
     * @return 是否删除成功
     */
    boolean delete(String key);
    
    /**
     * 批量删除缓存
     *
     * @param keys 缓存键集合
     * @return 删除的键数量
     */
    long delete(Collection<String> keys);
    
    /**
     * 设置过期时间
     *
     * @param key 缓存键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return 是否设置成功
     */
    boolean expire(String key, long timeout, TimeUnit unit);
    
    /**
     * 获取过期时间
     *
     * @param key 缓存键
     * @param unit 时间单位
     * @return 过期时间，-1表示永不过期，-2表示键不存在
     */
    long getExpire(String key, TimeUnit unit);
    
    /**
     * 判断缓存是否存在
     *
     * @param key 缓存键
     * @return 是否存在
     */
    boolean hasKey(String key);
    
    /**
     * 递增
     *
     * @param key 缓存键
     * @param delta 增量
     * @return 递增后的值
     */
    long increment(String key, long delta);
    
    /**
     * 递减
     *
     * @param key 缓存键
     * @param delta 减量
     * @return 递减后的值
     */
    long decrement(String key, long delta);
    
    /**
     * 获取匹配的所有键
     *
     * @param pattern 匹配模式
     * @return 匹配的键集合
     */
    Set<String> keys(String pattern);
    
    /**
     * 获取缓存的类型
     *
     * @param key 缓存键
     * @return 类型名称
     */
    String type(String key);
    
    /**
     * 清空所有缓存
     */
    void clear();
    
    // Hash操作
    
    /**
     * 设置Hash缓存
     *
     * @param key 缓存键
     * @param hashKey Hash键
     * @param value Hash值
     */
    <T> void hSet(String key, String hashKey, T value);
    
    /**
     * 获取Hash缓存
     *
     * @param key 缓存键
     * @param hashKey Hash键
     * @return Hash值
     */
    <T> T hGet(String key, String hashKey);
    
    /**
     * 删除Hash缓存
     *
     * @param key 缓存键
     * @param hashKeys Hash键集合
     * @return 删除的数量
     */
    long hDelete(String key, Object... hashKeys);
    
    /**
     * 判断Hash缓存是否存在
     *
     * @param key 缓存键
     * @param hashKey Hash键
     * @return 是否存在
     */
    boolean hHasKey(String key, String hashKey);
    
    /**
     * 获取Hash的所有键值对
     *
     * @param key 缓存键
     * @return 键值对Map
     */
    <T> Map<String, T> hGetAll(String key);
    
    /**
     * 获取Hash的所有键
     *
     * @param key 缓存键
     * @return 键集合
     */
    Set<String> hKeys(String key);
    
    /**
     * 获取Hash的所有值
     *
     * @param key 缓存键
     * @return 值列表
     */
    <T> List<T> hValues(String key);
    
    /**
     * 获取Hash的大小
     *
     * @param key 缓存键
     * @return Hash的大小
     */
    long hSize(String key);
    
    // List操作
    
    /**
     * 从左边插入List
     *
     * @param key 缓存键
     * @param value 值
     * @return List的长度
     */
    <T> long lLeftPush(String key, T value);
    
    /**
     * 从右边插入List
     *
     * @param key 缓存键
     * @param value 值
     * @return List的长度
     */
    <T> long lRightPush(String key, T value);
    
    /**
     * 从左边弹出List
     *
     * @param key 缓存键
     * @return 弹出的值
     */
    <T> T lLeftPop(String key);
    
    /**
     * 从右边弹出List
     *
     * @param key 缓存键
     * @return 弹出的值
     */
    <T> T lRightPop(String key);
    
    /**
     * 获取List的长度
     *
     * @param key 缓存键
     * @return List的长度
     */
    long lSize(String key);
    
    /**
     * 获取List指定范围的值
     *
     * @param key 缓存键
     * @param start 开始位置
     * @param end 结束位置
     * @return 值列表
     */
    <T> List<T> lRange(String key, long start, long end);
    
    /**
     * 删除List中的值
     *
     * @param key 缓存键
     * @param count 删除数量
     * @param value 要删除的值
     * @return 实际删除的数量
     */
    <T> long lRemove(String key, long count, T value);
    
    // Set操作
    
    /**
     * 添加Set成员
     *
     * @param key 缓存键
     * @param values 值集合
     * @return 添加成功的数量
     */
    <T> long sAdd(String key, Collection<T> values);
    
    /**
     * 移除Set成员
     *
     * @param key 缓存键
     * @param values 值集合
     * @return 移除成功的数量
     */
    <T> long sRemove(String key, Collection<T> values);
    
    /**
     * 判断是否是Set的成员
     *
     * @param key 缓存键
     * @param value 值
     * @return 是否是成员
     */
    <T> boolean sIsMember(String key, T value);
    
    /**
     * 获取Set的所有成员
     *
     * @param key 缓存键
     * @return 成员集合
     */
    <T> Set<T> sMembers(String key);
    
    /**
     * 获取Set的大小
     *
     * @param key 缓存键
     * @return Set的大小
     */
    long sSize(String key);
} 