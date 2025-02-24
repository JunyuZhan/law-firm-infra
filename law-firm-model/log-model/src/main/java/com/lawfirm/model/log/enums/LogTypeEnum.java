package com.lawfirm.model.log.enums;

import lombok.Getter;

/**
 * 日志类型枚举
 */
@Getter
public enum LogTypeEnum {

    /**
     * 操作日志
     */
    OPERATION("OPERATION", "操作日志"),

    /**
     * 行为日志
     */
    BEHAVIOR("BEHAVIOR", "行为日志"),

    /**
     * 系统日志
     */
    SYSTEM("SYSTEM", "系统日志"),

    /**
     * 安全日志
     */
    SECURITY("SECURITY", "安全日志"),

    /**
     * 异常日志
     */
    ERROR("ERROR", "异常日志"),

    /**
     * 审计日志
     */
    AUDIT("AUDIT", "审计日志");

    /**
     * 类型编码
     */
    private final String code;

    /**
     * 类型描述
     */
    private final String desc;

    LogTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 