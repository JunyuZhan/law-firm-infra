package com.lawfirm.core.message.sender.impl;

import com.lawfirm.common.message.WebSocketService;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * WebSocket消息发送实现
 */
@Component
@RequiredArgsConstructor
public class WebSocketMessageSender implements MessageSender {
    
    private final WebSocketService webSocketService;
    
    @Override
    public void send(MessageEntity message) {
        webSocketService.send(message.getReceiverId().toString(), message);
    }
    
    @Override
    public MessageSenderType getType() {
        return MessageSenderType.SITE_MESSAGE;
    }
} 