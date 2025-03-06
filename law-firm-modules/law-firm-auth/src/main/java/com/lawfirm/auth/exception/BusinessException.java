package com.lawfirm.auth.exception;

/**
 * 业务异常
 * 处理业务逻辑异常情况
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
} 