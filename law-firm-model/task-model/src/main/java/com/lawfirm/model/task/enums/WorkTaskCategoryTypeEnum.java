package com.lawfirm.model.task.enums;

import lombok.Getter;

/**
 * 工作任务分类类型枚举
 */
@Getter
public enum WorkTaskCategoryTypeEnum {
    
    /**
     * 系统分类
     */
    SYSTEM(0, "系统分类"),
    
    /**
     * 自定义分类
     */
    CUSTOM(1, "自定义分类");
    
    /**
     * 类型编码
     */
    private final Integer code;
    
    /**
     * 类型描述
     */
    private final String description;
    
    WorkTaskCategoryTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static WorkTaskCategoryTypeEnum getByCode(Integer code) {
        for (WorkTaskCategoryTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 