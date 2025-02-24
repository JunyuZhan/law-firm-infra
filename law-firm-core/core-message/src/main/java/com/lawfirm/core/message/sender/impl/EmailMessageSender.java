package com.lawfirm.core.message.sender.impl;

import com.lawfirm.common.message.EmailService;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.enums.MessageSenderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 邮件消息发送实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailMessageSender implements MessageSender {
    
    private final EmailService emailService;
    
    @Override
    public boolean send(MessageEntity message) {
        try {
            emailService.send(message.getReceiverName(), message.getTitle(), message.getContent(), true);
            log.info("Email sent successfully: {}", message.getId());
            return true;
        } catch (Exception e) {
            log.error("Failed to send email: {}", message.getId(), e);
            return false;
        }
    }
    
    @Override
    public MessageSenderType getType() {
        return MessageSenderType.EMAIL;
    }
} 