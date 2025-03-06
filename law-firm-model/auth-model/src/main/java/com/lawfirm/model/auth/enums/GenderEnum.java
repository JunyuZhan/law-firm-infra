package com.lawfirm.model.auth.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 性别枚举
 */
@Getter
public enum GenderEnum implements BaseEnum<Integer> {
    
    /**
     * 未知
     */
    UNKNOWN(0, "未知"),
    
    /**
     * 男
     */
    MALE(1, "男"),
    
    /**
     * 女
     */
    FEMALE(2, "女");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;
    
    GenderEnum(Integer code, String desc) {
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