package com.lawfirm.model.auth.enums;

import lombok.Getter;

/**
 * 用户状态枚举
 */
@Getter
public enum UserStatusEnum {
    
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    
    /**
     * 禁用
     */
    DISABLED(1, "禁用"),
    
    /**
     * 锁定
     */
    LOCKED(2, "锁定");
    
    private final Integer code;
    private final String desc;
    
    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 