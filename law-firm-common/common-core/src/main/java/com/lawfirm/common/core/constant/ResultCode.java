package com.lawfirm.common.core.constant;

import lombok.Getter;

/**
 * 通用响应码
 * 只定义最基础的、框架级的响应码
 * 具体的业务响应码应该由各个业务模块自行定义
 */
@Getter
public enum ResultCode {
    
    SUCCESS(200, "成功"),
    
    // 客户端错误: 400-499
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    
    // 服务端错误: 500-599
    ERROR(500, "错误"),
    INTERNAL_ERROR(501, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_ERROR(504, "网关错误"),
    
    // 框架级异常: 600-699
    VALIDATION_ERROR(600, "参数验证错误"),
    CONCURRENT_ERROR(601, "并发操作错误"),
    RATE_LIMIT_ERROR(602, "请求频率超限");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
} 