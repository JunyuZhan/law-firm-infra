package com.lawfirm.system.exception;

/**
 * 系统模块业务异常
 */
public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public SystemException(String message) {
        super(message);
    }
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
    public static SystemException notFound(String entity) {
        return new SystemException(entity + "不存在");
    }
    public static SystemException invalidState(String stateDesc) {
        return new SystemException("当前状态不允许: " + stateDesc);
    }
} 