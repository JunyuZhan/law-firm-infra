package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 文档编辑状态枚举
 */
@Getter
public enum DocumentEditStatusEnum implements BaseEnum<String> {
    
    UNLOCKED("UNLOCKED", "未锁定"),
    LOCKED("LOCKED", "已锁定"),
    EDITING("EDITING", "编辑中");

    @EnumValue
    @JsonValue
    private final String value;
    private final String description;

    DocumentEditStatusEnum(String value, String description) {
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