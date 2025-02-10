package com.lawfirm.common.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {
    SUCCESS(0, "操作成功"),
    ERROR(-1, "操作失败"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "访问受限"),
    NOT_FOUND(404, "资源不存在"),
    PARAM_ERROR(400, "参数错误"),
    INTERNAL_SERVER_ERROR(500, "服务器异常");

    private final int code;
    private final String msg;
} 