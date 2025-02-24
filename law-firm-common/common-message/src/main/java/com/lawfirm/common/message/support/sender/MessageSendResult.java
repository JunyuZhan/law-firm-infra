package com.lawfirm.common.message.support.sender;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 消息发送结果
 * 用于统一各种消息发送渠道的返回结果
 */
@Data
@Accessors(chain = true)
public class MessageSendResult {
    
    /**
     * 是否发送成功
     */
    private boolean success;
    
    /**
     * 消息ID
     */
    private String messageId;
    
    /**
     * 发送渠道
     */
    private String channel;
    
    /**
     * 错误码
     */
    private String errorCode;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 原始响应数据
     */
    private Object rawResponse;
    
    /**
     * 创建成功结果
     */
    public static MessageSendResult success(String messageId, String channel) {
        return new MessageSendResult()
                .setSuccess(true)
                .setMessageId(messageId)
                .setChannel(channel);
    }
    
    /**
     * 创建失败结果
     */
    public static MessageSendResult failure(String channel, String errorCode, String errorMessage) {
        return new MessageSendResult()
                .setSuccess(false)
                .setChannel(channel)
                .setErrorCode(errorCode)
                .setErrorMessage(errorMessage);
    }
} 