package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.config.MessageIdempotentHandler;
import com.lawfirm.core.message.config.MessageRateLimiter;
import com.lawfirm.core.message.constant.MessageConstants;
import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.core.message.metrics.MessageMetrics;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import com.lawfirm.model.base.message.repository.MessageRepository;
import com.lawfirm.model.base.message.repository.MessageTemplateRepository;
import com.lawfirm.model.base.message.repository.UserMessageSettingRepository;
import com.lawfirm.model.base.message.service.BusinessMessageService;
import com.lawfirm.model.base.message.service.MessageCacheService;
import com.lawfirm.model.base.message.service.MessageQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 抽象消息服务
 * 实现底层的BusinessMessageService接口
 */
@Slf4j
public abstract class AbstractMessageService implements BusinessMessageService {
    
    @Autowired
    protected MessageRepository messageRepository;
    
    @Autowired
    protected MessageTemplateRepository templateRepository;
    
    @Autowired
    protected UserMessageSettingRepository settingRepository;
    
    protected final MessageCacheService messageCacheService;
    protected final MessageQueueService messageQueueService;
    protected final RedisTemplate<String, Object> redisTemplate;
    protected final MessageMetrics messageMetrics;
    protected final MessageIdempotentHandler idempotentHandler;
    protected final MessageRateLimiter rateLimiter;
    
