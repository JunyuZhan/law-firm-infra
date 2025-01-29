package com.lawfirm.common.data.processor;

/**
 * 实体处理器接口
 * @param <T> 实体类型
 * @param <D> DTO类型
 */
public interface EntityProcessor<T, D> {
    /**
     * 创建前处理
     */
    default void beforeCreate(T entity) {}

    /**
     * 更新前处理
     */
    default void beforeUpdate(T entity) {}

    /**
     * 加载后处理
     */
    default void afterLoad(D dto, T entity) {}
} 