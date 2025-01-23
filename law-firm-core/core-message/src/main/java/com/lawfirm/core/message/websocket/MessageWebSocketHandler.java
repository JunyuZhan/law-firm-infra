package com.lawfirm.core.message.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.core.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 消息处理器
 */
@Slf4j
@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {
    
    private final MessageService messageService;
    private final ObjectMapper objectMapper;
    
    /**
     * 会话存储
     * key: clientId
     * value: session
     */
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    
    public MessageWebSocketHandler(MessageService messageService, ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String clientId = getClientId(session);
        Long userId = getUserId(session);
        
        sessions.put(clientId, session);
        messageService.subscribe(userId, clientId);
        
        log.info("WebSocket connected: clientId={}, userId={}", clientId, userId);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String clientId = getClientId(session);
        Long userId = getUserId(session);
        
        sessions.remove(clientId);
        messageService.unsubscribe(userId, clientId);
        
        log.info("WebSocket disconnected: clientId={}, userId={}, status={}", clientId, userId, status);
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 处理客户端发送的消息，这里主要用于心跳
        log.debug("Received message from client: {}", message.getPayload());
    }
    
    /**
     * 发送消息到客户端
     */
    public void sendMessage(String clientId, Object payload) {
        WebSocketSession session = sessions.get(clientId);
        if (session != null && session.isOpen()) {
            try {
                String message = objectMapper.writeValueAsString(payload);
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                log.error("Failed to send message to client: " + clientId, e);
            }
        }
    }
    
    /**
     * 获取客户端ID
     */
    private String getClientId(WebSocketSession session) {
        return session.getId();
    }
    
    /**
     * 获取用户ID
     */
    private Long getUserId(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        return (Long) attributes.get("userId");
    }
} 