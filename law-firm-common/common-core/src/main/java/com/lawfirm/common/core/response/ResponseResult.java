package com.lawfirm.common.core.response;

import lombok.Data;

/**
 * 统一响应结果
 * 
 * @deprecated 请使用 {@link com.lawfirm.common.core.api.CommonResult} 代替，
 * 以保持API响应格式一致性
 */
@Data
@Deprecated
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
     * 成功
     * 
     * @deprecated 请使用 {@link com.lawfirm.common.core.api.CommonResult#success()} 代替
     */
    @Deprecated
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * 成功
     * 
     * @deprecated 请使用 {@link com.lawfirm.common.core.api.CommonResult#success(Object)} 代替
     */
    @Deprecated
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * 失败
     */
    public static <T> ResponseResult<T> error(String message) {
        return error(500, message);
    }

    /**
     * 失败
     */
    public static <T> ResponseResult<T> error(Integer code, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
} 