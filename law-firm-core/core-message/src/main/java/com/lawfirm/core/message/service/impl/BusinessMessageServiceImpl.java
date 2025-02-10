package com.lawfirm.core.message.service.impl;

import com.lawfirm.common.message.constant.MessageConstants;
import com.lawfirm.common.message.exception.MessageException;
import com.lawfirm.common.message.handler.MessageIdempotentHandler;
import com.lawfirm.common.message.handler.MessageRateLimiter;
import com.lawfirm.common.message.handler.MessageRetryHandler;
import com.lawfirm.common.message.metrics.MessageMetrics;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.common.message.service.AbstractMessageService;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务消息服务实现
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class BusinessMessageServiceImpl extends AbstractMessageService implements BusinessMessageService {

    private final MessageRepository messageRepository;
    private final MessageTemplateRepository templateRepository;
    private final UserMessageSettingRepository settingRepository;
    private final Map<MessageSender.MessageSenderType, MessageSender> messageSenders;

    public BusinessMessageServiceImpl(
            MessageCacheService messageCacheService,
            MessageQueueService messageQueueService,
            RedisTemplate<String, Object> redisTemplate,
            MessageMetrics messageMetrics,
            MessageIdempotentHandler idempotentHandler,
            MessageRateLimiter rateLimiter,
            MessageRetryHandler retryHandler,
            MessageRepository messageRepository,
            MessageTemplateRepository templateRepository,
            UserMessageSettingRepository settingRepository,
            List<MessageSender> messageSenders) {
        super(messageCacheService, messageQueueService, redisTemplate, messageMetrics, 
                idempotentHandler, rateLimiter, retryHandler);
        this.messageRepository = messageRepository;
        this.templateRepository = templateRepository;
        this.settingRepository = settingRepository;
        this.messageSenders = messageSenders.stream()
                .collect(Collectors.toMap(MessageSender::getType, sender -> sender));
    }

    @Override
    @Transactional
    public String sendMessage(MessageEntity message) {
        return processMessageSend(message, null);
    }

    @Override
    @Transactional
    public String sendMessage(MessageEntity message, LocalDateTime scheduledTime) {
        if (scheduledTime == null || scheduledTime.isBefore(LocalDateTime.now())) {
            return sendMessage(message);
        }
        return processMessageSend(message, scheduledTime);
    }

    @Override
    @Transactional
    public String sendTemplateMessage(String templateCode, Map<String, Object> params,
                                    Long receiverId, String businessType, String businessId) {
        MessageTemplateEntity template = templateRepository.findByCodeAndEnabledTrue(templateCode);
        if (template == null) {
            throw new MessageException.TemplateNotFoundException(templateCode);
        }
        return processTemplateMessage(template, params, receiverId, businessType, businessId);
    }

    @Override
    @Transactional
    public List<String> sendSystemNotice(String title, String content, List<Long> receiverIds) {
        List<String> messageIds = new ArrayList<>();
        for (Long receiverId : receiverIds) {
            MessageEntity message = new MessageEntity();
            message.setTitle(title);
            message.setContent(content);
            message.setReceiverId(receiverId);
            message.setPriority(MessageConstants.Priority.HIGH);
            messageIds.add(sendMessage(message));
        }
        return messageIds;
    }

    @Async("messageTaskExecutor")
    @Override
    public void recallMessage(String messageId, Long userId) {
        try {
            MessageEntity message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new MessageException.MessageNotFoundException(messageId));

            if (!userId.equals(message.getSenderId())) {
                throw new MessageException.UnauthorizedAccessException("No permission to recall message: " + messageId);
            }

            // 删除消息缓存
            messageCacheService.deleteMessageCache(messageId, message.getReceiverId());
            
            // 删除消息记录
            messageRepository.deleteById(messageId);
            
            // 发送消息撤回通知
            super.sendRecallNotification(message, messageId, messageSenders);
            
            log.info("Message recalled asynchronously: id={}, sender={}", messageId, userId);
        } catch (Exception e) {
            log.error("Failed to recall message asynchronously: {}", messageId, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void markAsRead(String messageId, Long userId) {
        MessageEntity message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageException.MessageNotFoundException(messageId));

        if (!userId.equals(message.getReceiverId())) {
            throw new MessageException.UnauthorizedAccessException("No permission to mark message as read: " + messageId);
        }

        message.setIsRead(true);
        message.setReadTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());
        messageRepository.save(message);

        // 更新缓存
        messageCacheService.markAsRead(messageId, userId);
        
        log.info("Message marked as read: id={}, reader={}", messageId, userId);
    }

    @Override
    public void subscribe(Long userId, String clientId) {
        String key = MessageConstants.RedisKeyPrefix.USER_SUBSCRIPTION + userId;
        redisTemplate.opsForSet().add(key, clientId);
        log.info("User subscribed: userId={}, clientId={}", userId, clientId);
    }

    @Override
    public void unsubscribe(Long userId, String clientId) {
        String key = MessageConstants.RedisKeyPrefix.USER_SUBSCRIPTION + userId;
        redisTemplate.opsForSet().remove(key, clientId);
        log.info("User unsubscribed: userId={}, clientId={}", userId, clientId);
    }

    @Async("messageTaskExecutor")
    protected void sendMessageBySettings(MessageEntity message, UserMessageSettingEntity setting) {
        super.sendMessageBySettings(message, setting, messageSenders);
    }
} 