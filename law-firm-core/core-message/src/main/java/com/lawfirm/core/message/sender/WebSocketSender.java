package com.lawfirm.core.message.sender;

import com.lawfirm.common.message.WebSocketService;
import com.lawfirm.core.message.websocket.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * WebSocket消息发送实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketSender implements WebSocketService {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void send(String userId, Object message) {
        try {
            webSocketHandler.sendToUser(userId, message);
            log.info("WebSocket message sent successfully: userId={}", userId);
        } catch (Exception e) {
            log.error("Failed to send WebSocket message", e);
            throw new RuntimeException("Failed to send WebSocket message", e);
        }
    }
} 