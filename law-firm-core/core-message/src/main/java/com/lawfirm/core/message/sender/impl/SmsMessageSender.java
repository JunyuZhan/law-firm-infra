package com.lawfirm.core.message.sender.impl;

import com.lawfirm.core.message.config.MessageProperties;
import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.enums.MessageSenderType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 短信消息发送实现
 */
@Slf4j
@Component
public class SmsMessageSender implements MessageSender {
    
    private final MessageProperties.Sms smsConfig;
    
    public SmsMessageSender(MessageProperties.Sms smsConfig) {
        this.smsConfig = smsConfig;
    }
    
    @Override
    public boolean send(MessageEntity message) {
        try {
            // 实现短信发送逻辑
            log.info("Sending SMS message: receiver={}, content={}", 
                message.getReceiverId(), message.getContent());
            
            // TODO: 调用短信服务商API
            return true;
            
        } catch (Exception e) {
            log.error("Failed to send SMS message: {}", message.getId(), e);
            return false;
        }
    }
    
    @Override
    public MessageSenderType getType() {
        return MessageSenderType.SMS;
    }
} 