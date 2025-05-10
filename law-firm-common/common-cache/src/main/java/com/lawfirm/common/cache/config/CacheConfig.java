package com.lawfirm.common.cache.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

/**
 * 基础缓存配置类
 * 
 * 职责：
 * 1. 提供Redisson客户端
 * 2. 提供内存缓存管理器作为备选方案
 * 不再提供主要的CacheManager，避免与其他缓存管理器冲突
 * 
 * 注意：此类为纯粹的基础设施，不包含任何业务逻辑或业务相关配置
 */
@Configuration("commonCacheConfig")
@EnableCaching
@Order(10) // 确保在其他缓存配置之后加载
public class CacheConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.password:}")
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

    /**
     * 提供Redisson客户端，用于分布式锁等功能
     */
    @Bean(name = "commonRedissonClient")
    @ConditionalOnProperty(prefix = "spring.data.redis", name = "host")
    @ConditionalOnMissingBean(name = "commonRedissonClient")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
              .setAddress("redis://" + host + ":" + port);
        
        // 只有当密码非空时才设置密码
        if (StringUtils.hasText(password) && !password.isEmpty() && !password.trim().isEmpty()) {
            config.useSingleServer().setPassword(password);
        } else {
            // 显式设置为null，确保不发送AUTH命令
            config.useSingleServer().setPassword(null);
        }
        
        return Redisson.create(config);
    }

    /**
     * 提供内存缓存管理器，仅在未配置Redis时使用
     * 注意：这是一个通用的缓存管理器，不包含业务相关配置
     */
    @Bean("localCacheManager")
    @ConditionalOnProperty(name = "law.firm.cache.type", havingValue = "LOCAL")
    @ConditionalOnMissingBean(name = "cacheManager")
    public CacheManager localCacheManager() {
        // 不指定具体缓存名称，让使用方自行定义业务缓存名称
        return new ConcurrentMapCacheManager();
    }
} 