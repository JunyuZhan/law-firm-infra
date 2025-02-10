package com.lawfirm.common.core.enums;

/**
 * 基础枚举接口
 * @param <T> 枚举值类型
 */
public interface BaseEnum<T> {
    /**
     * 获取枚举值
     */
    T getValue();

    /**
     * 获取枚举描述
     */
    String getDescription();
} 