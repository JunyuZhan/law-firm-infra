package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.constant.ResultCode;
import lombok.Getter;

/**
 * 业务异常
 * 用于表示业务逻辑中的错误状态
 */
@Getter
public class BusinessException extends RuntimeException {
    
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    private final int code;
    
    /**
     * 构造函数
     * 
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.ERROR.getCode();
    }
    
    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * 构造函数
     * 
     * @param resultCode 结果码
     * @param message 错误消息
     */
    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }
    
    /**
     * 构造函数
     * 
     * @param resultCode 结果码
     */
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
    
    /**
     * 构造函数
     * 
     * @param message 错误消息
     * @param cause 原因
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCode.ERROR.getCode();
    }
} 