    protected AbstractMessageService(
            MessageCacheService messageCacheService,
            MessageQueueService messageQueueService,
            RedisTemplate<String, Object> redisTemplate,
            MessageMetrics messageMetrics,
            MessageIdempotentHandler idempotentHandler,
            MessageRateLimiter rateLimiter) {
        this.messageCacheService = messageCacheService;
        this.messageQueueService = messageQueueService;
        this.redisTemplate = redisTemplate;
        this.messageMetrics = messageMetrics;
        this.idempotentHandler = idempotentHandler;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public String sendMessage(MessageEntity message) {
        return processMessageSend(message, null);
    }

    @Override
    public String sendTemplateMessage(String templateCode, Map<String, Object> params, Long receiverId, String businessType, String businessId) {
        MessageTemplateEntity template = templateRepository.findByCodeAndEnabledTrue(templateCode);
        if (template == null) {
            throw new MessageException(MessageConstants.ERROR_CODE_TEMPLATE_NOT_FOUND, "消息模板不存在或未启用");
        }
        return processTemplateMessage(template, params, receiverId, businessType, businessId);
    }
    
    /**
     * 消息发送后的处理
     * 由子类实现具体的处理逻辑
     */
    protected abstract void afterMessageSent(MessageEntity message);

    /**
     * 处理消息发送
     */
    protected String processMessageSend(MessageEntity message, LocalDateTime scheduledTime) {
        long startTime = System.currentTimeMillis();
        try {
            // 1. 限流检查
            if (!rateLimiter.tryAcquire(message.getReceiverId().toString())) {
                throw new MessageException("Message rate limit exceeded");
            }
            
            // 2. 幂等性检查
            String messageId = message.getId();
            if (messageId != null && idempotentHandler.isProcessed(messageId)) {
                log.info("Message already processed: {}", messageId);
                return messageId;
            }
            
            // 3. 校验消息
            validateMessage(message);
            
            // 4. 设置消息基本信息
            if (messageId == null) {
                messageId = UUID.randomUUID().toString().replace("-", "");
                message.setId(messageId);
            }
            message.setCreateTime(LocalDateTime.now());
            message.setUpdateTime(LocalDateTime.now());
            message.setIsRead(false);
            
            // 5. 保存消息到数据库
            messageRepository.save(message);
            
            // 6. 发送消息
            if (scheduledTime != null) {
                messageQueueService.sendDelayMessage(message, scheduledTime);
            } else {
                // 6.1 缓存消息
                messageCacheService.cacheMessage(message);
                
                // 6.2 发送到消息队列
                messageQueueService.sendToQueue(message);
            }
            
            // 6.3 调用消息发送后的处理
            afterMessageSent(message);
            
            // 7. 标记消息已处理
            idempotentHandler.markAsProcessed(messageId);
            
            // 8. 记录指标
            messageMetrics.recordMessageSend(startTime);
            
            log.info("Message processed successfully: id={}, type={}, scheduled={}", 
                    messageId, message.getType(), scheduledTime);
            return messageId;
            
        } catch (Exception e) {
            log.error("Failed to process message", e);
            messageMetrics.recordMessageSendFailure(startTime);
            throw new MessageException("Failed to send message: " + e.getMessage(), e);
        }
    }
    
    /**
     * 处理模板消息
     */
    protected String processTemplateMessage(MessageTemplateEntity template, Map<String, Object> params,
                                         Long receiverId, String businessType, String businessId) {
        // 1. 替换模板参数
        String title = replaceTemplateParams(template.getTitle(), params);
        String content = replaceTemplateParams(template.getContent(), params);
        
        // 2. 创建消息实体
        MessageEntity message = new MessageEntity();
        message.setType(template.getType());
        message.setTitle(title);
        message.setContent(content);
        message.setTemplateId(template.getId());
        message.setParams(params.toString());
        message.setReceiverId(receiverId);
        message.setBusinessType(businessType);
        message.setBusinessId(businessId);
        
        // 3. 发送消息
        return processMessageSend(message, null);
    }
    
    /**
     * 校验消息
     */
    protected void validateMessage(MessageEntity message) {
        if (message == null) {
            throw new MessageException(MessageConstants.ERROR_CODE_INVALID_PARAMETER, "消息不能为空");
        }
        if (message.getReceiverId() == null) {
            throw new MessageException(MessageConstants.ERROR_CODE_INVALID_PARAMETER, "接收者ID不能为空");
        }
        if (message.getType() == null) {
            throw new MessageException(MessageConstants.ERROR_CODE_INVALID_PARAMETER, "消息类型不能为空");
        }
        if (!StringUtils.hasText(message.getContent()) && message.getTemplateId() == null) {
            throw new MessageException(MessageConstants.ERROR_CODE_INVALID_PARAMETER, "消息内容和模板ID不能同时为空");
        }
    }
    
    /**
     * 替换模板参数
     */
    protected String replaceTemplateParams(String template, Map<String, Object> params) {
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
    public void markAsRead(String messageId, Long userId) {
        MessageEntity message = messageRepository.findById(messageId).orElse(null);
        if (message != null && message.getReceiverId().equals(userId)) {
            message.setIsRead(true);
            message.setReadTime(LocalDateTime.now());
            messageRepository.save(message);
            messageCacheService.markAsRead(messageId, userId);
        }
    }

    @Override
    public long getUnreadCount(Long userId) {
        return messageRepository.countByReceiverIdAndIsReadFalse(userId);
    }

    @Override
    public void updateTemplate(MessageTemplateEntity template) {
        templateRepository.save(template);
    }

    @Override
    public MessageTemplateEntity getTemplate(String templateId) {
        return templateRepository.findById(templateId).orElse(null);
    }

    @Override
    public List<UserMessageSettingEntity> getUserSettings(Long userId) {
        return settingRepository.findByUserId(userId);
    }

    @Override
    public void updateUserSetting(UserMessageSettingEntity setting) {
        settingRepository.save(setting);
    }

    @Override
    public List<String> sendSystemNotice(String title, String content, List<Long> receiverIds) {
        return receiverIds.stream()
                .map(receiverId -> {
                    MessageEntity message = new MessageEntity();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(receiverId);
                    message.setType(MessageType.SYSTEM_NOTICE);
                    return sendMessage(message);
                })
                .toList();
    }

    @Override
    public void markAsRead(List<String> messageIds, Long userId) {
        messageIds.forEach(messageId -> markAsRead(messageId, userId));
    }

    @Override
    public Page<MessageEntity> listMessages(Long userId, int page, int size) {
        return messageRepository.findByReceiverId(userId, PageRequest.of(page, size));
    }

    @Override
    public String createTemplate(MessageTemplateEntity template) {
        if (templateRepository.findByCode(template.getCode()) != null) {
            throw new MessageException(MessageConstants.ERROR_CODE_TEMPLATE_EXISTS, "模板编码已存在");
        }
        
        template.setId(UUID.randomUUID().toString().replace("-", ""));
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());
        templateRepository.save(template);
        return template.getId();
    }

    @Override
    public void deleteTemplate(String templateId) {
        templateRepository.deleteById(templateId);
    }

    @Override
    public void subscribe(Long userId, String clientId) {
        // 实现订阅逻辑
    }

    @Override
    public void unsubscribe(Long userId, String clientId) {
        // 实现取消订阅逻辑
    }
}