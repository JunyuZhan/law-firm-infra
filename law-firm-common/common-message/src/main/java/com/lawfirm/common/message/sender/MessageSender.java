package com.lawfirm.common.message.sender;

import com.lawfirm.model.base.message.entity.MessageEntity;

/**
 * 消息发送策略接口
 */
public interface MessageSender {
    
    /**
     * 发送消息
     *
     * @param message 消息实体
     */
    void send(MessageEntity message);
    
    /**
     * 获取发送器类型
     *
     * @return 发送器类型
     */
    MessageSenderType getType();
    
    /**
     * 发送器类型枚举
     */
    enum MessageSenderType {
        SITE_MESSAGE,  // 站内信
        EMAIL,         // 邮件
        SMS,          // 短信
        WECHAT        // 微信
    }
} 