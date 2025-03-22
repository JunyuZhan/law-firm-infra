package com.lawfirm.api.adaptor;

import org.springframework.beans.BeanUtils;

/**
 * 数据适配器基类
 */
public abstract class BaseAdaptor {

    /**
     * 对象转换
     * 
     * @param <T> 目标类型
     * @param source 源对象
     * @param targetClass 目标类
     * @return 转换后的目标类型对象
     */
    protected <T> T convert(Object source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("对象转换失败", e);
        }
    }
} 