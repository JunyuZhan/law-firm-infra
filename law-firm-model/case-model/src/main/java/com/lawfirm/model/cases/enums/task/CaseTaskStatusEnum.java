package com.lawfirm.model.cases.enums.task;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件任务状态枚举
 */
@Getter
public enum CaseTaskStatusEnum implements BaseEnum<Integer> {

    /**
     * 待分配
     */
    PENDING_ASSIGNMENT(1, "待分配"),

    /**
     * 已分配
     */
    ASSIGNED(2, "已分配"),

    /**
     * 进行中
     */
    IN_PROGRESS(3, "进行中"),

    /**
     * 待审核
     */
    PENDING_REVIEW(4, "待审核"),

    /**
     * 审核中
     */
    IN_REVIEW(5, "审核中"),

    /**
     * 需修改
     */
    NEED_REVISION(6, "需修改"),

    /**
     * 已完成
     */
    COMPLETED(7, "已完成"),

    /**
     * 已取消
     */
    CANCELLED(8, "已取消"),

    /**
     * 已暂停
     */
    SUSPENDED(9, "已暂停"),

    /**
     * 已超时
     */
    OVERDUE(10, "已超时");

    private final Integer value;
    private final String description;

    CaseTaskStatusEnum(Integer value, String description) {
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
    public static CaseTaskStatusEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseTaskStatusEnum status : values()) {
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
        return this == ASSIGNED || this == IN_PROGRESS || 
               this == PENDING_REVIEW || this == IN_REVIEW || 
               this == NEED_REVISION;
    }

    /**
     * 是否是终态
     */
    public boolean isTerminal() {
        return this == COMPLETED || this == CANCELLED;
    }

    /**
     * 是否可编辑
     */
    public boolean isEditable() {
        return this == IN_PROGRESS || this == NEED_REVISION;
    }

    /**
     * 是否需要审核
     */
    public boolean needReview() {
        return this == PENDING_REVIEW || this == IN_REVIEW;
    }

    /**
     * 是否需要原因说明
     */
    public boolean needReason() {
        return this == CANCELLED || this == SUSPENDED || 
               this == NEED_REVISION;
    }

    /**
     * 是否可以重新激活
     */
    public boolean canReactivate() {
        return this == SUSPENDED || this == OVERDUE;
    }

    /**
     * 是否需要通知
     */
    public boolean needNotification() {
        return this == ASSIGNED || this == NEED_REVISION || 
               this == COMPLETED || this == CANCELLED || 
               this == OVERDUE;
    }

    /**
     * 获取下一个可能的状态
     */
    public CaseTaskStatusEnum[] getNextPossibleStatus() {
        switch (this) {
            case PENDING_ASSIGNMENT:
                return new CaseTaskStatusEnum[]{ASSIGNED, CANCELLED};
            case ASSIGNED:
                return new CaseTaskStatusEnum[]{IN_PROGRESS, SUSPENDED, CANCELLED};
            case IN_PROGRESS:
                return new CaseTaskStatusEnum[]{PENDING_REVIEW, SUSPENDED, CANCELLED, OVERDUE};
            case PENDING_REVIEW:
                return new CaseTaskStatusEnum[]{IN_REVIEW, CANCELLED};
            case IN_REVIEW:
                return new CaseTaskStatusEnum[]{NEED_REVISION, COMPLETED, CANCELLED};
            case NEED_REVISION:
                return new CaseTaskStatusEnum[]{IN_PROGRESS, CANCELLED};
            case SUSPENDED:
                return new CaseTaskStatusEnum[]{IN_PROGRESS, CANCELLED};
            case OVERDUE:
                return new CaseTaskStatusEnum[]{IN_PROGRESS, CANCELLED};
            default:
                return new CaseTaskStatusEnum[]{};
        }
    }

    /**
     * 是否需要更新进度
     */
    public boolean needProgressUpdate() {
        return this == IN_PROGRESS || this == COMPLETED;
    }

    /**
     * 是否需要更新工时
     */
    public boolean needTimeTracking() {
        return this == IN_PROGRESS || this == COMPLETED;
    }
} 