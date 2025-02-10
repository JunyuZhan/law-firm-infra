package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.config.MessageIdempotentConfig.MessageIdempotentHandler;
import com.lawfirm.core.message.config.MessageRateLimitConfig.MessageRateLimiter;
import com.lawfirm.core.message.constant.MessageConstants;
import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.core.message.metrics.MessageMetrics;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import com.lawfirm.model.base.message.service.MessageCacheService;
import com.lawfirm.model.base.message.service.MessageQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * 抽象消息服务
 */
@Slf4j
public abstract class AbstractMessageService {
    
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
    
    /**
     * 处理消息发送
     */
    protected String processMessageSend(MessageEntity message, LocalDateTime scheduledTime) {
        long startTime = System.currentTimeMillis();
        try {
            // 1. 限流检查
            if (!rateLimiter.tryConsume()) {
                throw new MessageException.RateLimitExceededException("Message rate limit exceeded");
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
            message.setStatus(MessageConstants.Status.PENDING);
            
            // 5. 发送消息
            if (scheduledTime != null) {
                messageQueueService.sendDelayMessage(message, scheduledTime);
            } else {
                // 5.1 缓存消息
                messageCacheService.cacheMessage(message);
                
                // 5.2 发送到消息队列
                messageQueueService.sendToQueue(message);
            }
            
            // 6. 标记消息已处理
            idempotentHandler.markAsProcessed(messageId);
            
            // 7. 记录指标
            messageMetrics.recordMessageSend(startTime);
            
            log.info("Message processed successfully: id={}, type={}, scheduled={}", 
                    messageId, message.getType(), scheduledTime);
            return messageId;
            
        } catch (Exception e) {
            log.error("Failed to process message", e);
            messageMetrics.recordMessageSendFailure(startTime);
            throw new MessageException.SendMessageFailedException("Failed to send message", e);
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
        message.setPriority(template.getPriority());
        
        // 3. 发送消息
        return processMessageSend(message, null);
    }
    
    /**
     * 校验消息
     */
    protected void validateMessage(MessageEntity message) {
        if (message.getReceiverId() == null) {
            throw new MessageException.InvalidMessageException("Receiver ID is required");
        }
        if (!StringUtils.hasText(message.getContent()) && message.getTemplateId() == null) {
            throw new MessageException.InvalidMessageException("Content or template ID is required");
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
    
    /**
     * 获取用户消息设置
     */
    protected UserMessageSettingEntity getUserSetting(Long userId) {
        String key = MessageConstants.RedisKeyPrefix.USER_SETTING + userId;
        return (UserMessageSettingEntity) redisTemplate.opsForValue().get(key);
    }
} 