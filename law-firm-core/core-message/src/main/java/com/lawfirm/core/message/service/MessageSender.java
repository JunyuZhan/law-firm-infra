package com.lawfirm.core.message.service;

import com.lawfirm.model.message.entity.base.BaseMessage;

/**
 * 消息发送接口
 */
public interface MessageSender {

    /**
     * 发送消息
     * @param message 消息对象
     */
    void send(BaseMessage message);

    /**
     * 获取消息
     * @param messageId 消息ID
     * @return 消息对象
     */
    BaseMessage getMessage(String messageId);

    /**
     * 删除消息
     * @param messageId 消息ID
     */
    void deleteMessage(String messageId);

    /**
     * 批量删除消息
     * @param messageIds 消息ID列表
     */
    void deleteMessages(java.util.List<Long> messageIds);
} 