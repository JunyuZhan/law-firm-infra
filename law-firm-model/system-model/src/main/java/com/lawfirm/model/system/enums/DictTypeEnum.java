package com.lawfirm.model.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 字典类型枚举
 * 实现BaseEnum接口，确保与MyBatis-Plus 3.5.3.1兼容
 */
@Getter
public enum DictTypeEnum implements BaseEnum<Integer> {

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
    private final String description;
    
    /**
     * 构造函数
     *
     * @param value 枚举值
     * @param description 枚举描述
     */
    DictTypeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
    
    /**
     * 根据值获取枚举
     *
     * @param value 枚举值
     * @return 枚举实例
     */
    public static DictTypeEnum fromValue(Integer value) {
        return BaseEnum.valueOf(DictTypeEnum.class, value);
    }
} 