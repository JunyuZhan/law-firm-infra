package com.lawfirm.model.cases.enums.base;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 案件状态枚举
 */
@Getter
@RequiredArgsConstructor
public enum CaseStatusEnum {
    
    DRAFT(0, "草稿"),
    PENDING(1, "待处理"),
    PROCESSING(2, "处理中"),
    ON_HOLD(3, "暂停"),
    CLOSED(4, "已结案"),
    ARCHIVED(5, "已归档");

    @EnumValue
    @JsonValue
    private final Integer value;
    private final String description;

    /**
     * 根据值获取枚举
     */
    public static CaseStatusEnum fromValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseStatusEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断是否为已结案状态
     */
    public boolean isClosed() {
        return this == CLOSED;
    }

    /**
     * 判断是否为已归档状态
     */
    public boolean isArchived() {
        return this == ARCHIVED;
    }

    /**
     * 判断是否为进行中状态
     */
    public boolean isProcessing() {
        return this == PROCESSING;
    }

    /**
     * 判断是否为暂停状态
     */
    public boolean isOnHold() {
        return this == ON_HOLD;
    }
} 