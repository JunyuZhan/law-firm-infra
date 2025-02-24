package com.lawfirm.core.message.mq.producer;

import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.model.base.message.entity.MessageEntity;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 消息生产者
 */
@Slf4j
@Component
public class MessageProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {
    
    @Value("${message.queue.exchange.direct:message.direct}")
    private String directExchange;
    
    @Value("${message.queue.exchange.delay:message.delay}")
    private String delayExchange;
    
    @Value("${message.queue.name.direct:message.all}")
    private String directRoutingKey;
    
    @Value("${message.queue.name.delay:message.delay}")
    private String delayRoutingKey;
    
    private final RabbitTemplate rabbitTemplate;
    
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData != null) {
            log.info("消息发送确认: correlationData={}, ack={}, cause={}", correlationData.getId(), ack, cause);
        }
        if (!ack) {
            log.error("消息发送失败: cause={}", cause);
            throw new MessageException("消息发送失败: " + cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息发送失败: message={}, replyCode={}, replyText={}, exchange={}, routingKey={}",
                returned.getMessage(), returned.getReplyCode(), returned.getReplyText(),
                returned.getExchange(), returned.getRoutingKey());
        throw new MessageException("消息发送失败: " + returned.getReplyText());
    }
    
    /**
     * 发送消息
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void sendMessage(MessageEntity message) {
        try {
            String correlationId = UUID.randomUUID().toString();
            CorrelationData correlationData = new CorrelationData(correlationId);
            
            MessageProperties properties = new MessageProperties();
            properties.setCorrelationId(correlationId);
            properties.setPriority(message.getPriority());
            
            Message amqpMessage = rabbitTemplate.getMessageConverter()
                    .toMessage(message, properties);
            
            rabbitTemplate.convertAndSend(directExchange, directRoutingKey, amqpMessage, correlationData);
            log.info("消息发送成功: exchange={}, routingKey={}, message={}, correlationData={}", 
                    directExchange, directRoutingKey, message, correlationData.getId());
            
        } catch (Exception e) {
            log.error("消息发送异常: exchange={}, routingKey={}, message={}, error={}", 
                    directExchange, directRoutingKey, message, e.getMessage(), e);
            throw new MessageException("消息发送异常: " + e.getMessage(), e);
        }
    }
    
    /**
     * 发送延迟消息
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void sendDelayMessage(MessageEntity message, long delayMillis) {
        if (delayMillis <= 0) {
            throw new MessageException("延迟时间必须大于0");
        }
        
        try {
            String correlationId = UUID.randomUUID().toString();
            CorrelationData correlationData = new CorrelationData(correlationId);
            
            MessageProperties properties = new MessageProperties();
            properties.setCorrelationId(correlationId);
            properties.setPriority(message.getPriority());
            properties.setDelay((int) delayMillis);
            
            Message amqpMessage = rabbitTemplate.getMessageConverter()
                    .toMessage(message, properties);
            
            rabbitTemplate.convertAndSend(delayExchange, delayRoutingKey, amqpMessage,
                    msg -> {
                        msg.getMessageProperties().setDelay((int) delayMillis);
                        return msg;
                    },
                    correlationData);
            
            log.info("延迟消息发送成功: exchange={}, routingKey={}, message={}, delay={}, correlationData={}", 
                    delayExchange, delayRoutingKey, message, delayMillis, correlationData.getId());
            
        } catch (Exception e) {
            log.error("延迟消息发送异常: exchange={}, routingKey={}, message={}, delay={}, error={}", 
                    delayExchange, delayRoutingKey, message, delayMillis, e.getMessage(), e);
            throw new MessageException("延迟消息发送异常: " + e.getMessage(), e);
        }
    }
} 