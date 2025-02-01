package com.lawfirm.common.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API统一返回结果
 */
@Data
@NoArgsConstructor
public class ApiResult<T> {
    
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 500;
    private static final String SUCCESS_MESSAGE = "操作成功";
    private static final String ERROR_MESSAGE = "操作失败";

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    private ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> ApiResult<T> success() {
        return new ApiResult<>(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    /**
     * 成功返回结果
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    /**
     * 成功返回结果
     */
    public static <T> ApiResult<T> success(String message, T data) {
        return new ApiResult<>(SUCCESS_CODE, message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> ApiResult<T> error() {
        return new ApiResult<>(ERROR_CODE, ERROR_MESSAGE, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(ERROR_CODE, message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ApiResult<T> error(int code, String message) {
        return new ApiResult<>(code, message, null);
    }
} 