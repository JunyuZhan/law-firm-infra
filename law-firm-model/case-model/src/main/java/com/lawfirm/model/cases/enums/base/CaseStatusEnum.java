package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件状态枚举
 */
@Getter
public enum CaseStatusEnum implements BaseEnum<Integer> {

    /**
     * 待受理
     */
    PENDING(1, "待受理"),

    /**
     * 受理中
     */
    ACCEPTING(2, "受理中"),

    /**
     * 已受理
     */
    ACCEPTED(3, "已受理"),

    /**
     * 进行中
     */
    IN_PROGRESS(4, "进行中"),

    /**
     * 暂停
     */
    SUSPENDED(5, "暂停"),

    /**
     * 终止
     */
    TERMINATED(6, "终止"),

    /**
     * 撤销
     */
    REVOKED(7, "撤销"),

    /**
     * 已结案
     */
    CLOSED(8, "已结案"),

    /**
     * 已归档
     */
    ARCHIVED(9, "已归档");

    private final Integer value;
    private final String description;

    CaseStatusEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 根据值获取枚举
     */
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

    /**
     * 是否是活跃状态
     */
    public boolean isActive() {
        return this == PENDING || this == ACCEPTING || this == ACCEPTED || this == IN_PROGRESS;
    }

    /**
     * 是否是终止状态
     */
    public boolean isTerminal() {
        return this == TERMINATED || this == REVOKED || this == CLOSED || this == ARCHIVED;
    }

    /**
     * 是否可以编辑
     */
    public boolean isEditable() {
        return this != ARCHIVED;
    }

    /**
     * 是否需要原因说明
     */
    public boolean needReason() {
        return this == SUSPENDED || this == TERMINATED || this == REVOKED;
    }

    /**
     * 是否需要审批
     */
    public boolean needApproval() {
        return this == TERMINATED || this == REVOKED || this == CLOSED;
    }

    /**
     * 是否允许重新激活
     */
    public boolean canReactivate() {
        return this == SUSPENDED || this == TERMINATED;
    }
} 