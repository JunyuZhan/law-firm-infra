package com.lawfirm.common.message.handler;

/**
 * 消息幂等处理器
 */
public interface MessageIdempotentHandler {
    
    /**
     * 检查消息是否已处理
     *
     * @param messageId 消息ID
     * @return 是否已处理
     */
    boolean isProcessed(String messageId);
    
    /**
     * 标记消息已处理
     *
     * @param messageId 消息ID
     */
    void markAsProcessed(String messageId);
    
    /**
     * 清除消息处理记录
     *
     * @param messageId 消息ID
     */
    void clear(String messageId);
} 