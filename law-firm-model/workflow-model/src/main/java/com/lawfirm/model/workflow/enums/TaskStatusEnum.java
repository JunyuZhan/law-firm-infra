package com.lawfirm.model.workflow.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 任务状态枚举
 */
@Getter
public enum TaskStatusEnum implements BaseEnum<Integer> {
    
    /**
     * 待处理
     */
    PENDING(0, "待处理"),
    
    /**
     * 处理中
     */
    PROCESSING(1, "处理中"),
    
    /**
     * 已完成
     */
    COMPLETED(2, "已完成"),
    
    /**
     * 已取消
     */
    CANCELLED(3, "已取消");
    
    private final Integer value;
    private final String desc;
    
    TaskStatusEnum(Integer value, String desc) {
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
    
    public static TaskStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (TaskStatusEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
} 