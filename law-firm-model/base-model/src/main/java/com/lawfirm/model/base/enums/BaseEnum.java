package com.lawfirm.model.base.enums;

/**
 * 基础枚举接口
 * 所有与数据库交互的枚举类都应实现此接口
 * 设计为支持MyBatis-Plus 3.5.3.1版本的枚举处理
 *
 * @param <T> 枚举值类型
 */
public interface BaseEnum<T> {
    
    /**
     * 获取枚举值
     * 此方法会被MyBatis-Plus用于枚举与数据库值的转换
     *
     * @return 枚举值
     */
    T getValue();
    
    /**
     * 获取枚举描述
     *
     * @return 枚举描述
     */
    String getDescription();
    
    /**
     * 根据值获取对应的枚举实例
     *
     * @param enumClass 枚举类
     * @param value 枚举值
     * @param <E> 枚举类型
     * @param <V> 枚举值类型
     * @return 对应的枚举实例，如果找不到则返回null
     */
    @SuppressWarnings("unchecked")
    static <E extends Enum<E> & BaseEnum<V>, V> E valueOf(Class<E> enumClass, V value) {
        if (value == null) {
            return null;
        }
        
        for (E e : enumClass.getEnumConstants()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
} 