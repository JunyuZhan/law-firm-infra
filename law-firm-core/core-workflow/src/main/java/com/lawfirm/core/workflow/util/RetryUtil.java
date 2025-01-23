package com.lawfirm.core.workflow.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

/**
 * 重试工具类
 */
@Slf4j
public class RetryUtil {

    /**
     * 执行重试操作
     *
     * @param operation 需要重试的操作
     * @param maxAttempts 最大重试次数
     * @param retryInterval 重试间隔(毫秒)
     * @param <T> 返回值类型
     * @return 操作结果
     * @throws Exception 如果重试后仍然失败则抛出异常
     */
    public static <T> T retry(Callable<T> operation, int maxAttempts, long retryInterval) throws Exception {
        return retry(operation, maxAttempts, retryInterval, e -> true);
    }

    /**
     * 执行重试操作(带重试条件)
     *
     * @param operation 需要重试的操作
     * @param maxAttempts 最大重试次数
     * @param retryInterval 重试间隔(毫秒)
     * @param retryOn 重试条件
     * @param <T> 返回值类型
     * @return 操作结果
     * @throws Exception 如果重试后仍然失败则抛出异常
     */
    public static <T> T retry(Callable<T> operation, int maxAttempts, long retryInterval, 
            Predicate<Exception> retryOn) throws Exception {
        
        int attempts = 0;
        Exception lastException = null;
        
        while (attempts < maxAttempts) {
            try {
                return operation.call();
            } catch (Exception e) {
                lastException = e;
                
                if (!retryOn.test(e)) {
                    throw e;
                }
                
                attempts++;
                
                if (attempts < maxAttempts) {
                    log.warn("Operation failed (attempt {}/{}), retrying in {} ms: {}", 
                            attempts, maxAttempts, retryInterval, e.getMessage());
                    
                    try {
                        Thread.sleep(retryInterval);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw e;
                    }
                }
            }
        }
        
        log.error("Operation failed after {} attempts", maxAttempts);
        throw lastException;
    }
    
    /**
     * 执行重试操作(无返回值)
     *
     * @param operation 需要重试的操作
     * @param maxAttempts 最大重试次数
     * @param retryInterval 重试间隔(毫秒)
     * @throws Exception 如果重试后仍然失败则抛出异常
     */
    public static void retry(Runnable operation, int maxAttempts, long retryInterval) throws Exception {
        retry(() -> {
            operation.run();
            return null;
        }, maxAttempts, retryInterval);
    }
    
    /**
     * 执行重试操作(无返回值,带重试条件)
     *
     * @param operation 需要重试的操作
     * @param maxAttempts 最大重试次数
     * @param retryInterval 重试间隔(毫秒)
     * @param retryOn 重试条件
     * @throws Exception 如果重试后仍然失败则抛出异常
     */
    public static void retry(Runnable operation, int maxAttempts, long retryInterval,
            Predicate<Exception> retryOn) throws Exception {
        retry(() -> {
            operation.run();
            return null;
        }, maxAttempts, retryInterval, retryOn);
    }
} 