package com.lawfirm.core.message.mq;

import com.lawfirm.core.message.config.MessageProperties;
import com.lawfirm.core.message.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Slf4j
@Component
public class MessageProducer {
    
    private final RocketMQTemplate rocketMQTemplate;
    private final MessageProperties messageProperties;
    
    public MessageProducer(RocketMQTemplate rocketMQTemplate, MessageProperties messageProperties) {
        this.rocketMQTemplate = rocketMQTemplate;
        this.messageProperties = messageProperties;
    }
    
    /**
     * 发送消息
     */
    public void sendMessage(Message message) {
        String topic = messageProperties.getRocketmq().getTopic();
        try {
            rocketMQTemplate.syncSend(topic, 
                    MessageBuilder.withPayload(message)
                            .setHeader("priority", message.getPriority())
                            .build(),
                    3000, // 超时时间
                    message.getPriority() // 消息优先级作为延迟级别
            );
            log.info("Message sent successfully: id={}, type={}", message.getId(), message.getType());
        } catch (Exception e) {
            log.error("Failed to send message: " + message.getId(), e);
            throw new RuntimeException("Failed to send message", e);
        }
    }
} 