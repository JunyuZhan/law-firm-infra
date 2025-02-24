package com.lawfirm.core.message.sender;

import com.lawfirm.core.message.model.Message;

/**
 * 消息发送器接口
 */
public interface MessageSender {
    /**
     * 发送消息
     *
     * @param message 消息对象
     * @return 是否发送成功
     */
    boolean send(Message message);

    /**
     * 获取发送器类型
     *
     * @return 发送器类型
     */
    MessageSenderType getType();
} 