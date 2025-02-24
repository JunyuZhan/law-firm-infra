package com.lawfirm.model.base.support.validator;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * 验证器集合
 * 提供常用的验证器
 */
public class Validators {

    /**
     * 非空验证器
     */
    public static <T> Predicate<T> notNull() {
        return obj -> obj != null;
    }

    /**
     * 字符串非空验证器
     */
    public static Predicate<String> notBlank() {
        return str -> str != null && !str.trim().isEmpty();
    }

    /**
     * 集合非空验证器
     */
    public static <T extends Collection<?>> Predicate<T> notEmpty() {
        return collection -> collection != null && !collection.isEmpty();
    }

    /**
     * Map非空验证器
     */
    public static <K, V> Predicate<Map<K, V>> mapNotEmpty() {
        return map -> map != null && !map.isEmpty();
    }

    /**
     * 正则表达式验证器
     */
    public static Predicate<String> regex(String pattern) {
        return str -> str != null && Pattern.matches(pattern, str);
    }

    /**
     * 范围验证器
     */
    public static <T extends Comparable<T>> Predicate<T> range(T min, T max) {
        return value -> value != null && value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    /**
     * 长度验证器
     */
    public static Predicate<String> length(int min, int max) {
        return str -> str != null && str.length() >= min && str.length() <= max;
    }
} 