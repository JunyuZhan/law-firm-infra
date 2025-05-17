package com.lawfirm.system.exception;

/**
 * 字典模块业务异常
 */
public class DictException extends SystemException {
    private static final long serialVersionUID = 1L;
    
    public DictException(String message) {
        super(message);
    }
    public DictException(String message, Throwable cause) {
        super(message, cause);
    }
    public static DictException notFound(String entity) {
        return new DictException(entity + "不存在");
    }
    public static DictException invalidState(String stateDesc) {
        return new DictException("当前状态不允许: " + stateDesc);
    }
} 