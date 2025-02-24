package com.lawfirm.common.web.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * 参数验证支持类
 * 提供参数验证功能
 */
public abstract class ValidSupport extends WebSupport {

    /**
     * 获取必填参数
     * 如果参数为空则抛出异常
     *
     * @param paramName 参数名
     * @return 参数值
     */
    protected String getRequiredParameter(String paramName) {
        String value = getRequest().getParameter(paramName);
        Assert.hasText(value, String.format("参数[%s]不能为空", paramName));
        return value;
    }

    /**
     * 获取整数参数
     * 如果参数不是有效的整数则返回null
     *
     * @param paramName 参数名
     * @return 整数参数值
     */
    protected Integer getIntParameter(String paramName) {
        String value = getRequest().getParameter(paramName);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 获取必填整数参数
     * 如果参数为空或不是有效的整数则抛出异常
     *
     * @param paramName 参数名
     * @return 整数参数值
     */
    protected Integer getRequiredIntParameter(String paramName) {
        String value = getRequiredParameter(paramName);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                String.format("参数[%s]必须是有效的整数", paramName));
        }
    }

    /**
     * 获取长整数参数
     * 如果参数不是有效的长整数则返回null
     *
     * @param paramName 参数名
     * @return 长整数参数值
     */
    protected Long getLongParameter(String paramName) {
        String value = getRequest().getParameter(paramName);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 获取必填长整数参数
     * 如果参数为空或不是有效的长整数则抛出异常
     *
     * @param paramName 参数名
     * @return 长整数参数值
     */
    protected Long getRequiredLongParameter(String paramName) {
        String value = getRequiredParameter(paramName);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                String.format("参数[%s]必须是有效的长整数", paramName));
        }
    }

    /**
     * 获取布尔参数
     * 如果参数为空则返回null
     *
     * @param paramName 参数名
     * @return 布尔参数值
     */
    protected Boolean getBooleanParameter(String paramName) {
        String value = getRequest().getParameter(paramName);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Boolean.parseBoolean(value);
    }

    /**
     * 获取必填布尔参数
     * 如果参数为空则抛出异常
     *
     * @param paramName 参数名
     * @return 布尔参数值
     */
    protected Boolean getRequiredBooleanParameter(String paramName) {
        String value = getRequiredParameter(paramName);
        return Boolean.parseBoolean(value);
    }
} 