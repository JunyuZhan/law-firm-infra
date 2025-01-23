package com.lawfirm.model.base.enums;

/**
 * 基础枚举接口
 */
public interface BaseEnum<T> {
    
    /**
     * 获取枚举值
     */
    T getValue();
    
    /**
     * 获取描述
     */
    String getDescription();
} 