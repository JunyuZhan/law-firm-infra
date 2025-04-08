package com.lawfirm.task.exception;

import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.BusinessException;

/**
 * 任务模块业务异常
 */
public class TaskException extends BusinessException {
    
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    public TaskException(String message) {
        super(message);
    }

    public TaskException(int code, String message) {
        super(code, message);
    }
    
    public TaskException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }
    
    public TaskException(ResultCode resultCode) {
        super(resultCode);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 使用任务错误码构造异常
     * @param errorCode 任务错误码
     */
    public TaskException(TaskErrorCode errorCode) {
        super(ResultCode.ERROR.getCode(), errorCode.getMessage());
    }
    
    /**
     * 使用任务错误码和自定义消息构造异常
     * @param errorCode 任务错误码
     * @param message 自定义错误消息
     */
    public TaskException(TaskErrorCode errorCode, String message) {
        super(ResultCode.ERROR.getCode(), message);
    }
} 