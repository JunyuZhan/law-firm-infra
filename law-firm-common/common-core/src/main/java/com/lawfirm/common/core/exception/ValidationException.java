package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.constant.ResultCode;
import lombok.Getter;
import java.util.Map;

/**
 * 参数校验异常
 */
@Getter
public class ValidationException extends BusinessException {
    
    private final Map<String, String> errors;
    
    public ValidationException(String message) {
        super(ResultCode.PARAM_ERROR.getCode(), message);
        this.errors = null;
    }
    
    public ValidationException(String message, Map<String, String> errors) {
        super(ResultCode.PARAM_ERROR.getCode(), message);
        this.errors = errors;
    }
    
    public ValidationException(int code, String message, Map<String, String> errors) {
        super(code, message);
        this.errors = errors;
    }
} 