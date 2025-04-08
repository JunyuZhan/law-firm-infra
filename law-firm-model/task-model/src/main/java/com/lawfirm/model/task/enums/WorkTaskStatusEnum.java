package com.lawfirm.model.task.enums;

import lombok.Getter;

/**
 * 工作任务状态枚举
 */
@Getter
public enum WorkTaskStatusEnum {
    
    /**
     * 待办
     */
    TODO(0, "待办"),
    
    /**
     * 进行中
     */
    IN_PROGRESS(1, "进行中"),
    
    /**
     * 已完成
     */
    COMPLETED(2, "已完成"),
    
    /**
     * 已取消
     */
    CANCELLED(3, "已取消");
    
    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 状态描述
     */
    private final String description;
    
    WorkTaskStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据状态码获取枚举
     */
    public static WorkTaskStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (WorkTaskStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 