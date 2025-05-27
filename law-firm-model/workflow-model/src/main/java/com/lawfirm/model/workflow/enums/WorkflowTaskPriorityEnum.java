package com.lawfirm.model.workflow.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 工作流任务优先级枚举
 */
@Getter
public enum WorkflowTaskPriorityEnum implements BaseEnum<Integer> {
    
    /**
     * 低优先级
     */
    LOW(1, "低"),
    
    /**
     * 中优先级
     */
    MEDIUM(2, "中"),
    
    /**
     * 高优先级
     */
    HIGH(3, "高"),
    
    /**
     * 紧急
     */
    URGENT(4, "紧急");
    
    private final Integer value;
    private final String desc;
    
    WorkflowTaskPriorityEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }
    
    public static WorkflowTaskPriorityEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (WorkflowTaskPriorityEnum priority : values()) {
            if (priority.getValue().equals(value)) {
                return priority;
            }
        }
        return null;
    }
} 