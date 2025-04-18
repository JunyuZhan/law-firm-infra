package com.lawfirm.common.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis模板配置类，负责配置RedisTemplate及其序列化器
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "spring.data.redis", name = "host")
@Profile("!test")
public class RedisTemplateConfig {

    @Bean(name = "commonRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用注入的ObjectMapper，确保序列化配置一致
        ObjectMapper redisObjectMapper = objectMapper.copy();
        redisObjectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        redisObjectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(redisObjectMapper);

        template.setValueSerializer(serializer);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();

        return template;
    }
    
    /**
     * 数据层Redis模板，用于数据操作
     * 与commonRedisTemplate相同的配置，但提供不同的Bean名称以满足特定组件的依赖
     */
    @Bean(name = "dataRedisTemplate")
    @Primary
    public RedisTemplate<String, Object> dataRedisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        // 复用相同的配置
        return redisTemplate(connectionFactory, objectMapper);
    }
}