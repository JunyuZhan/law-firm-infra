package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 用户状态枚举
 */
@Getter
public enum UserStatusEnum implements BaseEnum<Integer> {
    
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

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String getDescription() {
        return desc;
    }
} 