package com.lawfirm.common.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Bean属性复制工具类
 * 对Spring的BeanUtils进行扩展，提供更多实用功能
 */
public class BeanUtils {

    /**
     * 复制对象属性到目标类型（会创建新实例）
     *
     * @param source 源对象
     * @param targetClass 目标类型
     * @param <T> 目标类型泛型
     * @return 目标对象实例
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T target = instantiate(targetClass);
        org.springframework.beans.BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 复制对象属性，忽略null值
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        if (source == null) {
            return;
        }
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * 复制List中的对象属性到目标类型
     *
     * @param sourceList 源对象列表
     * @param targetClass 目标类型
     * @param <T> 目标类型泛型
     * @return 目标对象列表
     */
    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            targetList.add(copyProperties(source, targetClass));
        }
        return targetList;
    }

    /**
     * 复制List中的对象属性到目标类型，忽略null值
     *
     * @param sourceList 源对象列表
     * @param targetClass 目标类型
     * @param <T> 目标类型泛型
     * @return 目标对象列表
     */
    public static <T> List<T> copyListIgnoreNull(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            T target = instantiate(targetClass);
            copyPropertiesIgnoreNull(source, target);
            targetList.add(target);
        }
        return targetList;
    }

    /**
     * 获取对象中值为null的属性名数组
     *
     * @param source 源对象
     * @return null值属性名数组
     */
    private static String[] getNullPropertyNames(Object source) {
        Assert.notNull(source, "Source must not be null");
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            try {
                if (pd.getReadMethod() != null && src.getPropertyValue(pd.getName()) == null) {
                    nullPropertyNames.add(pd.getName());
                }
            } catch (Exception e) {
                // ignore
            }
        }
        return nullPropertyNames.toArray(new String[0]);
    }

    /**
     * 实例化目标类
     *
     * @param targetClass 目标类
     * @param <T> 目标类型
     * @return 目标类实例
     */
    public static <T> T instantiate(Class<T> targetClass) {
        Assert.notNull(targetClass, "目标类不能为null");
        try {
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("无法实例化目标类: " + targetClass.getName(), e) {};
        }
    }

    /**
     * 复制分页对象，将源分页对象中的记录复制为目标类型
     *
     * @param page 源分页对象
     * @param targetClass 目标类型
     * @param <T> 目标类型泛型
     * @param <S> 源类型泛型
     * @return 新的分页对象，包含转换后的记录
     */
    public static <T, S> Page<T> copyPage(Page<S> page, Class<T> targetClass) {
        if (page == null) {
            return null;
        }
        Page<T> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
            
        List<S> records = page.getRecords();
        List<T> targetRecords = copyList(records, targetClass);
        result.setRecords(targetRecords);
        
        return result;
    }
} 