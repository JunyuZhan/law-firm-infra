package com.lawfirm.core.message.integration;

import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.enums.MessageType;
import com.lawfirm.model.base.message.service.BusinessMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MessageServiceIntegrationTest {

    @Autowired
    private BusinessMessageService messageService;

    @Test
    void messageCrudOperations() {
        // 1. 创建消息
        MessageEntity message = new MessageEntity();
        message.setTitle("Test Message");
        message.setContent("Test Content");
        message.setType(MessageType.SYSTEM_NOTICE);
        message.setReceiverId(1L);

        String messageId = messageService.sendMessage(message);
        assertNotNull(messageId);

        // 2. 获取消息列表
        Page<MessageEntity> messages = messageService.listMessages(1L, 0, 10);
        assertTrue(messages.getTotalElements() > 0);

        // 3. 标记消息已读
        messageService.markAsRead(messageId, 1L);
        long unreadCount = messageService.getUnreadCount(1L);
        assertEquals(0, unreadCount);
    }

    @Test
    void templateMessageOperations() {
        // 1. 创建消息模板
        MessageTemplateEntity template = new MessageTemplateEntity();
        template.setCode("TEST_TEMPLATE");
        template.setName("Test Template");
        template.setContent("Hello ${name}");
        template.setType(MessageType.SYSTEM_NOTICE);
        template.setEnabled(true);
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());

        String templateId = messageService.createTemplate(template);
        assertNotNull(templateId);

        // 2. 发送模板消息
        String messageId = messageService.sendTemplateMessage(
                "TEST_TEMPLATE",
                Map.of("name", "John"),
                1L,
                "TEST",
                "123"
        );
        assertNotNull(messageId);

        // 3. 获取模板
        MessageTemplateEntity savedTemplate = messageService.getTemplate(templateId);
        assertEquals("TEST_TEMPLATE", savedTemplate.getCode());
    }
} 