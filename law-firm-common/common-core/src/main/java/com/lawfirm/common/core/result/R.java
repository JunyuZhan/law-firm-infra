package com.lawfirm.common.core.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>()
                .setCode(ResultCode.SUCCESS.getCode())
                .setMsg(ResultCode.SUCCESS.getMsg())
                .setData(data);
    }

    public static <T> R<T> error() {
        return error(ResultCode.ERROR);
    }

    public static <T> R<T> error(String msg) {
        return new R<T>()
                .setCode(ResultCode.ERROR.getCode())
                .setMsg(msg);
    }

    public static <T> R<T> error(IResultCode resultCode) {
        return new R<T>()
                .setCode(resultCode.getCode())
                .setMsg(resultCode.getMsg());
    }
} 