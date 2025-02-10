package com.lawfirm.model.document.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 文档转换任务状态枚举
 */
@Getter
public enum ConversionTaskStatus implements BaseEnum<String> {
    
    PENDING("PENDING", "等待转换"),
    CONVERTING("CONVERTING", "转换中"),
    COMPLETED("COMPLETED", "转换完成"),
    FAILED("FAILED", "转换失败"),
    CANCELLED("CANCELLED", "已取消");

    private final String value;
    private final String description;

    ConversionTaskStatus(String value, String description) {
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