package com.lawfirm.common.core.api;

import com.lawfirm.common.core.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

/**
 * 通用响应结果
 * 提供基础的响应结构和常用的静态工厂方法
 *
 * @param <T> 响应数据类型
 */
@Data
@NoArgsConstructor
public class CommonResult<T> {
    /**
     * 响应码
     */
    private int code;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 响应消息
     */
    private String message;

    public CommonResult(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 创建成功结果
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), null, ResultCode.SUCCESS.getMessage());
    }

    /**
     * 创建带数据的成功结果
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), data, ResultCode.SUCCESS.getMessage());
    }

    /**
     * 创建带消息的成功结果
     */
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), null, message);
    }

    /**
     * 创建带数据和消息的成功结果
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), data, message);
    }

    /**
     * 创建错误结果
     */
    public static <T> CommonResult<T> error() {
        return new CommonResult<>(ResultCode.INTERNAL_ERROR.getCode(), null, ResultCode.INTERNAL_ERROR.getMessage());
    }

    /**
     * 创建带消息的错误结果
     */
    public static <T> CommonResult<T> error(String message) {
        return new CommonResult<>(ResultCode.INTERNAL_ERROR.getCode(), null, message);
    }

    /**
     * 创建带错误码的错误结果
     */
    public static <T> CommonResult<T> error(ResultCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), null, errorCode.getMessage());
    }

    /**
     * 创建带错误码和消息的错误结果
     */
    public static <T> CommonResult<T> error(int code, String message) {
        return new CommonResult<>(code, null, message);
    }

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return code == ResultCode.SUCCESS.getCode();
    }

    /**
     * 创建验证失败结果
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<>(ResultCode.VALIDATION_ERROR.getCode(), null, message);
    }

    /**
     * 创建未授权结果
     */
    public static <T> CommonResult<T> unauthorized(String message) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), null, message);
    }

    /**
     * 创建禁止访问结果
     */
    public static <T> CommonResult<T> forbidden(String message) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), null, message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonResult<?> that = (CommonResult<?>) o;
        return code == that.code &&
               Objects.equals(data, that.data) &&
               Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, data, message);
    }

    @Override
    public String toString() {
        return "CommonResult{" +
               "code=" + code +
               ", data=" + data +
               ", message='" + message + '\'' +
               '}';
    }
} 