package com.lawfirm.model.cases.enums.event;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 事件状态枚举
 */
@Getter
public enum EventStatusEnum implements BaseEnum<Integer> {

    /**
     * 待安排
     */
    PENDING(1, "待安排"),

    /**
     * 已安排
     */
    SCHEDULED(2, "已安排"),

    /**
     * 准备中
     */
    PREPARING(3, "准备中"),

    /**
     * 进行中
     */
    IN_PROGRESS(4, "进行中"),

    /**
     * 已完成
     */
    COMPLETED(5, "已完成"),

    /**
     * 已取消
     */
    CANCELLED(6, "已取消"),

    /**
     * 已延期
     */
    POSTPONED(7, "已延期"),

    /**
     * 需跟进
     */
    NEED_FOLLOW_UP(8, "需跟进");

    private final Integer value;
    private final String description;

    EventStatusEnum(Integer value, String description) {
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
    public static EventStatusEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (EventStatusEnum status : values()) {
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
        return this == PENDING || this == SCHEDULED || 
               this == PREPARING || this == IN_PROGRESS;
    }

    /**
     * 是否是终态
     */
    public boolean isTerminal() {
        return this == COMPLETED || this == CANCELLED;
    }

    /**
     * 是否需要原因说明
     */
    public boolean needReason() {
        return this == CANCELLED || this == POSTPONED || 
               this == NEED_FOLLOW_UP;
    }

    /**
     * 是否可以重新安排
     */
    public boolean canReschedule() {
        return this == CANCELLED || this == POSTPONED;
    }

    /**
     * 是否需要总结报告
     */
    public boolean needSummaryReport() {
        return this == COMPLETED || this == NEED_FOLLOW_UP;
    }

    /**
     * 是否需要通知相关人员
     */
    public boolean needNotification() {
        return this == SCHEDULED || this == CANCELLED || 
               this == POSTPONED;
    }

    /**
     * 获取下一个可能的状态
     */
    public EventStatusEnum[] getNextPossibleStatus() {
        switch (this) {
            case PENDING:
                return new EventStatusEnum[]{SCHEDULED, CANCELLED};
            case SCHEDULED:
                return new EventStatusEnum[]{PREPARING, POSTPONED, CANCELLED};
            case PREPARING:
                return new EventStatusEnum[]{IN_PROGRESS, POSTPONED, CANCELLED};
            case IN_PROGRESS:
                return new EventStatusEnum[]{COMPLETED, NEED_FOLLOW_UP, CANCELLED};
            case NEED_FOLLOW_UP:
                return new EventStatusEnum[]{COMPLETED, CANCELLED};
            case POSTPONED:
                return new EventStatusEnum[]{SCHEDULED, CANCELLED};
            default:
                return new EventStatusEnum[]{};
        }
    }
} 