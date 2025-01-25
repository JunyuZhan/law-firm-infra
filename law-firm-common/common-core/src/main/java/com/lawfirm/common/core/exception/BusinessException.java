package com.lawfirm.common.core.exception;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private int code;
    private String message;
    
    public BusinessException(String message) {
        super(message);
        this.message = message;
        this.code = 500;
    }
    
    public BusinessException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
} 