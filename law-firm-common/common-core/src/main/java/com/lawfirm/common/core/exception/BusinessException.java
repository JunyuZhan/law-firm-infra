package com.lawfirm.common.core.exception;

/**
 * 业务异常类
 * 用于表示业务逻辑异常，非系统性异常
 */
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    private String code;
    
    /**
     * 构造函数
     */
    public BusinessException() {
        super();
    }
    
    /**
     * 构造函数
     * 
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
    }
    
    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * 构造函数
     * 
     * @param message 错误消息
     * @param cause 原因
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param cause 原因
     */
    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    
    /**
     * 获取错误码
     * 
     * @return 错误码
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置错误码
     * 
     * @param code 错误码
     */
    public void setCode(String code) {
        this.code = code;
    }
} 