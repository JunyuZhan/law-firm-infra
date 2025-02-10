package com.lawfirm.common.message.service;

import com.lawfirm.model.base.message.entity.MessageEntity;

import java.time.LocalDateTime;

/**
 * 消息队列服务
 */
public interface MessageQueueService {
    
    /**
     * 发送消息到队列
     */
    void sendToQueue(MessageEntity message);
    
    /**
     * 发送延迟消息
     */
    void sendDelayMessage(MessageEntity message, LocalDateTime scheduledTime);
    
    /**
     * 处理消息
     */
    void processMessage(MessageEntity message);
    
    /**
     * 处理死信消息
     */
    void processDLQMessage(MessageEntity message);
} 