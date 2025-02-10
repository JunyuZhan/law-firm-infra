package com.lawfirm.common.message.exception;

/**
 * 消息异常
 */
public class MessageException extends RuntimeException {
    
    public MessageException(String message) {
        super(message);
    }
    
    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 消息不存在异常
     */
    public static class MessageNotFoundException extends MessageException {
        public MessageNotFoundException(String messageId) {
            super("Message not found: " + messageId);
        }
    }
    
    /**
     * 消息发送失败异常
     */
    public static class SendMessageFailedException extends MessageException {
        public SendMessageFailedException(String message) {
            super(message);
        }
        
        public SendMessageFailedException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * 消息模板不存在异常
     */
    public static class TemplateNotFoundException extends MessageException {
        public TemplateNotFoundException(String templateCode) {
            super("Template not found: " + templateCode);
        }
    }
    
    /**
     * 无效消息异常
     */
    public static class InvalidMessageException extends MessageException {
        public InvalidMessageException(String message) {
            super(message);
        }
    }
    
    /**
     * 未授权访问异常
     */
    public static class UnauthorizedAccessException extends MessageException {
        public UnauthorizedAccessException(String message) {
            super(message);
        }
    }
    
    /**
     * 超出限流异常
     */
    public static class RateLimitExceededException extends MessageException {
        public RateLimitExceededException(String message) {
            super(message);
        }
    }
} 