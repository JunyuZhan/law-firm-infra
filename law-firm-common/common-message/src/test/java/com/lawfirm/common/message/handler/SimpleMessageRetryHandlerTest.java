package com.lawfirm.common.message.handler;

import com.lawfirm.common.message.handler.impl.SimpleMessageRetryHandler;
import com.lawfirm.model.base.message.entity.MessageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimpleMessageRetryHandlerTest {

    private SimpleMessageRetryHandler retryHandler;
    
    @Mock
    private MessageEntity message;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        retryHandler = new SimpleMessageRetryHandler(3, 100, 2.0, 1000);
        when(message.getId()).thenReturn("test-message-id");
    }
    
    @Test
    void shouldSucceedOnFirstAttempt() throws Exception {
        MessageRetryHandler.RetryCallback callback = mock(MessageRetryHandler.RetryCallback.class);
        
        retryHandler.doWithRetry(message, callback);
        
        verify(callback, times(1)).execute(message);
    }
    
    @Test
    void shouldRetryAndEventuallySucceed() throws Exception {
        MessageRetryHandler.RetryCallback callback = mock(MessageRetryHandler.RetryCallback.class);
        doThrow(new RuntimeException("First attempt failed"))
            .doThrow(new RuntimeException("Second attempt failed"))
            .doNothing()
            .when(callback).execute(message);
        
        retryHandler.doWithRetry(message, callback);
        
        verify(callback, times(3)).execute(message);
    }
    
    @Test
    void shouldFailAfterMaxAttempts() {
        MessageRetryHandler.RetryCallback callback = mock(MessageRetryHandler.RetryCallback.class);
        RuntimeException exception = new RuntimeException("Test exception");
        doThrow(exception).when(callback).execute(message);
        
        Exception thrown = assertThrows(RuntimeException.class, () -> 
            retryHandler.doWithRetry(message, callback));
        
        assertEquals("Test exception", thrown.getMessage());
        verify(callback, times(3)).execute(message);
    }
    
    @Test
    void shouldRespectInterruptedThread() throws Exception {
        MessageRetryHandler.RetryCallback callback = mock(MessageRetryHandler.RetryCallback.class);
        doThrow(new RuntimeException("First attempt failed"))
            .when(callback).execute(message);
        
        Thread.currentThread().interrupt();
        
        assertThrows(RuntimeException.class, () -> 
            retryHandler.doWithRetry(message, callback));
        
        verify(callback, times(1)).execute(message);
        assertTrue(Thread.interrupted()); // 清除中断标志
    }
} 