package com.lawfirm.api.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * 自定义Redisson配置类
 * 用于解决Redis无密码时的认证问题
 */
@Configuration
@Profile("dev")
public class CustomRedissonConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.database:0}")
    private int database;

    /**
     * 自定义RedissonClient配置
     * 明确指定不使用密码
     */
    @Bean
    @Primary
    public RedissonClient redisson() {
        Config config = new Config();
        // 使用单节点模式，明确不设置密码
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setDatabase(database)
                .setPassword(null); // 明确设置为null，不使用密码
        
        return Redisson.create(config);
    }
} 