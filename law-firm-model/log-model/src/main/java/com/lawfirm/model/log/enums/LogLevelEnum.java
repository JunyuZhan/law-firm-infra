package com.lawfirm.model.log.enums;

import lombok.Getter;

/**
 * 日志级别枚举
 */
@Getter
public enum LogLevelEnum {

    /**
     * 跟踪级别
     */
    TRACE("TRACE", "跟踪"),

    /**
     * 调试级别
     */
    DEBUG("DEBUG", "调试"),

    /**
     * 信息级别
     */
    INFO("INFO", "信息"),

    /**
     * 警告级别
     */
    WARN("WARN", "警告"),

    /**
     * 错误级别
     */
    ERROR("ERROR", "错误"),

    /**
     * 致命级别
     */
    FATAL("FATAL", "致命");

    /**
     * 级别编码
     */
    private final String code;

    /**
     * 级别描述
     */
    private final String desc;

    LogLevelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 