package com.lawfirm.core.message.sender.impl;

import com.lawfirm.common.message.WechatService;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.enums.MessageSenderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信消息发送实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WechatMessageSender implements MessageSender {
    
    private final WechatService wechatService;
    
    @Override
    public boolean send(MessageEntity message) {
        try {
            wechatService.sendTemplateMessage(message.getReceiverName(), message.getTitle(), message.getContent());
            log.info("WeChat message sent successfully: {}", message.getId());
            return true;
        } catch (Exception e) {
            log.error("Failed to send WeChat message: {}", message.getId(), e);
            return false;
        }
    }
    
    @Override
    public MessageSenderType getType() {
        return MessageSenderType.WECHAT;
    }
} 