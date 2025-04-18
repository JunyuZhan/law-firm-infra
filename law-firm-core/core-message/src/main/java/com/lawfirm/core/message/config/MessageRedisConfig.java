package com.lawfirm.core.message.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class MessageRedisConfig {

    /**
     * 配置消息模块的Redis模板
     * 使用注入的ObjectMapper确保序列化一致性
     */
    @Bean(name = "messageRedisTemplate")
    public RedisTemplate<String, Object> messageRedisTemplate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("objectMapper") ObjectMapper objectMapper) {
        
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        // 使用注入的ObjectMapper的副本，添加自定义配置
        ObjectMapper messageMapper = objectMapper.copy();
        messageMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        messageMapper.registerModule(new JavaTimeModule());
        
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(messageMapper, Object.class);

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        
        template.afterPropertiesSet();
        return template;
    }
} 