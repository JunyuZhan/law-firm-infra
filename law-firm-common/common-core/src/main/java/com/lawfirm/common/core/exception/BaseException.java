package com.lawfirm.common.core.exception;

import com.lawfirm.common.core.constant.ResultCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final int code;

    public BaseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BaseException(String message) {
        super(message);
        this.code = ResultCode.FAILED.getCode();
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }
} 