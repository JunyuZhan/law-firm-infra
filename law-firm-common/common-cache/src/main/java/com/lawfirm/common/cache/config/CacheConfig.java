package com.lawfirm.common.cache.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 缓存配置
 */
@Configuration("commonCacheConfig")
@EnableCaching
public class CacheConfig {

    @Value("${spring.redis.host:localhost}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password:}")
    private String password;

    @Bean
    @ConditionalOnProperty(prefix = "spring.redis", name = "host")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
              .setAddress("redis://" + host + ":" + port)
              .setPassword(password.isEmpty() ? null : password);
        return Redisson.create(config);
    }

    /**
     * 定义缓存管理器，使用内存缓存作为备用
     * 注意：在实际应用中应该使用RedissonSpringCacheManager
     */
    @Bean("commonCacheManager")
    @Primary
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("common");
    }
} 