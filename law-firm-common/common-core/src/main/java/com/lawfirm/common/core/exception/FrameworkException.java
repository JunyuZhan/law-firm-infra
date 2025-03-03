package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.constant.ResultCode;

/**
 * 框架级异常
 * 用于处理框架内部的异常情况
 */
public class FrameworkException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    public FrameworkException(ResultCode resultCode) {
        super(resultCode);
    }
    
    public FrameworkException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }
    
    public FrameworkException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }
    
    public FrameworkException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }
} 