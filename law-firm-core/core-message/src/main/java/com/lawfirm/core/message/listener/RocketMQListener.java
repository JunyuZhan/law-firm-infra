package com.lawfirm.core.message.listener;

import com.lawfirm.model.message.entity.base.BaseMessage;

/**
 * RocketMQ消息监听接口
 * 定义了处理RocketMQ消息的规范
 */
public interface RocketMQListener {
    
    /**
     * 处理消息
     * @param messageId 消息ID
     * @param message 消息内容
     */
    void handleMessage(String messageId, BaseMessage message);
    
    /**
     * 处理错误
     * @param messageId 消息ID
     * @param error 错误信息
     * @param e 异常
     */
    void handleError(String messageId, String error, Exception e);
} 