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
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.core.env.Environment;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;

/**
 * 缓存配置
 */
@Configuration("commonCacheConfig")
@EnableCaching
@Order(1)
public class CacheConfig {

    @Value("${spring.redis.host:localhost}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password:}")
    private String password;
    
    @Value("${law.firm.cache.enabled:false}")
    private boolean cacheEnabled;
    
    @Value("${law.firm.cache.type:LOCAL}")
    private String cacheType;
    
    @Value("${law.firm.cache.expiration:30}")
    private int cacheExpiration;

    private final Environment environment;
    
    public CacheConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "commonRedissonClient")
    @ConditionalOnProperty(prefix = "spring.redis", name = "host")
    @Order(2)
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
              .setAddress("redis://" + host + ":" + port)
              .setPassword(password.isEmpty() ? null : password);
        return Redisson.create(config);
    }

    /**
     * 定义缓存管理器，根据配置使用Redis或内存缓存
     */
    @Bean("commonCacheManager")
    @Primary
    @Order(3)
    public CacheManager cacheManager() {
        // 获取是否使用Redis的配置
        boolean useRedis = "REDIS".equalsIgnoreCase(cacheType) && cacheEnabled && 
                           environment.getProperty("dev.use-redis", Boolean.class, true);

        // 定义需要缓存的项
        List<String> cacheNames = Arrays.asList("common", "menu", "dict", "user", "role", "perm");
        
        // 根据配置选择缓存实现
        if (useRedis) {
            RedissonClient redissonClient = redissonClient();
            if (redissonClient != null) {
                return new RedissonSpringCacheManager(redissonClient);
            }
        }
        return new ConcurrentMapCacheManager(cacheNames.toArray(new String[0]));
    }
} 