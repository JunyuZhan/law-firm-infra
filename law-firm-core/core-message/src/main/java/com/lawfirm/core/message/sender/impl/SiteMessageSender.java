package com.lawfirm.core.message.sender.impl;

import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.enums.MessageSenderType;
import com.lawfirm.model.base.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 站内信消息发送实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SiteMessageSender implements MessageSender {
    
    private final MessageRepository messageRepository;
    
    @Override
    public boolean send(MessageEntity message) {
        try {
            messageRepository.save(message);
            log.info("Site message saved successfully: {}", message.getId());
            return true;
        } catch (Exception e) {
            log.error("Failed to save site message: {}", message.getId(), e);
            return false;
        }
    }
    
    @Override
    public MessageSenderType getType() {
        return MessageSenderType.SITE_MESSAGE;
    }
} 