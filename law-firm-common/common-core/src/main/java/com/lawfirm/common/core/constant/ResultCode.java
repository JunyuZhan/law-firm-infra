package com.lawfirm.common.core.constant;

import lombok.Getter;

@Getter
public enum ResultCode {
    
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    
    // 用户相关：1000-1999
    USER_NOT_EXIST(1000, "用户不存在"),
    USERNAME_OR_PASSWORD_ERROR(1001, "用户名或密码错误"),
    USER_ACCOUNT_EXPIRED(1002, "账号已过期"),
    USER_CREDENTIALS_ERROR(1003, "用户凭证错误"),
    USER_CREDENTIALS_EXPIRED(1004, "用户凭证已过期"),
    USER_ACCOUNT_DISABLE(1005, "账号不可用"),
    USER_ACCOUNT_LOCKED(1006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(1007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(1008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(1009, "账号下线"),
    
    // 业务异常：2000-2999
    NO_PERMISSION(2001, "没有权限操作"),
    
    // 系统异常：3000-3999
    SYSTEM_ERROR(3000, "系统异常"),
    NETWORK_ERROR(3001, "网络异常"),
    DB_ERROR(3002, "数据库异常"),
    FILE_ERROR(3003, "文件异常"),
    
    // 参数校验：4000-4999
    PARAM_ERROR(4000, "参数错误"),
    PARAM_NULL(4001, "参数为空"),
    PARAM_FORMAT_ERROR(4002, "参数格式不正确"),
    PARAM_VALUE_ERROR(4003, "参数值不正确"),
    
    // 数据异常：5000-5999
    DATA_NOT_FOUND(5000, "数据未找到"),
    DATA_IS_WRONG(5001, "数据有误"),
    DATA_ALREADY_EXISTED(5002, "数据已存在"),
    
    // 接口异常：6000-6999
    INTERFACE_INNER_INVOKE_ERROR(6000, "内部系统接口调用异常"),
    INTERFACE_OUTER_INVOKE_ERROR(6001, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(6002, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(6003, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(6004, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(6005, "接口负载过高");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
} 