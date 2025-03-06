package com.lawfirm.auth.exception;

/**
 * 无效令牌异常
 * 处理JWT令牌验证失败的情况
 */
public class InvalidTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public InvalidTokenException(String message) {
        super(message);
    }
    
    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
