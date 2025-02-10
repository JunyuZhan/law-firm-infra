package com.lawfirm.core.message.sender.impl;

import com.lawfirm.common.message.WechatService;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 微信消息发送实现
 */
@Component
@RequiredArgsConstructor
public class WechatMessageSender implements MessageSender {
    
    private final WechatService wechatService;
    
    @Override
    public void send(MessageEntity message) {
        wechatService.sendTemplateMessage(message.getReceiverName(), message.getTitle(), message.getContent());
    }
    
    @Override
    public MessageSenderType getType() {
        return MessageSenderType.WECHAT;
    }
} 