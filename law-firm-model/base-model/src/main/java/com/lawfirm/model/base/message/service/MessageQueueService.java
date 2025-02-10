package com.lawfirm.model.base.message.service;

import com.lawfirm.model.base.message.entity.MessageEntity;
import java.time.LocalDateTime;

/**
 * 消息队列服务接口
 */
public interface MessageQueueService {
    
    /**
     * 发送消息到队列
     */
    void sendToQueue(MessageEntity message);

    /**
     * 发送延迟消息到队列
     */
    void sendDelayMessage(MessageEntity message, LocalDateTime scheduledTime);
} 