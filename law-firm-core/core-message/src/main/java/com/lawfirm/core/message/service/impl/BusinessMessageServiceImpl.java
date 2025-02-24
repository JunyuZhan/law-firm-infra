package com.lawfirm.core.message.service.impl;

import com.lawfirm.core.message.config.MessageIdempotentHandler;
import com.lawfirm.core.message.config.MessageRateLimiter;
import com.lawfirm.core.message.sender.MessageSender;
import com.lawfirm.core.message.sender.MessageSenderType;
import com.lawfirm.core.message.model.Message;
import com.lawfirm.model.base.message.enums.MessageType;
import com.lawfirm.model.base.message.service.BusinessMessageService;
import com.lawfirm.core.message.constant.MessageConstants;
import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.core.message.metrics.MessageMetrics;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import com.lawfirm.model.base.message.repository.MessageRepository;
import com.lawfirm.model.base.message.repository.MessageTemplateRepository;
import com.lawfirm.model.base.message.repository.UserMessageSettingRepository;
import com.lawfirm.model.base.message.service.MessageCacheService;
import com.lawfirm.model.base.message.service.MessageQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 业务消息服务实现
 * 继承AbstractMessageService以复用通用的消息处理逻辑
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class BusinessMessageServiceImpl extends AbstractMessageService implements BusinessMessageService {

    private final MessageRepository messageRepository;
    private final MessageTemplateRepository templateRepository;
    private final UserMessageSettingRepository settingRepository;
    private final Map<MessageSenderType, MessageSender> messageSenders;

    public BusinessMessageServiceImpl(MessageRepository messageRepository,
                                    MessageTemplateRepository templateRepository,
                                    UserMessageSettingRepository settingRepository,
                                    MessageCacheService messageCacheService,
                                    MessageQueueService messageQueueService,
                                    RedisTemplate<String, Object> redisTemplate,
                                    MessageMetrics messageMetrics,
                                    MessageIdempotentHandler messageIdempotentHandler,
                                    MessageRateLimiter messageRateLimiter,
                                    Map<MessageSenderType, MessageSender> messageSenders) {
        super(messageCacheService, messageQueueService, redisTemplate, messageMetrics, 
              messageIdempotentHandler, messageRateLimiter);
        this.messageRepository = messageRepository;
        this.templateRepository = templateRepository;
        this.settingRepository = settingRepository;
        this.messageSenders = messageSenders;
    }

    @Override
    @Transactional
    public String sendMessage(MessageEntity message) {
        return processMessageSend(message, null);
    }

    @Override
    @Transactional
    public String sendMessage(MessageEntity message, LocalDateTime scheduledTime) {
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
        return super.sendSystemNotice(title, content, receiverIds);
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
            
            // 标记消息为已读
            message.setIsRead(true);
            messageRepository.save(message);
            
            // 发送消息撤回通知
            sendRecallNotification(message);
            
            log.info("Message recalled asynchronously: id={}, sender={}", messageId, userId);
        } catch (Exception e) {
            log.error("Failed to recall message asynchronously: {}", messageId, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void markAsRead(String messageId, Long userId) {
        super.markAsRead(messageId, userId);
    }

    @Override
    @Transactional
    public void markAsRead(List<String> messageIds, Long userId) {
        super.markAsRead(messageIds, userId);
    }

    @Override
    public long getUnreadCount(Long userId) {
        return super.getUnreadCount(userId);
    }

    @Override
    public Page<MessageEntity> listMessages(Long userId, int page, int size) {
        return messageRepository.findByReceiverId(userId, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public String createTemplate(MessageTemplateEntity template) {
        templateRepository.save(template);
        return template.getId();
    }

    @Override
    @Transactional
    public void updateTemplate(MessageTemplateEntity template) {
        super.updateTemplate(template);
    }

    @Override
    @Transactional
    public void deleteTemplate(String templateId) {
        templateRepository.deleteById(templateId);
    }

    @Override
    public MessageTemplateEntity getTemplate(String templateId) {
        return super.getTemplate(templateId);
    }

    @Override
    public List<UserMessageSettingEntity> getUserSettings(Long userId) {
        return settingRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void updateUserSetting(UserMessageSettingEntity setting) {
        super.updateUserSetting(setting);
    }

    @Override
    public void subscribe(Long userId, String clientId) {
        // 实现订阅逻辑
    }

    @Override
    public void unsubscribe(Long userId, String clientId) {
        // 实现取消订阅逻辑
    }

    /**
     * 发送消息撤回通知
     */
    protected void sendRecallNotification(MessageEntity message) {
        MessageEntity notification = new MessageEntity();
        notification.setType(MessageType.SYSTEM_NOTICE);
        notification.setTitle("消息撤回通知");
        notification.setContent("消息已被发送者撤回");
        notification.setReceiverId(message.getReceiverId());
        notification.setSenderId(message.getSenderId());
        sendMessage(notification);
    }

    /**
     * 消息发送后的处理
     */
    @Override
    protected void afterMessageSent(MessageEntity message) {
        try {
            // 获取用户消息设置
            List<UserMessageSettingEntity> settings = settingRepository.findByUserId(message.getReceiverId());
            if (settings == null || settings.isEmpty()) {
                log.warn("No message settings found for user: {}", message.getReceiverId());
                return;
            }

            // 根据用户设置发送消息
            for (UserMessageSettingEntity setting : settings) {
                sendMessageBySettings(message, setting);
            }
        } catch (Exception e) {
            log.error("Failed to process message: {}", message.getId(), e);
            throw new MessageException.MessageSendFailedException(
                "Failed to process message: " + e.getMessage(), e);
        }
    }

    /**
     * 根据用户设置发送消息
     */
    protected void sendMessageBySettings(MessageEntity message, UserMessageSettingEntity setting) {
        if (setting.getReceiveEmail()) {
            messageSenders.get(MessageSenderType.EMAIL).send(convertToMessage(message));
        }
        if (setting.getReceiveSms()) {
            messageSenders.get(MessageSenderType.SMS).send(convertToMessage(message));
        }
        if (setting.getReceiveWechat()) {
            messageSenders.get(MessageSenderType.WEBSOCKET).send(convertToMessage(message));
        }
    }

    /**
     * 将MessageEntity转换为Message
     */
    private Message convertToMessage(MessageEntity entity) {
        Message message = new Message();
        message.setId(Long.valueOf(entity.getId()));
        message.setType(convertMessageType(entity.getType()));
        message.setTitle(entity.getTitle());
        message.setContent(entity.getContent());
        message.setReceiverId(entity.getReceiverId());
        message.setSenderId(entity.getSenderId());
        message.setRead(entity.getIsRead());
        message.setCreateTime(entity.getCreateTime());
        message.setUpdateTime(entity.getUpdateTime());
        return message;
    }

    /**
     * 转换消息类型
     */
    private com.lawfirm.core.message.model.MessageType convertMessageType(MessageType type) {
        return com.lawfirm.core.message.model.MessageType.valueOf(type.name());
    }
} 