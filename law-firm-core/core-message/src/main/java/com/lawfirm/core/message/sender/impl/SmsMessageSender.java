package com.lawfirm.core.message.sender.impl;

import com.lawfirm.common.message.SmsService;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 短信消息发送实现
 */
@Component
@RequiredArgsConstructor
public class SmsMessageSender implements MessageSender {
    
    private final SmsService smsService;
    
    @Override
    public void send(MessageEntity message) {
        smsService.send(message.getReceiverName(), message.getContent(), null);
    }
    
    @Override
    public MessageSenderType getType() {
        return MessageSenderType.SMS;
    }
} 