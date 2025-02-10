package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.constant.ResultCode;
import lombok.Getter;

/**
 * 系统异常
 */
@Getter
public class SystemException extends BaseException {
    
    public SystemException(String message) {
        super(message);
    }
    
    public SystemException(int code, String message) {
        super(code, message);
    }
    
    public SystemException(String message, Throwable cause) {
        super(ResultCode.FAILED.getCode(), message);
        initCause(cause);
    }
    
    public SystemException(int code, String message, Throwable cause) {
        super(code, message);
        initCause(cause);
    }
} 