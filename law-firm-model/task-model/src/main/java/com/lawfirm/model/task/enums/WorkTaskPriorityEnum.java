package com.lawfirm.model.task.enums;

import lombok.Getter;

/**
 * 工作任务优先级枚举
 */
@Getter
public enum WorkTaskPriorityEnum {
    
    /**
     * 低
     */
    LOW(0, "低"),
    
    /**
     * 中
     */
    MEDIUM(1, "中"),
    
    /**
     * 高
     */
    HIGH(2, "高");
    
    /**
     * 优先级码
     */
    private final Integer code;
    
    /**
     * 优先级描述
     */
    private final String description;
    
    WorkTaskPriorityEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据优先级码获取枚举
     */
    public static WorkTaskPriorityEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (WorkTaskPriorityEnum priority : values()) {
            if (priority.getCode().equals(code)) {
                return priority;
            }
        }
        return null;
    }
} 