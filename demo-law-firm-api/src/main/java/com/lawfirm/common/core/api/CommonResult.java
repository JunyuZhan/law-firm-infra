package com.lawfirm.common.core.api;

public class CommonResult<T> {
    private int code = 200;
    private String message = "success";
    private T data;

    public static <T> CommonResult<T> success() {
        return new CommonResult<>();
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.data = data;
        return result;
    }

    // getter/setter 省略，可根据需要补充
} 