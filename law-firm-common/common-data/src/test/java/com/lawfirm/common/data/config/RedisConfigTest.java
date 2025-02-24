package com.lawfirm.common.data.config;

import com.lawfirm.common.data.TestApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TestApplication.class, TestConfig.class})
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.main.allow-bean-definition-overriding=true",
    "spring.data.redis.host=localhost",
    "spring.data.redis.port=6379",
    "spring.data.redis.database=0",
    "spring.data.redis.timeout=10000",
    "spring.data.redis.lettuce.pool.max-active=8",
    "spring.data.redis.lettuce.pool.max-idle=8",
    "spring.data.redis.lettuce.pool.min-idle=0",
    "spring.data.redis.lettuce.pool.max-wait=-1ms"
})
class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> valueOperations;
    private HashOperations<String, Object, Object> hashOperations;

    @BeforeEach
    void setUp() {
        valueOperations = redisTemplate.opsForValue();
        hashOperations = redisTemplate.opsForHash();
        // 清理测试数据
        redisTemplate.delete("test:*");
    }

    @Test
    void testStringOperations() {
        String key = "test:string";
        String value = "Hello Redis";
        
        valueOperations.set(key, value);
        Object result = valueOperations.get(key);
        
        assertEquals(value, result);
    }

    @Test
    void testHashOperations() {
        String key = "test:hash";
        String hashKey = "name";
        String value = "Test User";
        
        hashOperations.put(key, hashKey, value);
        Object result = hashOperations.get(key, hashKey);
        
        assertEquals(value, result);
    }

    @Test
    void testObjectSerialization() {
        String key = "test:object";
        TestObject obj = new TestObject("Test Name", 25);
        
        valueOperations.set(key, obj);
        Object result = valueOperations.get(key);
        
        assertTrue(result instanceof TestObject);
        TestObject retrievedObj = (TestObject) result;
        assertEquals(obj.getName(), retrievedObj.getName());
        assertEquals(obj.getAge(), retrievedObj.getAge());
    }

    static class TestObject {
        private String name;
        private int age;

        public TestObject() {}

        public TestObject(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
    }
} 