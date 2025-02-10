package com.lawfirm.common.message.handler;

import com.lawfirm.model.base.message.entity.MessageEntity;

/**
 * 消息重试处理器
 */
public interface MessageRetryHandler {
    
    /**
     * 执行重试操作
     *
     * @param message 消息实体
     * @param retryCallback 重试回调
     * @throws Exception 重试失败异常
     */
    void doWithRetry(MessageEntity message, RetryCallback retryCallback) throws Exception;
    
    /**
     * 重试回调接口
     */
    @FunctionalInterface
    interface RetryCallback {
        void execute(MessageEntity message) throws Exception;
    }
} 