package com.lawfirm.common.message.handler.retry;

import java.time.Duration;

/**
 * 重试策略接口
 * 定义消息重试的基本行为
 */
public interface RetryPolicy {
    
    /**
     * 是否应该重试
     *
     * @param retryCount 当前重试次数
     * @param exception 异常信息
     * @return 是否重试
     */
    boolean shouldRetry(int retryCount, Throwable exception);
    
    /**
     * 获取下次重试的延迟时间
     *
     * @param retryCount 当前重试次数
     * @return 延迟时间
     */
    Duration getNextRetryDelay(int retryCount);
    
    /**
     * 获取最大重试次数
     *
     * @return 最大重试次数
     */
    int getMaxRetryCount();
    
    /**
     * 是否应该放弃重试
     *
     * @param exception 异常信息
     * @return 是否放弃
     */
    boolean shouldAbort(Throwable exception);
} 