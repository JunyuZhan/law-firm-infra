package com.lawfirm.common.cache.utils;

import com.lawfirm.common.cache.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
class CacheUtilTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CacheUtil cacheUtil;

    private ValueOperations<String, Object> valueOperations;

    @BeforeEach
    void setUp() {
        valueOperations = Mockito.mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testString() {
        String key = "test:string";
        String value = "hello";
        
        // 模拟设置和获取
        when(valueOperations.get(anyString())).thenReturn(value);
        when(redisTemplate.hasKey(anyString())).thenReturn(true);
        
        // 测试设置和获取
        cacheUtil.set(key, value, 60, TimeUnit.SECONDS);
        String result = (String) cacheUtil.get(key);
        assert value.equals(result);
        
        // 测试删除
        when(redisTemplate.delete(anyString())).thenReturn(true);
        cacheUtil.delete(key);
        
        // 验证方法调用
        Mockito.verify(valueOperations).set(key, value, 60, TimeUnit.SECONDS);
        Mockito.verify(redisTemplate).delete(key);
    }

    @Test
    void testHash() {
        String key = "test:hash";
        String field = "name";
        String value = "test";
        
        // 测试设置和获取
        cacheUtil.hSet(key, field, value);
        assertEquals(value, cacheUtil.hGet(key, field));
        
        // 测试删除
        assertTrue(cacheUtil.hHasKey(key, field));
        cacheUtil.hDelete(key, field);
        assertFalse(cacheUtil.hHasKey(key, field));
    }

    @Test
    void testList() {
        String key = "test:list";
        String value = "item";
        
        // 测试添加和获取
        cacheUtil.lPush(key, value);
        assertEquals(value, cacheUtil.lRange(key, 0, -1).get(0));
        
        // 测试批量添加
        cacheUtil.lPushAll(key, Arrays.asList("item1", "item2"));
        assertEquals(3, cacheUtil.lSize(key));
        
        // 测试删除
        cacheUtil.lRemove(key, 1, value);
        assertEquals(2, cacheUtil.lSize(key));
    }

    @Test
    void testSet() {
        String key = "test:set";
        String value = "member";
        
        // 测试添加和判断
        cacheUtil.sAdd(key, value);
        assertTrue(cacheUtil.sIsMember(key, value));
        
        // 测试获取所有成员
        assertEquals(1, cacheUtil.sMembers(key).size());
        
        // 测试删除
        cacheUtil.sRemove(key, value);
        assertFalse(cacheUtil.sIsMember(key, value));
    }
} 