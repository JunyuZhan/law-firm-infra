package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.service.WebSocketService;
import com.lawfirm.core.message.websocket.WebSocketHandler;
import com.lawfirm.model.base.message.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * WebSocket 消息服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void sendMessage(MessageEntity message) {
        if (message.getReceiverId() != null) {
            webSocketHandler.sendToUser(message.getReceiverId().toString(), message);
            log.info("WebSocket message sent to user: {}", message.getReceiverId());
        } else {
            log.warn("Cannot send WebSocket message: receiverId is null");
        }
    }

    @Override
    public void sendToUser(String userId, Object message) {
        webSocketHandler.sendToUser(userId, message);
        log.info("WebSocket message sent to user: {}", userId);
    }

    @Override
    public void broadcast(Object message) {
        webSocketHandler.broadcast(message);
        log.info("WebSocket message broadcasted to all users");
    }
} 