package com.lawfirm.model.task.enums;

import lombok.Getter;

/**
 * 工作任务提醒方式枚举
 */
@Getter
public enum WorkTaskRemindTypeEnum {
    
    /**
     * 站内信
     */
    INNER_MSG(0, "站内消息"),
    
    /**
     * 邮件
     */
    EMAIL(1, "邮件"),
    
    /**
     * 短信
     */
    SMS(2, "短信");
    
    /**
     * 提醒方式码
     */
    private final Integer code;
    
    /**
     * 提醒方式描述
     */
    private final String description;
    
    WorkTaskRemindTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据提醒方式码获取枚举
     */
    public static WorkTaskRemindTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (WorkTaskRemindTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 