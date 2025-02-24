package com.lawfirm.common.cache.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.TimeoutOptions;
import java.time.Duration;

@TestConfiguration
public class RedisTestConfig {

    @Bean
    @Primary
    public RedisProperties testRedisProperties() {
        RedisProperties properties = new RedisProperties();
        properties.setHost("localhost");
        properties.setPort(6379);
        properties.setDatabase(15);
        properties.setTimeout(Duration.ofSeconds(10));
        properties.setConnectTimeout(Duration.ofSeconds(5));
        return properties;
    }

    @Bean
    @Primary
    public RedisConnectionFactory testRedisConnectionFactory(RedisProperties testRedisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(testRedisProperties.getHost());
        config.setPort(testRedisProperties.getPort());
        config.setDatabase(testRedisProperties.getDatabase());

        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(testRedisProperties.getTimeout())
                .clientOptions(ClientOptions.builder()
                        .timeoutOptions(TimeoutOptions.enabled(testRedisProperties.getTimeout()))
                        .build())
                .build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfig);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory testRedisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(testRedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
} 