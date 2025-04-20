package com.lawfirm.model.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 配置类型枚举
 * 实现BaseEnum接口，确保与MyBatis-Plus 3.5.3.1兼容
 */
@Getter
public enum ConfigTypeEnum implements BaseEnum<String> {

    /**
     * 系统配置
     */
    SYSTEM("SYSTEM", "系统配置"),
    
    /**
     * 业务配置
     */
    BUSINESS("BUSINESS", "业务配置"),
    
    /**
     * 安全配置
     */
    SECURITY("SECURITY", "安全配置"),
    
    /**
     * 其他配置
     */
    OTHER("OTHER", "其他配置");

    /**
     * 配置类型值
     */
    @EnumValue
    @JsonValue
    private final String value;
    
    /**
     * 配置类型描述
     */
    private final String description;
    
    /**
     * 构造函数
     *
     * @param value 枚举值
     * @param description 枚举描述
     */
    ConfigTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
    
    /**
     * 根据值获取枚举
     *
     * @param value 枚举值
     * @return 枚举实例
     */
    public static ConfigTypeEnum fromValue(String value) {
        return BaseEnum.valueOf(ConfigTypeEnum.class, value);
    }
} 