package com.lawfirm.core.message.enums;

/**
 * 消息发送器类型
 */
public enum MessageSenderType {
    /**
     * 站内信
     */
    SITE_MESSAGE,
    
    /**
     * 邮件
     */
    EMAIL,
    
    /**
     * 短信
     */
    SMS,
    
    /**
     * 微信
     */
    WECHAT,
    
    /**
     * WebSocket
     */
    WEBSOCKET
} 