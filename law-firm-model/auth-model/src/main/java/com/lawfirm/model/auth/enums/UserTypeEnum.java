package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 用户类型枚举
 */
@Getter
public enum UserTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 系统用户
     */
    SYSTEM(0, "系统用户"),
    
    /**
     * 普通用户
     */
    NORMAL(1, "普通用户");
    
    private final Integer code;
    private final String desc;
    
    UserTypeEnum(Integer code, String desc) {
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