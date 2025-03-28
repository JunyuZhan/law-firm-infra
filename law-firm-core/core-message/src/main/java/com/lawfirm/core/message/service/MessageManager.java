package com.lawfirm.core.message.service;

import com.lawfirm.core.message.handler.template.BaseMessageHandler;
import com.lawfirm.model.message.enums.MessageTypeEnum;

/**
 * 消息管理服务接口
 * 定义消息处理和消息处理器注册相关功能
 */
public interface MessageManager {
    
    /**
     * 处理消息
     * 
     * @param messageId 消息ID
     */
    void processMessage(String messageId);
    
    /**
     * 注册消息处理器
     * 
     * @param type 消息类型
     * @param handler 消息处理器
     */
    void registerHandler(MessageTypeEnum type, BaseMessageHandler<?> handler);
} 