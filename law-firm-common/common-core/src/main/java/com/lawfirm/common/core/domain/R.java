package com.lawfirm.common.core.domain;

import lombok.Data;

/**
 * 通用响应类
 */
@Data
public class R<T> {
    
    private static final int SUCCESS = 200;
    private static final int FAIL = 500;
    
    private int code;
    private String msg;
    private T data;
    
    public static <T> R<T> ok() {
        return ok(null);
    }
    
    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(SUCCESS);
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }
    
    public static <T> R<T> fail() {
        return fail("操作失败");
    }
    
    public static <T> R<T> fail(String msg) {
        R<T> r = new R<>();
        r.setCode(FAIL);
        r.setMsg(msg);
        return r;
    }
} 