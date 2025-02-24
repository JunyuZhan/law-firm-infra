package com.lawfirm.model.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 配置类型枚举
 */
@Getter
@AllArgsConstructor
public enum ConfigTypeEnum {

    SYSTEM("SYSTEM", "系统配置"),
    BUSINESS("BUSINESS", "业务配置"),
    SECURITY("SECURITY", "安全配置"),
    OTHER("OTHER", "其他配置");

    private final String value;
    private final String label;
} 