package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.config.MessageProperties;
import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.core.message.model.Message;
import com.lawfirm.core.message.model.MessageTemplate;
import com.lawfirm.core.message.model.UserMessageSetting;
import com.lawfirm.core.message.service.MessageService;
import com.lawfirm.model.base.message.service.BusinessMessageService;
import com.lawfirm.model.base.message.service.MessageCacheService;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import com.lawfirm.model.base.message.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 消息服务实现
 * 负责消息模型转换和业务处理委托
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    
    private final BusinessMessageService businessMessageService;
    private final MessageCacheService messageCacheService;
    
    @Override
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String sendMessage(MessageEntity message) {
        try {
            validateMessage(message);
            return businessMessageService.sendMessage(message);
        } catch (Exception e) {
            log.error("Failed to send message: {}", message, e);
            throw new RuntimeException("消息发送失败", e);
        }
    }
    
    @Override
    public String sendTemplateMessage(String templateCode, Map<String, Object> params,
                                    Long receiverId, String businessType, String businessId) {
        return businessMessageService.sendTemplateMessage(templateCode, params, receiverId, businessType, businessId);
    }
    
    @Override
    public List<String> sendSystemNotice(String title, String content, List<Long> receiverIds) {
        return businessMessageService.sendSystemNotice(title, content, receiverIds);
    }
    
    @Override
    public void markAsRead(String messageId, Long userId) {
        businessMessageService.markAsRead(messageId, userId);
    }
    
    @Override
    public void markAsRead(List<String> messageIds, Long userId) {
        businessMessageService.markAsRead(messageIds, userId);
    }
    
    @Override
    public long getUnreadCount(Long userId) {
        return businessMessageService.getUnreadCount(userId);
    }
    
    @Override
    public List<MessageEntity> listMessages(Long userId, int page, int size) {
        return businessMessageService.listMessages(userId, page, size);
    }
    
    @Override
    public String createTemplate(MessageTemplateEntity template) {
        return businessMessageService.createTemplate(template);
    }
    
    @Override
    public void updateTemplate(MessageTemplateEntity template) {
        businessMessageService.updateTemplate(template);
    }
    
    @Override
    public void deleteTemplate(String templateId) {
        businessMessageService.deleteTemplate(templateId);
    }
    
    @Override
    public MessageTemplateEntity getTemplate(String templateId) {
        return businessMessageService.getTemplate(templateId);
    }
    
    @Override
    public UserMessageSettingEntity getUserSetting(Long userId) {
        return businessMessageService.getUserSetting(userId);
    }
    
    @Override
    public void updateUserSetting(UserMessageSettingEntity setting) {
        businessMessageService.updateUserSetting(setting);
    }
    
    @Override
    public void subscribe(Long userId, String clientId) {
        businessMessageService.subscribe(userId, clientId);
    }
    
    @Override
    public void unsubscribe(Long userId, String clientId) {
        businessMessageService.unsubscribe(userId, clientId);
    }
    
    private void validateMessage(MessageEntity message) {
        if (message == null) {
            throw new IllegalArgumentException("消息不能为空");
        }
        if (message.getReceiverId() == null) {
            throw new IllegalArgumentException("接收者ID不能为空");
        }
        if (message.getType() == null) {
            throw new IllegalArgumentException("消息类型不能为空");
        }
        if (!StringUtils.hasText(message.getContent()) && message.getTemplateId() == null) {
            throw new IllegalArgumentException("消息内容和模板ID不能同时为空");
        }
    }
    
    // 类型转换方法
    private MessageEntity convertToEntity(Message message) {
        if (message == null) {
            return null;
        }
        MessageEntity entity = new MessageEntity();
        entity.setId(message.getId());
        entity.setType(message.getType().name());
        entity.setTitle(message.getTitle());
        entity.setContent(message.getContent());
        entity.setReceiverId(message.getReceiverId());
        entity.setTemplateId(message.getTemplateId());
        entity.setBusinessType(message.getBusinessType());
        entity.setBusinessId(message.getBusinessId());
        entity.setParams(message.getParams() != null ? message.getParams().toString() : null);
        entity.setCreateTime(message.getCreateTime());
        entity.setUpdateTime(message.getUpdateTime());
        entity.setIsRead(message.isRead());
        entity.setReadTime(message.getReadTime());
        return entity;
    }
    
    private Message convertToMessage(MessageEntity entity) {
        if (entity == null) {
            return null;
        }
        return Message.builder()
                .id(entity.getId())
                .type(MessageType.valueOf(entity.getType()))
                .title(entity.getTitle())
                .content(entity.getContent())
                .receiverId(entity.getReceiverId())
                .templateId(entity.getTemplateId())
                .businessType(entity.getBusinessType())
                .businessId(entity.getBusinessId())
                .params(entity.getParams() != null ? parseParams(entity.getParams()) : null)
                .createTime(entity.getCreateTime())
                .updateTime(entity.getUpdateTime())
                .isRead(entity.getIsRead())
                .readTime(entity.getReadTime())
                .build();
    }
    
    private Map<String, Object> parseParams(String params) {
        try {
            // 这里需要根据实际的参数格式实现解析逻辑
            return new HashMap<>();
        } catch (Exception e) {
            log.error("Failed to parse message params: {}", params, e);
            return new HashMap<>();
        }
    }
    
    private MessageTemplateEntity convertToTemplateEntity(MessageTemplate template) {
        if (template == null) {
            return null;
        }
        MessageTemplateEntity entity = new MessageTemplateEntity();
        entity.setId(template.getId());
        entity.setCode(template.getCode());
        entity.setName(template.getName());
        entity.setType(template.getType().name());
        entity.setTitle(template.getTitle());
        entity.setContent(template.getContent());
        entity.setEnabled(template.isEnabled());
        entity.setCreateTime(template.getCreateTime());
        entity.setUpdateTime(template.getUpdateTime());
        return entity;
    }
    
    private MessageTemplate convertToTemplate(MessageTemplateEntity entity) {
        if (entity == null) {
            return null;
        }
        return MessageTemplate.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .type(MessageType.valueOf(entity.getType()))
                .title(entity.getTitle())
                .content(entity.getContent())
                .enabled(entity.getEnabled())
                .createTime(entity.getCreateTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }
    
    private UserMessageSettingEntity convertToSettingEntity(UserMessageSetting setting) {
        if (setting == null) {
            return null;
        }
        UserMessageSettingEntity entity = new UserMessageSettingEntity();
        entity.setId(setting.getId());
        entity.setUserId(setting.getUserId());
        entity.setType(setting.getType().name());
        entity.setEnabled(setting.isEnabled());
        entity.setCreateTime(setting.getCreateTime());
        entity.setUpdateTime(setting.getUpdateTime());
        return entity;
    }
    
    private UserMessageSetting convertToSetting(UserMessageSettingEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserMessageSetting.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .type(MessageType.valueOf(entity.getType()))
                .enabled(entity.getEnabled())
                .createTime(entity.getCreateTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }
} 