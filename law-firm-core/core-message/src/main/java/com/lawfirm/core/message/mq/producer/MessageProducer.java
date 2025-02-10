package com.lawfirm.core.message.mq.producer;

import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.model.base.message.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * 消息生产者
 */
@Slf4j
@Component
public class MessageProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    
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
        rabbitTemplate.setReturnCallback(this);
    }
    
    /**
     * 发送消息
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
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
            log.info("Message sent successfully: id={}, type={}, correlation={}", 
                    message.getId(), message.getType(), correlationId);
            
        } catch (Exception e) {
            log.error("Failed to send message: " + message.getId(), e);
            throw new MessageException("消息发送失败", e);
        }
    }
    
    /**
     * 发送延迟消息
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
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
            
            log.info("Delay message sent successfully: id={}, type={}, delay={}ms, correlation={}", 
                    message.getId(), message.getType(), delayMillis, correlationId);
            
        } catch (Exception e) {
            log.error("Failed to send delay message: " + message.getId(), e);
            throw new MessageException("延迟消息发送失败", e);
        }
    }
    
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData != null) {
            if (ack) {
                log.info("Message confirmed: correlation={}", correlationData.getId());
            } else {
                log.error("Message not confirmed: correlation={}, cause={}", correlationData.getId(), cause);
                // TODO: 实现消息发送失败的补偿机制，如重试或保存到数据库等
            }
        }
    }
    
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
                              String exchange, String routingKey) {
        String correlationId = message.getMessageProperties().getCorrelationId();
        log.error("Message returned: correlation={}, exchange={}, routingKey={}, replyCode={}, replyText={}",
                correlationId, exchange, routingKey, replyCode, replyText);
        // TODO: 实现消息返回的处理逻辑，如重试或保存到数据库等
    }
} 