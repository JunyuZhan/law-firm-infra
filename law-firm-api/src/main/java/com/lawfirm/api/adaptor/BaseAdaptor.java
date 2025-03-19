package com.lawfirm.api.adaptor;

import org.springframework.beans.BeanUtils;

/**
 * 数据适配器基类
 */
public abstract class BaseAdaptor {

    /**
     * 对象转换
     */
    protected <T> T convert(Object source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("对象转换失败", e);
        }
    }
} 