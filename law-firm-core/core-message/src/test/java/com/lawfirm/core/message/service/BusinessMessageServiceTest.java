package com.lawfirm.core.message.service;

import com.lawfirm.common.message.EmailService;
import com.lawfirm.common.message.SmsService;
import com.lawfirm.common.message.WechatService;
import com.lawfirm.common.message.WebSocketService;
import com.lawfirm.core.message.service.impl.BusinessMessageServiceImpl;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import com.lawfirm.model.base.message.enums.MessageType;
import com.lawfirm.model.base.message.repository.MessageRepository;
import com.lawfirm.model.base.message.repository.MessageTemplateRepository;
import com.lawfirm.model.base.message.repository.UserMessageSettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessMessageServiceTest {

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageTemplateRepository templateRepository;
    @Mock
    private UserMessageSettingRepository settingRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private SmsService smsService;
    @Mock
    private WechatService wechatService;
    @Mock
    private WebSocketService webSocketService;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private SetOperations<String, String> setOperations;

    private BusinessMessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        messageService = new BusinessMessageServiceImpl(
                messageRepository,
                templateRepository,
                settingRepository,
                emailService,
                smsService,
                wechatService,
                webSocketService,
                redisTemplate
        );
    }

    @Test
    void sendMessage_Success() {
        // Arrange
        MessageEntity message = createTestMessage();
        UserMessageSettingEntity setting = createTestSetting();
        when(settingRepository.findByUserIdAndType(anyLong(), any())).thenReturn(setting);
        when(messageRepository.save(any())).thenReturn(message);

        // Act
        String messageId = messageService.sendMessage(message);

        // Assert
        assertNotNull(messageId);
        verify(messageRepository).save(any());
        verify(webSocketService).send(anyString(), any());
        verify(emailService).send(anyString(), anyString(), anyString(), anyBoolean());
        verify(smsService).send(anyString(), anyString(), isNull());
    }

    @Test
    void sendTemplateMessage_Success() {
        // Arrange
        MessageTemplateEntity template = createTestTemplate();
        when(templateRepository.findByCodeAndEnabledTrue(anyString())).thenReturn(template);
        when(messageRepository.save(any())).thenReturn(new MessageEntity());

        // Act
        String messageId = messageService.sendTemplateMessage(
                "TEST_CODE",
                Map.of("key", "value"),
                1L,
                "TEST_TYPE",
                "TEST_ID"
        );

        // Assert
        assertNotNull(messageId);
        verify(templateRepository).findByCodeAndEnabledTrue(anyString());
        verify(messageRepository).save(any());
    }

    @Test
    void listMessages_Success() {
        // Arrange
        List<MessageEntity> messages = List.of(createTestMessage());
        Page<MessageEntity> page = new PageImpl<>(messages);
        when(messageRepository.findByReceiverId(anyLong(), any())).thenReturn(page);

        // Act
        Page<MessageEntity> result = messageService.listMessages(1L, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(messageRepository).findByReceiverId(eq(1L), any());
    }

    private MessageEntity createTestMessage() {
        MessageEntity message = new MessageEntity();
        message.setId("test-id");
        message.setTitle("Test Title");
        message.setContent("Test Content");
        message.setType(MessageType.SYSTEM_NOTICE);
        message.setReceiverId(1L);
        message.setCreateTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());
        return message;
    }

    private UserMessageSettingEntity createTestSetting() {
        UserMessageSettingEntity setting = new UserMessageSettingEntity();
        setting.setUserId(1L);
        setting.setType(MessageType.SYSTEM_NOTICE);
        setting.setReceiveSiteMessage(true);
        setting.setReceiveEmail(true);
        setting.setReceiveSms(true);
        setting.setReceiveWechat(false);
        return setting;
    }

    private MessageTemplateEntity createTestTemplate() {
        MessageTemplateEntity template = new MessageTemplateEntity();
        template.setId("test-template-id");
        template.setCode("TEST_CODE");
        template.setName("Test Template");
        template.setContent("Test Content ${key}");
        template.setType(MessageType.SYSTEM_NOTICE);
        template.setEnabled(true);
        return template;
    }
} 