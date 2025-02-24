package com.lawfirm.model.cases.enums.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 案件状态枚举
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CaseStatusEnum implements BaseEnum<Integer> {
    /**
     * 待处理
     */
    PENDING(0, "待处理"),

    /**
     * 进行中
     */
    IN_PROGRESS(1, "进行中"),

    /**
     * 已完成
     */
    COMPLETED(2, "已完成"),

    /**
     * 已归档
     */
    ARCHIVED(3, "已归档"),

    /**
     * 已取消
     */
    CANCELLED(4, "已取消"),

    /**
     * 已暂停
     */
    SUSPENDED(5, "已暂停");

    private final Integer value;
    private final String description;

    CaseStatusEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static CaseStatusEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseStatusEnum status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
} 