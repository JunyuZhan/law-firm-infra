package com.lawfirm.core.message.exception;

import lombok.Getter;

/**
 * 消息异常基类
 */
public class MessageException extends RuntimeException {
    
    @Getter
    private final String errorCode;
    
    public MessageException(String message) {
        super(message);
        this.errorCode = "MESSAGE_ERROR";
    }
    
    public MessageException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public MessageException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "MESSAGE_ERROR";
    }
    
    public MessageException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    /**
     * 消息不存在异常
     */
    public static class MessageNotFoundException extends MessageException {
        public MessageNotFoundException(String messageId) {
            super("MESSAGE_NOT_FOUND", "Message not found: " + messageId);
        }
    }
    
    /**
     * 消息模板不存在异常
     */
    public static class TemplateNotFoundException extends MessageException {
        public TemplateNotFoundException(String templateCode) {
            super("TEMPLATE_NOT_FOUND", "Message template not found: " + templateCode);
        }
    }
    
    /**
     * 消息发送失败异常
     */
    public static class MessageSendFailedException extends MessageException {
        public MessageSendFailedException(String message) {
            super("MESSAGE_SEND_FAILED", message);
        }
        
        public MessageSendFailedException(String message, Throwable cause) {
            super("MESSAGE_SEND_FAILED", message, cause);
        }
    }
    
    /**
     * 消息参数无效异常
     */
    public static class InvalidMessageParameterException extends MessageException {
        public InvalidMessageParameterException(String message) {
            super("INVALID_MESSAGE_PARAMETER", message);
        }
    }
    
    /**
     * 消息处理超时异常
     */
    public static class MessageTimeoutException extends MessageException {
        public MessageTimeoutException(String messageId) {
            super("MESSAGE_TIMEOUT", "Message processing timeout: " + messageId);
        }
    }
    
    /**
     * 消息重复发送异常
     */
    public static class DuplicateMessageException extends MessageException {
        public DuplicateMessageException(String messageId) {
            super("DUPLICATE_MESSAGE", "Duplicate message detected: " + messageId);
        }
    }
    
    /**
     * 消息发送频率限制异常
     */
    public static class RateLimitExceededException extends MessageException {
        public RateLimitExceededException(String userId) {
            super("RATE_LIMIT_EXCEEDED", "Message rate limit exceeded for user: " + userId);
        }
    }
    
    /**
     * 未授权访问异常
     */
    public static class UnauthorizedAccessException extends MessageException {
        public UnauthorizedAccessException(String message) {
            super("UNAUTHORIZED_ACCESS", message);
        }
    }
} 