package com.lawfirm.common.message;

/**
 * WebSocket消息发送基础服务接口
 */
public interface WebSocketService {
    
    /**
     * 发送WebSocket消息
     *
     * @param userId 用户ID
     * @param message 消息内容
     */
    void send(String userId, Object message);
} 