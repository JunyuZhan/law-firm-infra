package com.lawfirm.core.message.service;

import com.lawfirm.model.base.message.entity.MessageEntity;

/**
 * WebSocket 消息服务接口
 */
public interface WebSocketService {
    /**
     * 发送 WebSocket 消息
     * @param message 消息实体
     */
    void sendMessage(MessageEntity message);
    
    /**
     * 发送消息给指定用户
     * @param userId 用户ID
     * @param message 消息内容
     */
    void sendToUser(String userId, Object message);
    
    /**
     * 广播消息给所有在线用户
     * @param message 消息内容
     */
    void broadcast(Object message);
} 