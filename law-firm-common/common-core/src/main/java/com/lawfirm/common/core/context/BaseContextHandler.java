package com.lawfirm.common.core.context;

import com.lawfirm.common.core.exception.BaseException;

import java.util.HashMap;
import java.util.Map;

public class BaseContextHandler {
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = getThreadLocalMap();
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = getThreadLocalMap();
        return map.get(key);
    }

    public static String getUserId() {
        return get("currentUserId") == null ? null : get("currentUserId").toString();
    }

    public static void setUserId(String userId) {
        set("currentUserId", userId);
    }

    public static String getUserName() {
        return get("currentUserName") == null ? null : get("currentUserName").toString();
    }

    public static void setUserName(String userName) {
        set("currentUserName", userName);
    }

    public static Integer getUserType() {
        return get("currentUserType") == null ? null : (Integer) get("currentUserType");
    }

    public static void setUserType(Integer userType) {
        set("currentUserType", userType);
    }

    private static Map<String, Object> getThreadLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(10);
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
} 