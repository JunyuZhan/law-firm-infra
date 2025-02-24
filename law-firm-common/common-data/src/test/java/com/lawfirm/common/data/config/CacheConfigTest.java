package com.lawfirm.common.data.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.cache.type=redis",
    "spring.cache.redis.time-to-live=3600000",
    "spring.cache.redis.key-prefix=test:"
})
class CacheConfigTest {

    @MockBean
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisCacheConfiguration redisCacheConfiguration;

    @Test
    void cacheManager_ShouldBeConfigured() {
        assertNotNull(cacheManager, "Cache manager should be configured");
    }

    @Test
    void redisCacheConfiguration_ShouldBeConfigured() {
        assertNotNull(redisCacheConfiguration, "Redis cache configuration should be configured");
        assertEquals(Duration.ofHours(1), redisCacheConfiguration.getTtl(), 
            "Default TTL should be 1 hour");
        assertTrue(redisCacheConfiguration.usePrefix(), 
            "Key prefix should be enabled");
    }
} 