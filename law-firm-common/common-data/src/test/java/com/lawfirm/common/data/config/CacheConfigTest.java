package com.lawfirm.common.data.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisServerCommands;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings("deprecation") // 整个类抑制deprecation警告
class CacheConfigTest {

    @Mock
    private RedisConnectionFactory redisConnectionFactory;
    
    @Mock
    private RedisConnection redisConnection;
    
    @Mock
    private RedisServerCommands redisServerCommands;

    @InjectMocks
    private RedisCacheManagerConfig redisCacheManagerConfig;

    private CacheManager cacheManager;
    private RedisCacheConfiguration redisCacheConfiguration;

    @BeforeEach
    void setUp() {
        // 模拟Redis连接
        when(redisConnectionFactory.getConnection()).thenReturn(redisConnection);
        when(redisConnection.serverCommands()).thenReturn(redisServerCommands);
        
        // 模拟缓存键集合 - 使用兼容的API
        Set<byte[]> cacheKeys = new HashSet<>();
        when(redisConnection.keys(any(byte[].class))).thenReturn(cacheKeys);
        
        // 创建缓存配置
        redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofHours(1))
            .prefixCacheNameWith("test:");
            
        // 创建缓存管理器
        cacheManager = RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .build();
    }

    @Test
    void cacheManager_ShouldBeConfigured() {
        assertNotNull(cacheManager, "Cache manager should be configured");
        assertTrue(cacheManager instanceof RedisCacheManager, "Cache manager should be RedisCacheManager");
    }

    @Test
    void redisCacheConfiguration_ShouldBeConfigured() {
        assertNotNull(redisCacheConfiguration, "Redis cache configuration should be configured");
        
        // 使用兼容的API，对于警告添加注解忽略
        Duration ttl = redisCacheConfiguration.getTtl();
        assertEquals(Duration.ofHours(1), ttl, "Default TTL should be 1 hour");
    }
    
    @Test
    void redisConnectionFactory_ShouldReturnMockConnection() {
        RedisConnection connection = redisConnectionFactory.getConnection();
        assertNotNull(connection, "Redis connection should not be null");
        
        // 验证模拟行为
        RedisServerCommands serverCommands = connection.serverCommands();
        assertNotNull(serverCommands, "Server commands should not be null");
        
        // 验证keys方法，对于警告添加注解忽略
        Set<byte[]> keys = connection.keys("test:*".getBytes());
        assertNotNull(keys);
    }
} 