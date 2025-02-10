package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.config.MessageProperties;
import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.core.message.model.Message;
import com.lawfirm.core.message.model.MessageTemplate;
import com.lawfirm.core.message.model.MessageType;
import com.lawfirm.core.message.model.UserMessageSetting;
import com.lawfirm.core.message.mq.MessageProducer;
import com.lawfirm.core.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 消息服务实现
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    
    private final MessageProperties messageProperties;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MessageProducer messageProducer;
    
    public MessageServiceImpl(MessageProperties messageProperties,
                            RedisTemplate<String, Object> redisTemplate,
                            MessageProducer messageProducer) {
        this.messageProperties = messageProperties;
        this.redisTemplate = redisTemplate;
        this.messageProducer = messageProducer;
    }
    
    @Override
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String sendMessage(Message message) {
        try {
            // 1. 设置消息ID和时间
            message.setId(UUID.randomUUID().toString())
                    .setType(MessageType.NORMAL)
                    .setCreateTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());
            
            // 2. 发送消息到 MQ
            messageProducer.sendMessage(message);
            
            // 3. 保存到Redis
            saveMessageToRedis(message);
            
            return message.getId();
        } catch (Exception e) {
            log.error("Failed to send message: {}", message, e);
            throw new MessageException("消息发送失败", e);
        }
    }
    
    private void saveMessageToRedis(Message message) {
        try {
            String messageKey = messageProperties.getRedis().getMessageKeyPrefix() + message.getReceiverId();
            redisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    operations.opsForZSet().add(messageKey, message,
                            message.getCreateTime().toEpochSecond(java.time.ZoneOffset.UTC));
                    operations.expire(messageKey, 7, TimeUnit.DAYS);
                    return operations.exec();
                }
            });
        } catch (Exception e) {
            log.error("Failed to save message to Redis: {}", message.getId(), e);
            // 这里不抛出异常，因为消息已经发送到MQ
        }
    }
    
    @Override
    public String sendTemplateMessage(String templateCode, Map<String, Object> params,
                                    Long receiverId, String businessType, String businessId) {
        // 1. 获取消息模板
        MessageTemplate template = getMessageTemplate(templateCode);
        if (template == null || !template.getEnabled()) {
            throw new MessageException("消息模板不存在或已禁用: " + templateCode);
        }
        
        // 2. 替换模板参数
        String title = replaceTemplateParams(template.getTitle(), params);
        String content = replaceTemplateParams(template.getContent(), params);
        
        // 3. 创建消息
        Message message = new Message()
                .setType(MessageType.TEMPLATE)
                .setTitle(title)
                .setContent(content)
                .setTemplateId(template.getId())
                .setTemplateParams(params)
                .setReceiverId(receiverId)
                .setBusinessType(businessType)
                .setBusinessId(businessId);
        
        return sendMessage(message);
    }
    
    private MessageTemplate getMessageTemplate(String templateCode) {
        try {
            String templateKey = messageProperties.getRedis().getTemplateKeyPrefix() + templateCode;
            return (MessageTemplate) redisTemplate.opsForValue().get(templateKey);
        } catch (Exception e) {
            log.error("Failed to get message template: {}", templateCode, e);
            throw new MessageException("获取消息模板失败", e);
        }
    }
    
    private String replaceTemplateParams(String template, Map<String, Object> params) {
        if (StringUtils.hasText(template) && params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue() != null ? String.valueOf(entry.getValue()) : "";
                template = template.replace("${" + key + "}", value);
            }
        }
        return template;
    }
    
    @Override
    public List<String> sendSystemNotice(String title, String content, List<Long> receiverIds) {
        if (receiverIds == null || receiverIds.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 批量创建系统通知消息
        List<Message> messages = receiverIds.stream()
                .map(receiverId -> new Message()
                        .setType(MessageType.SYSTEM)
                        .setTitle(title)
                        .setContent(content)
                        .setReceiverId(receiverId)
                        .setPriority(9))
                .collect(Collectors.toList());
        
        // 批量发送
        return messages.stream()
                .map(this::sendMessage)
                .collect(Collectors.toList());
    }
    
    @Override
    public void markAsRead(String messageId, Long userId) {
        try {
            String messageKey = messageProperties.getRedis().getMessageKeyPrefix() + userId;
            redisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    Set<Object> messages = operations.opsForZSet().range(messageKey, 0, -1);
                    if (messages != null) {
                        for (Object obj : messages) {
                            Message message = (Message) obj;
                            if (messageId.equals(message.getId())) {
                                message.setRead(true)
                                        .setReadTime(LocalDateTime.now())
                                        .setUpdateTime(LocalDateTime.now());
                                operations.opsForZSet().add(messageKey, message,
                                        message.getCreateTime().toEpochSecond(java.time.ZoneOffset.UTC));
                                break;
                            }
                        }
                    }
                    return operations.exec();
                }
            });
        } catch (Exception e) {
            log.error("Failed to mark message as read: {}", messageId, e);
            throw new MessageException("标记消息已读失败", e);
        }
    }
    
    @Override
    public void markAsRead(List<String> messageIds, Long userId) {
        messageIds.forEach(messageId -> markAsRead(messageId, userId));
    }
    
    @Override
    public long getUnreadCount(Long userId) {
        String messageKey = messageProperties.getRedis().getMessageKeyPrefix() + userId;
        Set<Object> messages = redisTemplate.opsForZSet().range(messageKey, 0, -1);
        if (messages == null) {
            return 0;
        }
        return messages.stream()
                .map(obj -> (Message) obj)
                .filter(message -> !message.getRead())
                .count();
    }
    
    @Override
    public List<Message> listMessages(Long userId, int page, int size) {
        String messageKey = messageProperties.getRedis().getMessageKeyPrefix() + userId;
        Set<Object> messages = redisTemplate.opsForZSet().reverseRange(messageKey,
                (page - 1) * size, page * size - 1);
        if (messages == null) {
            return Collections.emptyList();
        }
        return messages.stream()
                .map(obj -> (Message) obj)
                .collect(Collectors.toList());
    }
    
    @Override
    public String createTemplate(MessageTemplate template) {
        template.setId(UUID.randomUUID().toString())
                .setEnabled(true)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        
        String templateKey = messageProperties.getRedis().getTemplateKeyPrefix() + template.getCode();
        redisTemplate.opsForValue().set(templateKey, template);
        
        return template.getId();
    }
    
    @Override
    public void updateTemplate(MessageTemplate template) {
        template.setUpdateTime(LocalDateTime.now());
        String templateKey = messageProperties.getRedis().getTemplateKeyPrefix() + template.getCode();
        redisTemplate.opsForValue().set(templateKey, template);
    }
    
    @Override
    public void deleteTemplate(String templateId) {
        String templatePattern = messageProperties.getRedis().getTemplateKeyPrefix() + "*";
        Set<String> keys = redisTemplate.keys(templatePattern);
        if (keys != null) {
            for (String key : keys) {
                MessageTemplate template = (MessageTemplate) redisTemplate.opsForValue().get(key);
                if (template != null && templateId.equals(template.getId())) {
                    redisTemplate.delete(key);
                    break;
                }
            }
        }
    }
    
    @Override
    public MessageTemplate getTemplate(String templateId) {
        String templatePattern = messageProperties.getRedis().getTemplateKeyPrefix() + "*";
        Set<String> keys = redisTemplate.keys(templatePattern);
        if (keys != null) {
            for (String key : keys) {
                MessageTemplate template = (MessageTemplate) redisTemplate.opsForValue().get(key);
                if (template != null && templateId.equals(template.getId())) {
                    return template;
                }
            }
        }
        return null;
    }
    
    @Override
    public UserMessageSetting getUserSetting(Long userId) {
        String settingKey = messageProperties.getRedis().getSettingKeyPrefix() + userId;
        UserMessageSetting setting = (UserMessageSetting) redisTemplate.opsForValue().get(settingKey);
        if (setting == null) {
            setting = new UserMessageSetting()
                    .setUserId(userId)
                    .setCreateTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());
            redisTemplate.opsForValue().set(settingKey, setting);
        }
        return setting;
    }
    
    @Override
    public void updateUserSetting(UserMessageSetting setting) {
        setting.setUpdateTime(LocalDateTime.now());
        String settingKey = messageProperties.getRedis().getSettingKeyPrefix() + setting.getUserId();
        redisTemplate.opsForValue().set(settingKey, setting);
    }
    
    @Override
    public void subscribe(Long userId, String clientId) {
        String subscriptionKey = messageProperties.getRedis().getSubscriptionKeyPrefix() + userId;
        redisTemplate.opsForSet().add(subscriptionKey, clientId);
        
        // 更新最后活跃时间
        UserMessageSetting setting = getUserSetting(userId);
        setting.setLastActiveTime(LocalDateTime.now());
        updateUserSetting(setting);
    }
    
    @Override
    public void unsubscribe(Long userId, String clientId) {
        String subscriptionKey = messageProperties.getRedis().getSubscriptionKeyPrefix() + userId;
        redisTemplate.opsForSet().remove(subscriptionKey, clientId);
    }
} 