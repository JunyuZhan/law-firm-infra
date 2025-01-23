package com.lawfirm.core.message.mq;

import com.lawfirm.core.message.config.MessageProperties;
import com.lawfirm.core.message.model.Message;
import com.lawfirm.core.message.websocket.MessageWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 消息消费者
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${message.rocketmq.topic}",
        consumerGroup = "${message.rocketmq.consumer-group}"
)
public class MessageConsumer implements RocketMQListener<Message> {
    
    private final MessageProperties messageProperties;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MessageWebSocketHandler webSocketHandler;
    
    public MessageConsumer(MessageProperties messageProperties,
                          RedisTemplate<String, Object> redisTemplate,
                          MessageWebSocketHandler webSocketHandler) {
        this.messageProperties = messageProperties;
        this.redisTemplate = redisTemplate;
        this.webSocketHandler = webSocketHandler;
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            // 1. 保存消息到 Redis
            String messageKey = messageProperties.getRedis().getMessageKeyPrefix() + message.getReceiverId();
            redisTemplate.opsForZSet().add(messageKey, message, 
                    message.getCreateTime().toEpochSecond(java.time.ZoneOffset.UTC));
            
            // 2. 设置过期时间
            redisTemplate.expire(messageKey, 
                    messageProperties.getRedis().getMessageExpireDays(), 
                    java.util.concurrent.TimeUnit.DAYS);
            
            // 3. 获取用户订阅的客户端
            String subscriptionKey = messageProperties.getRedis().getSubscriptionKeyPrefix() + message.getReceiverId();
            Set<Object> clientIds = redisTemplate.opsForSet().members(subscriptionKey);
            
            // 4. 推送消息到在线客户端
            if (clientIds != null) {
                for (Object clientId : clientIds) {
                    webSocketHandler.sendMessage((String) clientId, message);
                }
            }
            
            log.info("Message consumed successfully: id={}, type={}", message.getId(), message.getType());
        } catch (Exception e) {
            log.error("Failed to consume message: " + message.getId(), e);
            throw new RuntimeException("Failed to consume message", e);
        }
    }
} 