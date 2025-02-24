package com.lawfirm.core.message.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 消息处理器
 */
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    
    private final ObjectMapper objectMapper;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    
    public WebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = getUserId(session);
        sessions.put(userId, session);
        log.info("WebSocket connection established: userId={}", userId);
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String userId = getUserId(session);
        log.debug("Received message from userId={}: {}", userId, message.getPayload());
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = getUserId(session);
        sessions.remove(userId);
        log.info("WebSocket connection closed: userId={}, status={}", userId, status);
    }
    
    /**
     * 发送消息给指定用户
     */
    public void sendToUser(String userId, Object message) {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String messageText = objectMapper.writeValueAsString(message);
                session.sendMessage(new TextMessage(messageText));
                log.debug("Message sent to userId={}: {}", userId, messageText);
            } catch (IOException e) {
                log.error("Failed to send message to userId={}", userId, e);
            }
        } else {
            log.debug("User not connected: userId={}", userId);
        }
    }
    
    /**
     * 广播消息给所有在线用户
     */
    public void broadcast(Object message) {
        String messageText;
        try {
            messageText = objectMapper.writeValueAsString(message);
        } catch (IOException e) {
            log.error("Failed to serialize broadcast message", e);
            return;
        }
        
        sessions.forEach((userId, session) -> {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(messageText));
                    log.debug("Broadcast message sent to userId={}", userId);
                } catch (IOException e) {
                    log.error("Failed to broadcast message to userId={}", userId, e);
                }
            }
        });
    }
    
    private String getUserId(WebSocketSession session) {
        return session.getPrincipal() != null ? 
            session.getPrincipal().getName() : session.getId();
    }
} 