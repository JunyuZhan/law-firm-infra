package com.lawfirm.model.system.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum ConfigTypeEnum implements BaseEnum<String> {
    
    SYSTEM("SYSTEM", "系统配置"),
    BUSINESS("BUSINESS", "业务配置"),
    SECURITY("SECURITY", "安全配置"),
    EMAIL("EMAIL", "邮件配置"),
    SMS("SMS", "短信配置"),
    STORAGE("STORAGE", "存储配置"),
    WORKFLOW("WORKFLOW", "工作流配置"),
    OTHER("OTHER", "其他配置");

    private final String value;
    private final String description;

    ConfigTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 