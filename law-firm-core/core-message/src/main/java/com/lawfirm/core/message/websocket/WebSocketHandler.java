package com.lawfirm.core.message.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * WebSocket消息处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 发送消息给指定用户
     *
     * @param userId 用户ID
     * @param message 消息内容
     */
    public void sendToUser(String userId, Object message) {
        try {
            messagingTemplate.convertAndSendToUser(userId, "/queue/messages", message);
        } catch (Exception e) {
            log.error("Failed to send WebSocket message to user: {}", userId, e);
        }
    }

    /**
     * 广播消息
     *
     * @param message 消息内容
     */
    public void broadcast(Object message) {
        try {
            messagingTemplate.convertAndSend("/topic/broadcast", message);
        } catch (Exception e) {
            log.error("Failed to broadcast WebSocket message", e);
        }
    }
} 