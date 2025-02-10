package com.lawfirm.common.message.handler.impl;

import com.lawfirm.common.message.handler.MessageRetryHandler;
import com.lawfirm.model.base.message.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单消息重试处理器实现
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleMessageRetryHandler implements MessageRetryHandler {
    
    private final int maxAttempts;
    private final long initialInterval;
    private final double multiplier;
    private final long maxInterval;
    
    @Override
    public void doWithRetry(MessageEntity message, RetryCallback retryCallback) throws Exception {
        int attempts = 0;
        long interval = initialInterval;
        Exception lastException = null;
        
        while (attempts < maxAttempts) {
            try {
                retryCallback.execute(message);
                return;
            } catch (Exception e) {
                lastException = e;
                attempts++;
                
                if (attempts < maxAttempts) {
                    log.warn("Retry attempt {} failed for message {}, will retry in {} ms", 
                            attempts, message.getId(), interval, e);
                    
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw e;
                    }
                    
                    interval = Math.min((long)(interval * multiplier), maxInterval);
                }
            }
        }
        
        log.error("All {} retry attempts failed for message {}", maxAttempts, message.getId(), lastException);
        throw lastException;
    }
} 