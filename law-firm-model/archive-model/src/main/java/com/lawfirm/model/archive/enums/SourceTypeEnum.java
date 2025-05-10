package com.lawfirm.model.archive.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 档案来源类型枚举
 */
@Getter
public enum SourceTypeEnum implements BaseEnum<String> {

    /**
     * 案件
     */
    CASE("CASE", "案件"),

    /**
     * 合同
     */
    CONTRACT("CONTRACT", "合同"),

    /**
     * 文档
     */
    DOCUMENT("DOCUMENT", "文档"),
    
    /**
     * 行政文件
     */
    ADMIN("ADMIN", "行政文件");

    private final String value;
    private final String description;

    SourceTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 根据类型值获取枚举
     */
    public static SourceTypeEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (SourceTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
} 