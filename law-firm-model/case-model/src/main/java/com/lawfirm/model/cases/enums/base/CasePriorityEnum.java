package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件优先级枚举
 */
@Getter
public enum CasePriorityEnum implements BaseEnum<Integer> {

    /**
     * 最高优先级
     */
    HIGHEST(1, "最高优先级"),

    /**
     * 高优先级
     */
    HIGH(2, "高优先级"),

    /**
     * 中等优先级
     */
    MEDIUM(3, "中等优先级"),

    /**
     * 低优先级
     */
    LOW(4, "低优先级"),

    /**
     * 最低优先级
     */
    LOWEST(5, "最低优先级");

    private final Integer value;
    private final String description;

    CasePriorityEnum(Integer value, String description) {
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
    public static CasePriorityEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CasePriorityEnum priority : values()) {
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
        return this == HIGHEST || this == HIGH;
    }

    /**
     * 是否是低优先级
     */
    public boolean isLowPriority() {
        return this == LOW || this == LOWEST;
    }

    /**
     * 获取优先级顺序（数字越小优先级越高）
     */
    public int getOrder() {
        return value;
    }

    /**
     * 比较优先级
     * @return 大于0表示当前优先级更低，小于0表示当前优先级更高，等于0表示相同
     */
    public int compareByPriority(CasePriorityEnum other) {
        if (other == null) {
            return -1;
        }
        return this.value.compareTo(other.value);
    }

    /**
     * 是否需要特别关注
     */
    public boolean needSpecialAttention() {
        return this == HIGHEST;
    }

    /**
     * 获取建议响应时间（小时）
     */
    public int getSuggestedResponseHours() {
        switch (this) {
            case HIGHEST:
                return 2;
            case HIGH:
                return 4;
            case MEDIUM:
                return 8;
            case LOW:
                return 24;
            case LOWEST:
                return 48;
            default:
                return 24;
        }
    }

    /**
     * 获取建议处理时间（天）
     */
    public int getSuggestedProcessingDays() {
        switch (this) {
            case HIGHEST:
                return 1;
            case HIGH:
                return 3;
            case MEDIUM:
                return 5;
            case LOW:
                return 7;
            case LOWEST:
                return 10;
            default:
                return 5;
        }
    }
} 