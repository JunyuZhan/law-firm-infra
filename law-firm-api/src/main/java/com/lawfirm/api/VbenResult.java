package com.lawfirm.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Vue-Vben-Admin期望的响应格式
 * @param <T> 响应数据类型
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VbenResult<T> {
    /**
     * 错误码，0表示成功
     */
    private Integer code;
    
    /**
     * 数据结果
     */
    private T result;
    
    /**
     * 错误信息
     */
    private String message;
    
    /**
     * 消息类型
     */
    private String type;
    
    /**
     * 成功结果
     */
    public static <T> VbenResult<T> success(T data) {
        return new VbenResult<>(0, data, "ok", "success");
    }
    
    /**
     * 成功结果（带消息）
     */
    public static <T> VbenResult<T> success(T data, String message) {
        return new VbenResult<>(0, data, message, "success");
    }
    
    /**
     * 错误结果
     */
    public static <T> VbenResult<T> error(String message) {
        return new VbenResult<>(1, null, message, "error");
    }
    
    /**
     * 错误结果（带错误码）
     */
    public static <T> VbenResult<T> error(Integer code, String message) {
        return new VbenResult<>(code, null, message, "error");
    }
    
    /**
     * 未授权结果
     */
    public static <T> VbenResult<T> unauthorized(String message) {
        return new VbenResult<>(401, null, message, "error");
    }
    
    /**
     * 服务器错误结果
     */
    public static <T> VbenResult<T> serverError(String message) {
        return new VbenResult<>(500, null, message, "error");
    }
} 