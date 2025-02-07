package com.lawfirm.common.data.processor;

/**
 * 实体处理器
 *
 * @param <T> 实体类型
 */
public interface EntityProcessor<T> {

    /**
     * 创建前处理
     */
    default void beforeCreate(T entity) {
    }

    /**
     * 更新前处理
     */
    default void beforeUpdate(T entity) {
    }

    /**
     * 加载后处理
     */
    default void afterLoad(T entity) {}
} 