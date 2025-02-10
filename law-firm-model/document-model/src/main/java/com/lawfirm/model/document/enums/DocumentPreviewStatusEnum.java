package com.lawfirm.model.document.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 文档预览状态枚举
 */
@Getter
public enum DocumentPreviewStatusEnum implements BaseEnum<String> {
    
    NOT_STARTED("NOT_STARTED", "未开始预览生成"),
    GENERATING("GENERATING", "预览生成中"),
    GENERATED("GENERATED", "预览已生成"),
    FAILED("FAILED", "预览生成失败");

    private final String value;
    private final String description;

    DocumentPreviewStatusEnum(String value, String description) {
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