package com.lawfirm.core.message.sender.impl;

import com.lawfirm.core.message.service.WebSocketService;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.enums.MessageSenderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * WebSocket消息发送实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketMessageSender implements MessageSender {
    
    private final WebSocketService webSocketService;
    
    @Override
    public boolean send(MessageEntity message) {
        try {
            webSocketService.sendMessage(message);
            log.info("WebSocket message sent successfully: {}", message.getId());
            return true;
        } catch (Exception e) {
            log.error("Failed to send WebSocket message: {}", message.getId(), e);
            return false;
        }
    }

    @Override
    public MessageSenderType getType() {
        return MessageSenderType.WEBSOCKET;
    }
} 