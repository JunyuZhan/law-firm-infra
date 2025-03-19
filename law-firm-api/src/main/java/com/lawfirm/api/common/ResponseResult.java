package com.lawfirm.api.common;

import lombok.Data;

/**
 * 统一响应结果
 */
@Data
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 结果
     */
    private T result;

    /**
     * 成功响应
     */
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * 成功响应
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        result.setSuccess(true);
        result.setResult(data);
        return result;
    }

    /**
     * 失败响应
     */
    public static <T> ResponseResult<T> error(String message) {
        return error(500, message);
    }

    /**
     * 失败响应
     */
    public static <T> ResponseResult<T> error(Integer code, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }
} 