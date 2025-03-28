package com.lawfirm.api.vben;

import com.lawfirm.common.core.api.CommonResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Vue-Vben-Admin期望的响应格式
 * @param <T> 数据类型
 */
@Data
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
     * 从CommonResult转换为VbenResult
     */
    public static <T> VbenResult<T> from(CommonResult<T> commonResult) {
        VbenResult<T> vbenResult = new VbenResult<>();
        vbenResult.setCode(commonResult.getCode());
        vbenResult.setResult(commonResult.getData());
        vbenResult.setMessage(commonResult.getMessage());
        vbenResult.setType(commonResult.isSuccess() ? "success" : "error");
        return vbenResult;
    }
    
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
} 