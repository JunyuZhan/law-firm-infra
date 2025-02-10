package com.lawfirm.common.message.service;

import com.lawfirm.common.message.constant.MessageConstants;
import com.lawfirm.common.message.exception.MessageException;
import com.lawfirm.common.message.handler.MessageIdempotentHandler;
import com.lawfirm.common.message.handler.MessageRateLimiter;
import com.lawfirm.common.message.handler.MessageRetryHandler;
import com.lawfirm.common.message.metrics.MessageMetrics;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Map;

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
    protected final MessageRetryHandler retryHandler;
    
    protected AbstractMessageService(
            MessageCacheService messageCacheService,
            MessageQueueService messageQueueService,
            RedisTemplate<String, Object> redisTemplate,
            MessageMetrics messageMetrics,
            MessageIdempotentHandler idempotentHandler,
            MessageRateLimiter rateLimiter,
            MessageRetryHandler retryHandler) {
        this.messageCacheService = messageCacheService;
        this.messageQueueService = messageQueueService;
        this.redisTemplate = redisTemplate;
        this.messageMetrics = messageMetrics;
        this.idempotentHandler = idempotentHandler;
        this.rateLimiter = rateLimiter;
        this.retryHandler = retryHandler;
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
                messageId = java.util.UUID.randomUUID().toString().replace("-", "");
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
     * 发送消息到指定渠道
     */
    protected void sendWithSender(MessageEntity message, MessageSender sender, MessageSender.MessageSenderType type) {
        if (sender != null) {
            try {
                retryHandler.doWithRetry(message, sender::send);
            } catch (Exception e) {
                log.error("Failed to send message with type {} after retries: {}", type, message.getId(), e);
                messageMetrics.recordMessageSendFailure(System.currentTimeMillis());
            }
        } else {
            log.warn("Message sender not found for type: {}", type);
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

    /**
     * 根据用户设置发送消息
     */
    @Async("messageTaskExecutor")
    protected void sendMessageBySettings(MessageEntity message, UserMessageSettingEntity setting, 
            Map<MessageSender.MessageSenderType, MessageSender> messageSenders) {
        try {
            // 发送站内信
            if (Boolean.TRUE.equals(setting.getReceiveSiteMessage())) {
                sendWithSender(message, messageSenders.get(MessageSender.MessageSenderType.SITE_MESSAGE), 
                        MessageSender.MessageSenderType.SITE_MESSAGE);
            }

            // 发送邮件
            if (Boolean.TRUE.equals(setting.getReceiveEmail())) {
                sendWithSender(message, messageSenders.get(MessageSender.MessageSenderType.EMAIL),
                        MessageSender.MessageSenderType.EMAIL);
            }

            // 发送短信
            if (Boolean.TRUE.equals(setting.getReceiveSms())) {
                sendWithSender(message, messageSenders.get(MessageSender.MessageSenderType.SMS),
                        MessageSender.MessageSenderType.SMS);
            }

            // 发送微信消息
            if (Boolean.TRUE.equals(setting.getReceiveWechat())) {
                sendWithSender(message, messageSenders.get(MessageSender.MessageSenderType.WECHAT),
                        MessageSender.MessageSenderType.WECHAT);
            }
        } catch (Exception e) {
            log.error("Failed to send message asynchronously: {}", message.getId(), e);
            messageMetrics.recordMessageSendFailure(System.currentTimeMillis());
            throw e;
        }
    }

    /**
     * 发送撤回通知
     */
    protected void sendRecallNotification(MessageEntity message, String messageId, 
            Map<MessageSender.MessageSenderType, MessageSender> messageSenders) {
        try {
            MessageEntity recallMessage = new MessageEntity();
            recallMessage.setId(messageId);
            recallMessage.setReceiverId(message.getReceiverId());
            recallMessage.setContent(Map.of("type", "RECALL", "messageId", messageId).toString());
            
            sendWithSender(recallMessage, 
                    messageSenders.get(MessageSender.MessageSenderType.SITE_MESSAGE),
                    MessageSender.MessageSenderType.SITE_MESSAGE);
        } catch (Exception e) {
            log.error("Failed to send recall notification: {}", messageId, e);
            // 撤回通知发送失败不影响消息撤回操作
        }
    }
} 