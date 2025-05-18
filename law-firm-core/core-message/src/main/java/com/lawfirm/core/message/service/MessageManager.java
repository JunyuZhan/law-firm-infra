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

    /**
     * 获取未读消息数量
     * @param userId 用户ID
     * @param type 消息类型
     * @param businessId 业务ID
     * @return 未读消息数量
     */
    int getUnreadMessageCount(Long userId, String type, String businessId);

    /**
     * 获取消息列表
     * @param type 消息类型
     * @param businessId 业务ID
     * @param page 页码
     * @param size 每页大小
     * @return 消息列表
     */
    java.util.List<Object> getMessages(String type, String businessId, int page, int size);

    /**
     * 标记消息为已读
     * @param userId 用户ID
     * @param messageIds 消息ID列表
     */
    void markMessagesAsRead(Long userId, java.util.List<String> messageIds);
} 