package com.lawfirm.core.message.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawfirm.core.message.websocket.MessageWebSocketHandler;

/**
 * 消息自动配置
 */
@Configuration
@ComponentScan("com.lawfirm.core.message")
@EnableConfigurationProperties(MessageProperties.class)
@EnableWebSocket
public class MessageAutoConfiguration implements WebSocketConfigurer {
    
    private final MessageProperties messageProperties;
    private final MessageWebSocketHandler messageWebSocketHandler;
    
    public MessageAutoConfiguration(MessageProperties messageProperties, MessageWebSocketHandler messageWebSocketHandler) {
        this.messageProperties = messageProperties;
        this.messageWebSocketHandler = messageWebSocketHandler;
    }
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageWebSocketHandler, messageProperties.getWebsocket().getEndpoint())
                .setAllowedOrigins(messageProperties.getWebsocket().getAllowedOrigins());
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // 使用新的构造方法创建序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
        
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        
        return template;
    }
} 