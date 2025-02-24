package com.lawfirm.model.cases.enums.event;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 事件优先级枚举
 */
@Getter
public enum EventPriorityEnum implements BaseEnum<Integer> {

    /**
     * 紧急
     */
    URGENT(1, "紧急"),

    /**
     * 重要
     */
    IMPORTANT(2, "重要"),

    /**
     * 普通
     */
    NORMAL(3, "普通"),

    /**
     * 低优先级
     */
    LOW(4, "低优先级");

    private final Integer value;
    private final String description;

    EventPriorityEnum(Integer value, String description) {
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
    public static EventPriorityEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (EventPriorityEnum priority : values()) {
            if (priority.value.equals(value)) {
                return priority;
            }
        }
        return null;
    }

    /**
     * 是否需要立即处理
     */
    public boolean needImmediateAction() {
        return this == URGENT;
    }

    /**
     * 是否需要提前通知
     */
    public boolean needAdvanceNotice() {
        return this == URGENT || this == IMPORTANT;
    }

    /**
     * 是否可以推迟
     */
    public boolean canBePostponed() {
        return this == NORMAL || this == LOW;
    }

    /**
     * 获取建议提前通知时间（小时）
     */
    public int getSuggestedAdvanceNoticeHours() {
        switch (this) {
            case URGENT:
                return 2;
            case IMPORTANT:
                return 24;
            case NORMAL:
                return 48;
            case LOW:
                return 72;
            default:
                return 24;
        }
    }

    /**
     * 获取建议响应时间（小时）
     */
    public int getSuggestedResponseHours() {
        switch (this) {
            case URGENT:
                return 1;
            case IMPORTANT:
                return 4;
            case NORMAL:
                return 24;
            case LOW:
                return 48;
            default:
                return 24;
        }
    }

    /**
     * 是否需要上报
     */
    public boolean needEscalation() {
        return this == URGENT;
    }

    /**
     * 获取上报等待时间（小时）
     */
    public int getEscalationWaitHours() {
        switch (this) {
            case URGENT:
                return 2;
            case IMPORTANT:
                return 8;
            case NORMAL:
                return 24;
            default:
                return 48;
        }
    }

    /**
     * 是否需要定期提醒
     */
    public boolean needRegularReminder() {
        return this == URGENT || this == IMPORTANT;
    }

    /**
     * 获取提醒间隔（小时）
     */
    public int getReminderIntervalHours() {
        switch (this) {
            case URGENT:
                return 1;
            case IMPORTANT:
                return 4;
            case NORMAL:
                return 12;
            default:
                return 24;
        }
    }
} 