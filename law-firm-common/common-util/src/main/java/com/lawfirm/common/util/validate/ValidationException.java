package com.lawfirm.common.util.validate;

import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.BaseException;

/**
 * 验证异常
 * 用于参数校验等基础验证场景
 */
public class ValidationException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    public ValidationException(ResultCode resultCode) {
        super(resultCode);
    }
    
    public ValidationException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }
    
    public ValidationException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }
    
    public ValidationException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }
} 