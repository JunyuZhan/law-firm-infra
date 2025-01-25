package com.lawfirm.client.exception;

import com.lawfirm.common.core.exception.BaseException;
import com.lawfirm.common.core.constant.ResultCode;

public class ClientException extends BaseException {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(ResultCode resultCode) {
        super(resultCode);
    }

    public ClientException(int code, String message) {
        super(code, message);
    }
} 