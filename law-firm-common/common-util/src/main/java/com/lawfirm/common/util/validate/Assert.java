package com.lawfirm.common.util.validate;

import com.lawfirm.common.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

/**
 * 断言工具类
 */
public class Assert {
    
    /**
     * 断言对象不为空
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言字符串不为空
     */
    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言布尔表达式为true
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言对象为指定类型
     */
    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new BusinessException(message);
        }
    }
} 