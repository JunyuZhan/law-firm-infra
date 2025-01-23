package com.lawfirm.common.cache.config;

import org.mockito.Mockito;
import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    RedisRepositoriesAutoConfiguration.class,
    org.redisson.spring.starter.RedissonAutoConfiguration.class
})
@ComponentScan("com.lawfirm.common.cache")
public class TestConfig {
    
    @Bean
    @Primary
    @SuppressWarnings("unchecked")
    public RedisTemplate<String, Object> redisTemplate() {
        return Mockito.mock(RedisTemplate.class);
    }
    
    @Bean
    @Primary
    public StringRedisTemplate stringRedisTemplate() {
        return Mockito.mock(StringRedisTemplate.class);
    }
    
    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        return Mockito.mock(RedisConnectionFactory.class);
    }

    @Bean
    @Primary
    public RedissonClient redissonClient() {
        return Mockito.mock(RedissonClient.class);
    }
} 