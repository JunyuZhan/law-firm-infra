package com.lawfirm.common.core.utils;



import org.apache.commons.lang3.StringUtils;

import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.BaseException;

public class Assert {
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BaseException(message);
        }
    }

    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new BaseException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BaseException(message);
        }
    }

    public static void hasText(String text, ResultCode resultCode) {
        if (StringUtils.isEmpty(text)) {
            throw new BaseException(resultCode);
        }
    }

    public static void notNull(Object object, ResultCode resultCode) {
        if (object == null) {
            throw new BaseException(resultCode);
        }
    }

    public static void isTrue(boolean expression, ResultCode resultCode) {
        if (!expression) {
            throw new BaseException(resultCode);
        }
    }
} 