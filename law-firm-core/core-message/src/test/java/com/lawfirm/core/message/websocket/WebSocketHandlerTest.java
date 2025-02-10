package com.lawfirm.core.message.websocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WebSocketHandlerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    private WebSocketHandler webSocketHandler;

    @BeforeEach
    void setUp() {
        webSocketHandler = new WebSocketHandler(messagingTemplate);
    }

    @Test
    void sendToUser_Success() {
        // Arrange
        String userId = "test-user";
        String message = "test message";

        // Act
        webSocketHandler.sendToUser(userId, message);

        // Assert
        verify(messagingTemplate).convertAndSendToUser(eq(userId), eq("/queue/messages"), any());
    }

    @Test
    void broadcast_Success() {
        // Arrange
        String message = "test broadcast message";

        // Act
        webSocketHandler.broadcast(message);

        // Assert
        verify(messagingTemplate).convertAndSend(eq("/topic/broadcast"), any());
    }
} 