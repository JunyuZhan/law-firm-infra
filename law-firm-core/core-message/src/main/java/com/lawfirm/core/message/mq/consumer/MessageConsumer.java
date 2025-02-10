package com.lawfirm.core.message.mq.consumer;

import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.core.message.metrics.MessageMetrics;
import com.lawfirm.core.message.websocket.WebSocketHandler;
import com.lawfirm.model.base.message.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 消息消费者
 */
@Slf4j
@Component
public class MessageConsumer {
    
    @Value("${message.queue.exchange.dlx:message.dlx}")
    private String dlxExchange;
    
    @Value("${message.queue.name.dlq:message.dlq}")
    private String dlqName;
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final WebSocketHandler webSocketHandler;
    private final RabbitTemplate rabbitTemplate;
    private final MessageMetrics messageMetrics;
    
    public MessageConsumer(RedisTemplate<String, Object> redisTemplate,
                          WebSocketHandler webSocketHandler,
                          RabbitTemplate rabbitTemplate,
                          MessageMetrics messageMetrics) {
        this.redisTemplate = redisTemplate;
        this.webSocketHandler = webSocketHandler;
        this.rabbitTemplate = rabbitTemplate;
        this.messageMetrics = messageMetrics;
    }
    
    /**
     * 处理实时消息
     */
    @RabbitListener(queues = "${message.queue.name.direct:message.all}", 
            containerFactory = "messageListenerContainerFactory")
    public void handleMessage(MessageEntity message, Message amqpMessage) {
        String messageId = message.getId();
        long startTime = System.currentTimeMillis();
        
        try {
            // 1. 幂等性检查
            String consumeKey = "message:consume:" + messageId;
            if (!redisTemplate.opsForValue().setIfAbsent(consumeKey, "1", 
                    Duration.ofDays(7))) {
                log.warn("Message already consumed: {}", messageId);
                return;
            }
            
            // 2. 保存消息到Redis
            saveMessageToRedis(message);
            
            // 3. 推送消息到WebSocket
            pushMessageToWebSocket(message);
            
            // 4. 记录消费成功
            long costTime = System.currentTimeMillis() - startTime;
            messageMetrics.recordMessageConsumed(message.getType(), costTime);
            log.info("Message consumed successfully: id={}, type={}, cost={}ms", 
                    messageId, message.getType(), costTime);
            
        } catch (Exception e) {
            // 5. 处理异常
            handleConsumptionError(message, amqpMessage, e);
        }
    }
    
    /**
     * 处理延迟消息
     */
    @RabbitListener(queues = "${message.queue.name.delay:message.delay}", 
            containerFactory = "messageListenerContainerFactory")
    public void handleDelayMessage(MessageEntity message, Message amqpMessage) {
        handleMessage(message, amqpMessage);
    }
    
    /**
     * 保存消息到Redis
     */
    private void saveMessageToRedis(MessageEntity message) {
        try {
            String messageKey = "message:user:" + message.getReceiverId();
            redisTemplate.execute(session -> {
                session.multi();
                session.opsForZSet().add(messageKey, message,
                        message.getCreateTime().toEpochSecond(java.time.ZoneOffset.UTC));
                session.expire(messageKey, 7, TimeUnit.DAYS);
                return session.exec();
            });
        } catch (Exception e) {
            log.error("Failed to save message to Redis: {}", message.getId(), e);
            throw new MessageException("保存消息到Redis失败", e);
        }
    }
    
    /**
     * 推送消息到WebSocket
     */
    private void pushMessageToWebSocket(MessageEntity message) {
        try {
            String subscriptionKey = "message:subscription:" + message.getReceiverId();
            Set<Object> clientIds = redisTemplate.opsForSet().members(subscriptionKey);
            
            if (clientIds != null && !clientIds.isEmpty()) {
                for (Object clientId : clientIds) {
                    try {
                        webSocketHandler.sendToUser((String) clientId, message);
                    } catch (Exception e) {
                        log.error("Failed to push message to WebSocket client: {}, message: {}", 
                                clientId, message.getId(), e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to push message to WebSocket: {}", message.getId(), e);
            throw new MessageException("推送消息到WebSocket失败", e);
        }
    }
    
    /**
     * 处理消费异常
     */
    private void handleConsumptionError(MessageEntity message, Message amqpMessage, Exception e) {
        String messageId = message.getId();
        log.error("Failed to consume message: " + messageId, e);
        
        // 1. 记录消费失败
        messageMetrics.recordMessageFailed(message.getType());
        
        // 2. 重试次数检查
        Integer retryCount = amqpMessage.getMessageProperties().getHeader("x-retry-count");
        if (retryCount == null) {
            retryCount = 0;
        }
        
        if (retryCount < 3) {
            // 3. 重试
            amqpMessage.getMessageProperties().setHeader("x-retry-count", retryCount + 1);
            rabbitTemplate.send(amqpMessage.getMessageProperties().getReceivedExchange(),
                    amqpMessage.getMessageProperties().getReceivedRoutingKey(),
                    amqpMessage);
            log.info("Message requeued for retry: {}, retry count: {}", messageId, retryCount + 1);
        } else {
            // 4. 发送到死信队列
            rabbitTemplate.send(dlxExchange, dlqName, amqpMessage);
            log.warn("Message sent to DLQ: {}, after {} retries", messageId, retryCount);
        }
    }
} 