package com.lawfirm.common.util.validate;

import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 断言工具类，提供基础的参数校验功能
 */
public class Assert {
    
    /**
     * 断言对象不为空
     */
    public static void notNull(Object object, ResultCode resultCode) {
        if (object == null) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言对象为空
     */
    public static void isNull(Object object, ResultCode resultCode) {
        if (object != null) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言字符串不为空
     */
    public static void notEmpty(String str, ResultCode resultCode) {
        if (StringUtils.isEmpty(str)) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言字符串不为空白
     */
    public static void notBlank(String str, ResultCode resultCode) {
        if (StringUtils.isBlank(str)) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言布尔表达式为true
     */
    public static void isTrue(boolean expression, ResultCode resultCode) {
        if (!expression) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言布尔表达式为false
     */
    public static void isFalse(boolean expression, ResultCode resultCode) {
        if (expression) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言对象为指定类型
     */
    public static void isInstanceOf(Class<?> type, Object obj, ResultCode resultCode) {
        notNull(type, ResultCode.VALIDATION_ERROR);
        if (!type.isInstance(obj)) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言集合不为空
     */
    public static void notEmpty(Collection<?> collection, ResultCode resultCode) {
        if (collection == null || collection.isEmpty()) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言Map不为空
     */
    public static void notEmpty(Map<?, ?> map, ResultCode resultCode) {
        if (map == null || map.isEmpty()) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言数组不为空
     */
    public static void notEmpty(Object[] array, ResultCode resultCode) {
        if (array == null || array.length == 0) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言两个对象相等
     */
    public static void equals(Object obj1, Object obj2, ResultCode resultCode) {
        if (!Objects.equals(obj1, obj2)) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言数值在指定范围内
     */
    public static void inRange(long value, long min, long max, ResultCode resultCode) {
        if (min > max || value < min || value > max) {
            throw new ValidationException(resultCode);
        }
    }

    /**
     * 断言字符串长度在指定范围内
     */
    public static void lengthInRange(String str, int min, int max, ResultCode resultCode) {
        if (str == null) {
            throw new ValidationException(resultCode);
        }
        if (min < 0 || min > max) {
            throw new ValidationException(resultCode);
        }
        int length = str.length();
        if (length < min || length > max) {
            throw new ValidationException(resultCode);
        }
    }
}