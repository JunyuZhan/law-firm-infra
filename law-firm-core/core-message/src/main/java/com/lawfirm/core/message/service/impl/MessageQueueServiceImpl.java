package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.mq.producer.MessageProducer;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.service.MessageQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 消息队列服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageQueueServiceImpl implements MessageQueueService {

    private final MessageProducer messageProducer;

    @Override
    public void sendToQueue(MessageEntity message) {
        try {
            messageProducer.sendMessage(message);
            log.info("Message sent to queue: id={}, type={}", message.getId(), message.getType());
        } catch (Exception e) {
            log.error("Failed to send message to queue: " + message.getId(), e);
            throw new RuntimeException("Failed to send message to queue", e);
        }
    }

    @Override
    public void sendDelayMessage(MessageEntity message, LocalDateTime scheduledTime) {
        try {
            long delayMillis = scheduledTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli() 
                    - System.currentTimeMillis();
            if (delayMillis > 0) {
                messageProducer.sendDelayMessage(message, delayMillis);
                log.info("Delay message sent to queue: id={}, type={}, delay={}ms", 
                        message.getId(), message.getType(), delayMillis);
            } else {
                sendToQueue(message);
            }
        } catch (Exception e) {
            log.error("Failed to send delay message to queue: " + message.getId(), e);
            throw new RuntimeException("Failed to send delay message to queue", e);
        }
    }
} 