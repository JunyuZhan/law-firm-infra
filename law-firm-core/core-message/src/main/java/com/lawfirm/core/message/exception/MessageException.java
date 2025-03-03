package com.lawfirm.core.message.exception;

import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.BaseException;

/**
 * 消息异常类
 */
public class MessageException extends BaseException {

    private static final long serialVersionUID = 1L;

    public MessageException(ResultCode code, String message) {
        super(code, message);
    }

    public MessageException(ResultCode code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public MessageException(String message) {
        super(ResultCode.ERROR, message);
    }

    public MessageException(String message, Throwable cause) {
        super(ResultCode.ERROR, message, cause);
    }
} 