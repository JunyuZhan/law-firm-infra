package com.lawfirm.common.core.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(200, "操作成功", null);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, "操作成功", data);
    }

    public static <T> BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, message, null);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(500, message, null);
    }
} 