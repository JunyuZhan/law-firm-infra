package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.constant.ResultCode;

/**
 * 文件未找到异常
 */
public class FileNotFoundException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 构造函数
     */
    public FileNotFoundException() {
        super(ResultCode.NOT_FOUND, "文件未找到");
    }
    
    /**
     * 构造函数
     * 
     * @param message 错误消息
     */
    public FileNotFoundException(String message) {
        super(ResultCode.NOT_FOUND, message);
    }
    
    /**
     * 构造函数
     * 
     * @param message 错误消息
     * @param cause 错误原因
     */
    public FileNotFoundException(String message, Throwable cause) {
        super(ResultCode.NOT_FOUND, message, cause);
    }
} 