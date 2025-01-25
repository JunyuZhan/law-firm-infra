package com.lawfirm.cases.exception;

import com.lawfirm.common.core.exception.BaseException;
import com.lawfirm.common.core.constant.ResultCode;

public class CaseException extends BaseException {

    public CaseException(String message) {
        super(message);
    }

    public CaseException(ResultCode resultCode) {
        super(resultCode);
    }

    public CaseException(int code, String message) {
        super(code, message);
    }
} 