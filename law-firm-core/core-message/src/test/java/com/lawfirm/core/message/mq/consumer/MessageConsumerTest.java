package com.lawfirm.core.message.mq.consumer;

import com.lawfirm.core.message.websocket.WebSocketHandler;
import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.enums.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageConsumerTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private WebSocketHandler webSocketHandler;
    @Mock
    private ZSetOperations<String, Object> zSetOperations;
    @Mock
    private SetOperations<String, Object> setOperations;

    private MessageConsumer messageConsumer;

    @BeforeEach
    void setUp() {
        messageConsumer = new MessageConsumer(redisTemplate, webSocketHandler);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(redisTemplate.opsForSet()).thenReturn(setOperations);
    }

    @Test
    void handleMessage_Success() {
        // Arrange
        MessageEntity message = createTestMessage();
        Set<Object> clientIds = Set.of("client1", "client2");
        when(setOperations.members(anyString())).thenReturn(clientIds);

        // Act
        messageConsumer.handleMessage(message);

        // Assert
        verify(zSetOperations).add(anyString(), any(), anyDouble());
        verify(redisTemplate).expire(anyString(), any());
        verify(webSocketHandler, times(2)).sendToUser(anyString(), any());
    }

    @Test
    void handleMessage_NoSubscribers() {
        // Arrange
        MessageEntity message = createTestMessage();
        when(setOperations.members(anyString())).thenReturn(null);

        // Act
        messageConsumer.handleMessage(message);

        // Assert
        verify(zSetOperations).add(anyString(), any(), anyDouble());
        verify(redisTemplate).expire(anyString(), any());
        verify(webSocketHandler, never()).sendToUser(anyString(), any());
    }

    @Test
    void handleMessage_Failure() {
        // Arrange
        MessageEntity message = createTestMessage();
        when(zSetOperations.add(anyString(), any(), anyDouble()))
                .thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> messageConsumer.handleMessage(message));
    }

    @Test
    void handleDelayMessage_Success() {
        // Arrange
        MessageEntity message = createTestMessage();
        Set<Object> clientIds = Set.of("client1");
        when(setOperations.members(anyString())).thenReturn(clientIds);

        // Act
        messageConsumer.handleDelayMessage(message);

        // Assert
        verify(zSetOperations).add(anyString(), any(), anyDouble());
        verify(redisTemplate).expire(anyString(), any());
        verify(webSocketHandler).sendToUser(anyString(), any());
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