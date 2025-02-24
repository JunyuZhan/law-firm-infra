package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.sender.MessageSenderFactory;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import com.lawfirm.model.base.message.enums.MessageSenderType;
import com.lawfirm.model.base.message.repository.UserMessageSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息发送策略服务
 * 根据用户设置选择合适的发送器
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSendStrategyService {
    
    private final MessageSenderFactory senderFactory;
    private final UserMessageSettingRepository settingRepository;
    
    /**
     * 根据用户设置发送消息
     *
     * @param message 消息实体
     * @return 发送成功的发送器类型列表
     */
    public List<MessageSenderType> sendMessageByUserSettings(MessageEntity message) {
        List<MessageSenderType> successTypes = new ArrayList<>();
        UserMessageSettingEntity setting = settingRepository.findByUserIdAndType(
            message.getReceiverId(), message.getType());
            
        if (setting == null) {
            // 如果没有设置，默认使用站内信
            sendWithType(message, MessageSenderType.SITE_MESSAGE, successTypes);
            return successTypes;
        }
        
        // 根据用户设置选择发送器
        if (Boolean.TRUE.equals(setting.getReceiveSiteMessage())) {
            sendWithType(message, MessageSenderType.SITE_MESSAGE, successTypes);
        }
        
        if (Boolean.TRUE.equals(setting.getReceiveEmail())) {
            sendWithType(message, MessageSenderType.EMAIL, successTypes);
        }
        
        if (Boolean.TRUE.equals(setting.getReceiveSms())) {
            sendWithType(message, MessageSenderType.SMS, successTypes);
        }
        
        if (Boolean.TRUE.equals(setting.getReceiveWechat())) {
            sendWithType(message, MessageSenderType.WECHAT, successTypes);
        }
        
        return successTypes;
    }
    
    private void sendWithType(MessageEntity message, MessageSenderType type, 
                            List<MessageSenderType> successTypes) {
        try {
            MessageSender sender = senderFactory.getSender(type);
            if (sender.send(message)) {
                successTypes.add(type);
                log.info("Message sent successfully with type {}: {}", type, message.getId());
            } else {
                log.warn("Failed to send message with type {}: {}", type, message.getId());
            }
        } catch (Exception e) {
            log.error("Error sending message with type {}: {}", type, message.getId(), e);
        }
    }
} 