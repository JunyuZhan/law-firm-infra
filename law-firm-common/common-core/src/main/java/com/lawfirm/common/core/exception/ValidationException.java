package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.constant.ResultCode;

/**
 * 参数验证异常
 */
public class ValidationException extends FrameworkException {
    
    private static final long serialVersionUID = 1L;
    
    public ValidationException(String message) {
        super(ResultCode.VALIDATION_ERROR, message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(ResultCode.VALIDATION_ERROR, message, cause);
    }
} 