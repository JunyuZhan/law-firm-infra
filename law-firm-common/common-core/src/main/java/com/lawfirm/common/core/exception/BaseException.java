package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.constant.ResultCode;
import lombok.Getter;

/**
 * 基础异常类
 * 所有自定义异常的基类
 */
@Getter
public abstract class BaseException extends RuntimeException {
    
    private final int code;

    protected BaseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    protected BaseException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    protected BaseException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.code = resultCode.getCode();
    }

    protected BaseException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.code = resultCode.getCode();
    }
} 