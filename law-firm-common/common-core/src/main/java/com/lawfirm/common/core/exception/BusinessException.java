package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.constant.ResultCode;
import lombok.Getter;

/**
 * 业务异常
 */
@Getter
public class BusinessException extends BaseException {
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(int code, String message) {
        super(code, message);
    }
    
    public BusinessException(ResultCode resultCode) {
        super(resultCode);
    }
    
    public BusinessException(ResultCode resultCode, String message) {
        super(resultCode.getCode(), message);
    }
} 