package com.lawfirm.common.web.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一响应对象
 */
@Getter
@Setter
@Accessors(chain = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 是否成功
     */
    private Boolean success;

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.code = 200;
        r.msg = "success";
        r.data = data;
        r.success = true;
        return r;
    }

    public static <T> R<T> error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static <T> R<T> error(String msg) {
        return error(500, msg);
    }

    public static <T> R<T> error(int code, String msg) {
        R<T> r = new R<>();
        r.code = code;
        r.msg = msg;
        r.success = false;
        return r;
    }
} 