package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件优先级枚举
 */
@Getter
public enum CasePriorityEnum implements BaseEnum<String> {
    
    URGENT("紧急", 5),
    HIGH("高优先级", 4),
    MEDIUM("中优先级", 3),
    LOW("低优先级", 2),
    NORMAL("普通", 1);

    private final String description;
    private final Integer priority;

    CasePriorityEnum(String description, Integer priority) {
        this.description = description;
        this.priority = priority;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 获取优先级数值
     * @return 优先级数值
     */
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * 判断是否为紧急优先级
     * @return 是否紧急
     */
    public boolean isUrgent() {
        return this == URGENT;
    }

    /**
     * 判断是否为高优先级
     * @return 是否高优先级
     */
    public boolean isHigh() {
        return this == HIGH;
    }

    /**
     * 判断是否为低优先级
     * @return 是否低优先级
     */
    public boolean isLow() {
        return this == LOW || this == NORMAL;
    }

    /**
     * 比较优先级
     * @param other 其他优先级
     * @return 比较结果
     */
    public int compareByPriority(CasePriorityEnum other) {
        return this.priority.compareTo(other.priority);
    }

    /**
     * 判断是否高于指定优先级
     * @param other 其他优先级
     * @return 是否更高优先级
     */
    public boolean isHigherThan(CasePriorityEnum other) {
        return this.priority > other.priority;
    }

    /**
     * 判断是否低于指定优先级
     * @param other 其他优先级
     * @return 是否更低优先级
     */
    public boolean isLowerThan(CasePriorityEnum other) {
        return this.priority < other.priority;
    }
} 