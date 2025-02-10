package com.lawfirm.core.message.service;

import com.lawfirm.common.message.handler.MessageIdempotentHandler;
import com.lawfirm.common.message.handler.MessageRateLimiter;
import com.lawfirm.common.message.handler.MessageRetryHandler;
import com.lawfirm.common.message.metrics.MessageMetrics;
import com.lawfirm.common.message.sender.MessageSender;
import com.lawfirm.core.message.service.impl.BusinessMessageServiceImpl;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.repository.MessageRepository;
import com.lawfirm.model.base.message.repository.MessageTemplateRepository;
import com.lawfirm.model.base.message.repository.UserMessageSettingRepository;
import com.lawfirm.model.base.message.service.MessageCacheService;
import com.lawfirm.model.base.message.service.MessageQueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BusinessMessageServiceImplTest {

    private BusinessMessageServiceImpl messageService;

    @Mock private MessageCacheService messageCacheService;
    @Mock private MessageQueueService messageQueueService;
    @Mock private RedisTemplate<String, Object> redisTemplate;
    @Mock private MessageMetrics messageMetrics;
    @Mock private MessageIdempotentHandler idempotentHandler;
    @Mock private MessageRateLimiter rateLimiter;
    @Mock private MessageRepository messageRepository;
    @Mock private MessageTemplateRepository templateRepository;
    @Mock private UserMessageSettingRepository settingRepository;
    @Mock private MessageRetryHandler retryHandler;
    @Mock private MessageSender siteMessageSender;
    @Mock private MessageSender emailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        when(siteMessageSender.getType()).thenReturn(MessageSender.MessageSenderType.SITE_MESSAGE);
        when(emailSender.getType()).thenReturn(MessageSender.MessageSenderType.EMAIL);
        
        messageService = new BusinessMessageServiceImpl(
                messageCacheService,
                messageQueueService,
                redisTemplate,
                messageMetrics,
                idempotentHandler,
                rateLimiter,
                messageRepository,
                templateRepository,
                settingRepository,
                List.of(siteMessageSender, emailSender),
                retryHandler
        );
        
        when(rateLimiter.tryConsume()).thenReturn(true);
    }

    @Test
    void shouldSendMessage() {
        MessageEntity message = new MessageEntity();
        message.setContent("Test content");
        message.setReceiverId(1L);

        String messageId = messageService.sendMessage(message);

        assertNotNull(messageId);
        verify(messageCacheService).cacheMessage(any(MessageEntity.class));
        verify(messageQueueService).sendToQueue(any(MessageEntity.class));
    }

    @Test
    void shouldSendTemplateMessage() {
        MessageTemplateEntity template = new MessageTemplateEntity();
        template.setId("template-1");
        template.setCode("TEST_TEMPLATE");
        template.setTitle("Hello ${name}");
        template.setContent("Welcome ${name}!");
        
        when(templateRepository.findByCodeAndEnabledTrue("TEST_TEMPLATE"))
                .thenReturn(template);

        String messageId = messageService.sendTemplateMessage(
                "TEST_TEMPLATE",
                Map.of("name", "John"),
                1L,
                "TEST",
                "123"
        );

        assertNotNull(messageId);
        verify(messageCacheService).cacheMessage(any(MessageEntity.class));
        verify(messageQueueService).sendToQueue(any(MessageEntity.class));
    }

    @Test
    void shouldRecallMessage() {
        MessageEntity message = new MessageEntity();
        message.setId("message-1");
        message.setSenderId(1L);
        message.setReceiverId(2L);
        
        when(messageRepository.findById("message-1"))
                .thenReturn(Optional.of(message));

        messageService.recallMessage("message-1", 1L);

        verify(messageCacheService).deleteMessageCache("message-1", 2L);
        verify(messageRepository).deleteById("message-1");
        verify(siteMessageSender).send(any(MessageEntity.class));
    }

    @Test
    void shouldMarkMessageAsRead() {
        MessageEntity message = new MessageEntity();
        message.setId("message-1");
        message.setReceiverId(1L);
        
        when(messageRepository.findById("message-1"))
                .thenReturn(Optional.of(message));

        messageService.markAsRead("message-1", 1L);

        verify(messageRepository).save(any(MessageEntity.class));
        verify(messageCacheService).markAsRead("message-1", 1L);
        assertTrue(message.getIsRead());
        assertNotNull(message.getReadTime());
    }

    @Test
    void shouldHandleScheduledMessage() {
        MessageEntity message = new MessageEntity();
        message.setContent("Scheduled message");
        message.setReceiverId(1L);
        
        LocalDateTime scheduledTime = LocalDateTime.now().plusHours(1);

        String messageId = messageService.sendMessage(message, scheduledTime);

        assertNotNull(messageId);
        verify(messageQueueService).sendDelayMessage(any(MessageEntity.class), eq(scheduledTime));
    }
} 