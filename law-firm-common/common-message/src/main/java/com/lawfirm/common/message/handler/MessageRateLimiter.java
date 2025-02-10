package com.lawfirm.common.message.handler;

/**
 * 消息限流器
 */
public interface MessageRateLimiter {
    
    /**
     * 尝试获取一个令牌
     *
     * @return 是否获取成功
     */
    boolean tryConsume();
    
    /**
     * 尝试获取指定数量的令牌
     *
     * @param tokens 令牌数量
     * @return 是否获取成功
     */
    boolean tryConsume(int tokens);
    
    /**
     * 获取一个令牌（阻塞）
     */
    void consume();
    
    /**
     * 获取指定数量的令牌（阻塞）
     *
     * @param tokens 令牌数量
     */
    void consume(int tokens);
    
    /**
     * 获取可用令牌数量
     *
     * @return 可用令牌数量
     */
    long getAvailableTokens();
} 