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

    /**
     * 判断是否为初始状态
     */
    public boolean isInitialStatus() {
        return this == DRAFT;
    }

    /**
     * 判断是否为终止状态
     */
    public boolean isTerminalStatus() {
        return this == CLOSED || this == ARCHIVED;
    }

    /**
     * 判断是否为活动状态
     */
    public boolean isActiveStatus() {
        return this == PENDING || this == PROCESSING;
    }

    /**
     * 判断是否为暂停状态
     */
    public boolean isPausedStatus() {
        return this == ON_HOLD;
    }

    /**
     * 判断是否为关闭状态
     */
    public boolean isClosedStatus() {
        return this == CLOSED;
    }

    /**
     * 判断是否为异常状态
     */
    public boolean isAbnormalStatus() {
        return false; // 当前没有定义异常状态，根据需要修改
    }

    /**
     * 判断是否需要审批
     */
    public boolean needApproval() {
        return this == PENDING; // 根据业务需求修改
    }

    /**
     * 判断是否允许回退
     */
    public boolean allowRollback() {
        return this != DRAFT && this != ARCHIVED; // 除了草稿和归档状态，其他状态都可以回退
    }

    /**
     * 判断是否允许跳过
     */
    public boolean allowSkip() {
        return this == PENDING || this == PROCESSING; // 只有待处理和处理中状态可以跳过
    }

    /**
     * 判断是否允许编辑
     */
    public boolean allowEdit() {
        return this != ARCHIVED; // 除了归档状态，其他状态都可以编辑
    }

    /**
     * 判断是否允许删除
     */
    public boolean allowDelete() {
        return this == DRAFT; // 只有草稿状态可以删除
    }
} 