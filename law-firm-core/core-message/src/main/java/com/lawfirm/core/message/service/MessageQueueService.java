package com.lawfirm.core.message.service;

import com.lawfirm.model.base.message.entity.MessageEntity;

import java.time.LocalDateTime;

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
     * 重试发送消息
     */
    void retryMessage(String messageId);

    /**
     * 取消延迟消息
     */
    void cancelDelayMessage(String messageId);
} 