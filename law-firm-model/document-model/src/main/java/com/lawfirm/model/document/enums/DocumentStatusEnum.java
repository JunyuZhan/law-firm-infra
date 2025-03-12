package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档状态枚举
 */
@Getter
public enum DocumentStatusEnum {

    /**
     * 草稿
     */
    DRAFT("DRAFT", "草稿"),

    /**
     * 待审核
     */
    PENDING_REVIEW("PENDING_REVIEW", "待审核"),

    /**
     * 已审核
     */
    REVIEWED("REVIEWED", "已审核"),

    /**
     * 已发布
     */
    PUBLISHED("PUBLISHED", "已发布"),

    /**
     * 已归档
     */
    ARCHIVED("ARCHIVED", "已归档"),

    /**
     * 已废弃
     */
    DEPRECATED("DEPRECATED", "已废弃"),

    /**
     * 已删除
     */
    DELETED("DELETED", "已删除");

    /**
     * 状态编码
     */
    @EnumValue
    @JsonValue
    private final String code;

    /**
     * 状态名称
     */
    private final String name;

    DocumentStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据编码获取枚举
     */
    public static DocumentStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (DocumentStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 