package com.lawfirm.common.core.context;

import com.lawfirm.common.core.exception.FrameworkException;
import com.lawfirm.common.core.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础上下文处理器
 * 提供线程安全的上下文存储和访问机制
 */
@Slf4j
public class BaseContextHandler {
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    /**
     * 设置上下文属性
     */
    public static void set(String key, Object value) {
        if (key == null) {
            throw new FrameworkException(ResultCode.BAD_REQUEST, "Context key cannot be null");
        }
        getContextMap().put(key, value);
    }

    /**
     * 获取上下文属性
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        if (key == null) {
            throw new FrameworkException(ResultCode.BAD_REQUEST, "Context key cannot be null");
        }
        return (T) getContextMap().get(key);
    }

    /**
     * 获取上下文属性，带默认值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, T defaultValue) {
        T value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 移除上下文属性
     */
    public static void remove(String key) {
        getContextMap().remove(key);
    }

    /**
     * 清空上下文
     */
    public static void clear() {
        THREAD_LOCAL.remove();
    }

    /**
     * 获取上下文Map
     */
    private static Map<String, Object> getContextMap() {
        return THREAD_LOCAL.get();
    }

    /**
     * 获取所有上下文属性
     */
    public static Map<String, Object> getAll() {
        return new HashMap<>(getContextMap());
    }

    /**
     * 获取当前用户ID
     */
    public static Long getUserId() {
        return get("userId");
    }
    
    /**
     * 设置当前用户ID
     */
    public static void setUserId(Long userId) {
        set("userId", userId);
    }
}