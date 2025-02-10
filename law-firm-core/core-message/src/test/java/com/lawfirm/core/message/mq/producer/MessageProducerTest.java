package com.lawfirm.core.message.mq.producer;

import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.enums.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    private MessageProducer messageProducer;

    @BeforeEach
    void setUp() {
        messageProducer = new MessageProducer(rabbitTemplate);
    }

    @Test
    void sendMessage_Success() {
        // Arrange
        MessageEntity message = createTestMessage();

        // Act
        messageProducer.sendMessage(message);

        // Assert
        verify(rabbitTemplate).convertAndSend(eq("message.direct"), eq("message.all"), any(MessageEntity.class));
    }

    @Test
    void sendMessage_Failure() {
        // Arrange
        MessageEntity message = createTestMessage();
        doThrow(new RuntimeException("Test exception")).when(rabbitTemplate)
                .convertAndSend(anyString(), anyString(), any(MessageEntity.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> messageProducer.sendMessage(message));
    }

    @Test
    void sendDelayMessage_Success() {
        // Arrange
        MessageEntity message = createTestMessage();
        long delayMillis = 1000L;

        // Act
        messageProducer.sendDelayMessage(message, delayMillis);

        // Assert
        verify(rabbitTemplate).convertAndSend(eq("message.delay"), eq("message.delay"), 
                any(MessageEntity.class), any());
    }

    @Test
    void sendDelayMessage_Failure() {
        // Arrange
        MessageEntity message = createTestMessage();
        long delayMillis = 1000L;
        doThrow(new RuntimeException("Test exception")).when(rabbitTemplate)
                .convertAndSend(anyString(), anyString(), any(MessageEntity.class), any());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> messageProducer.sendDelayMessage(message, delayMillis));
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
} 