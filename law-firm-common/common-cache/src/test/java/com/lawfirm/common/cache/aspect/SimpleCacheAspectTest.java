package com.lawfirm.common.cache.aspect;

import com.lawfirm.common.cache.annotation.SimpleCache;
import com.lawfirm.common.cache.config.RedisTestConfig;
import com.lawfirm.common.cache.constant.CacheConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=com.lawfirm.common.cache.config.CacheAutoConfiguration"
})
@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class})
@ActiveProfiles("test")
@Import(RedisTestConfig.class)
public class SimpleCacheAspectTest {

    @Autowired
    private TestService testService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void setUp() {
        // 清除所有相关的缓存键
        Set<String> keys = redisTemplate.keys(CacheConstants.SIMPLE_CACHE_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @Test
    void testSimpleCache() {
        // 第一次调用,应该执行方法并缓存
        String result1 = testService.getData("test");
        assertEquals("test-data", result1);

        // 第二次调用,应该直接从缓存返回
        String result2 = testService.getData("test");
        assertEquals("test-data", result2);

        // 获取实际的缓存键
        Set<String> keys = redisTemplate.keys(CacheConstants.SIMPLE_CACHE_PREFIX + "*");
        assertNotNull(keys);
        assertEquals(1, keys.size());
        String actualKey = keys.iterator().next();

        // 验证缓存存在且值正确
        assertTrue(redisTemplate.hasKey(actualKey));
        assertEquals("test-data", redisTemplate.opsForValue().get(actualKey));
    }

    @Test
    void testSimpleCacheWithException() {
        // 测试抛出异常时的行为
        assertThrows(RuntimeException.class, () -> testService.getDataWithException("test"));
        
        // 验证异常情况下没有缓存
        Set<String> keys = redisTemplate.keys(CacheConstants.SIMPLE_CACHE_PREFIX + "*");
        assertTrue(keys == null || keys.isEmpty());
    }
}

@Component
class TestService {
    
    @SimpleCache(key = "test:simple:cache:key:#param", timeout = 5, timeUnit = TimeUnit.MINUTES)
    public String getData(String param) {
        return param + "-data";
    }

    @SimpleCache(key = "test:simple:cache:key:#param", timeout = 5, timeUnit = TimeUnit.MINUTES)
    public String getDataWithException(String param) {
        throw new RuntimeException("Test exception");
    }
} 