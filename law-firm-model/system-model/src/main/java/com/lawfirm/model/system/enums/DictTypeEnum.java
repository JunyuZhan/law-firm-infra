package com.lawfirm.model.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字典类型枚举
 */
@Getter
@AllArgsConstructor
public enum DictTypeEnum {

    /**
     * 系统字典
     */
    SYSTEM(1, "系统字典"),

    /**
     * 业务字典
     */
    BUSINESS(2, "业务字典"),

    /**
     * 状态字典
     */
    STATUS(3, "状态字典"),

    /**
     * 其他字典
     */
    OTHER(4, "其他字典");

    /**
     * 类型值
     */
    @EnumValue
    @JsonValue
    private final Integer value;

    /**
     * 类型描述
     */
    private final String desc;
} 