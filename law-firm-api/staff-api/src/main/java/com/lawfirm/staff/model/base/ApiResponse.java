package com.lawfirm.staff.model.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * API响应基类
 */
@Data
@Schema(description = "API响应基类")
public class ApiResponse<T> {
    
    @Schema(description = "状态码")
    private Integer code;
    
    @Schema(description = "消息")
    private String message;
    
    @Schema(description = "数据")
    private T data;
    
    public ApiResponse() {
        this.code = 200;
        this.message = "操作成功";
    }
    
    public ApiResponse(T data) {
        this();
        this.data = data;
    }
    
    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>();
    }
    
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data);
    }
    
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }
    
    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(500, "操作失败");
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(500, message);
    }
    
    public static <T> ApiResponse<T> error(Integer code, String message) {
        return new ApiResponse<>(code, message);
    }
} 