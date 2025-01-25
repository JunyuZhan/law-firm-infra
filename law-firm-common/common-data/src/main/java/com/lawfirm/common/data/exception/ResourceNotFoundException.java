package com.lawfirm.common.data.exception;

/**
 * 资源未找到异常
 */
public class ResourceNotFoundException extends RuntimeException {
    private Integer code;

    public ResourceNotFoundException(String message) {
        super(message);
        this.code = 404;
    }

    public ResourceNotFoundException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
} 