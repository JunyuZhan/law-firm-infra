package com.lawfirm.model.cases.enums.task;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 任务优先级枚举
 */
@Getter
public enum TaskPriorityEnum implements BaseEnum<Integer> {

    /**
     * 紧急
     */
    URGENT(1, "紧急"),

    /**
     * 高
     */
    HIGH(2, "高"),

    /**
     * 中
     */
    MEDIUM(3, "中"),

    /**
     * 低
     */
    LOW(4, "低"),

    /**
     * 可选
     */
    OPTIONAL(5, "可选");

    private final Integer value;
    private final String description;

    TaskPriorityEnum(Integer value, String description) {
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
    public static TaskPriorityEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (TaskPriorityEnum priority : values()) {
            if (priority.value.equals(value)) {
                return priority;
            }
        }
        return null;
    }

    /**
     * 是否是高优先级
     */
    public boolean isHighPriority() {
        return this == URGENT || this == HIGH;
    }

    /**
     * 是否需要立即处理
     */
    public boolean needImmediateAction() {
        return this == URGENT;
    }

    /**
     * 是否可以推迟
     */
    public boolean canBePostponed() {
        return this == LOW || this == OPTIONAL;
    }

    /**
     * 获取建议响应时间（小时）
     */
    public int getSuggestedResponseHours() {
        switch (this) {
            case URGENT:
                return 1;
            case HIGH:
                return 4;
            case MEDIUM:
                return 8;
            case LOW:
                return 24;
            case OPTIONAL:
                return 48;
            default:
                return 8;
        }
    }

    /**
     * 获取建议完成时间（小时）
     */
    public int getSuggestedCompletionHours() {
        switch (this) {
            case URGENT:
                return 4;
            case HIGH:
                return 24;
            case MEDIUM:
                return 48;
            case LOW:
                return 72;
            case OPTIONAL:
                return 120;
            default:
                return 48;
        }
    }

    /**
     * 是否需要定期提醒
     */
    public boolean needRegularReminder() {
        return this == URGENT || this == HIGH;
    }

    /**
     * 获取提醒间隔（小时）
     */
    public int getReminderIntervalHours() {
        switch (this) {
            case URGENT:
                return 1;
            case HIGH:
                return 4;
            case MEDIUM:
                return 8;
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
            case HIGH:
                return 8;
            case MEDIUM:
                return 24;
            default:
                return 48;
        }
    }
} 