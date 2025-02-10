package com.lawfirm.core.message.sender.impl;

import com.lawfirm.common.message.EmailService;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 邮件消息发送实现
 */
@Component
@RequiredArgsConstructor
public class EmailMessageSender implements MessageSender {
    
    private final EmailService emailService;
    
    @Override
    public void send(MessageEntity message) {
        emailService.send(message.getReceiverName(), message.getTitle(), message.getContent(), true);
    }
    
    @Override
    public MessageSenderType getType() {
        return MessageSenderType.EMAIL;
    }
} 