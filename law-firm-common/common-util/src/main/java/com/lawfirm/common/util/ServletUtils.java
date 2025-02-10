package com.lawfirm.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson2.JSON;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

/**
 * Servlet工具类
 */
public class ServletUtils {

    /**
     * 获取当前请求对象
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("当前线程中不存在Request上下文");
        }
        return ((ServletRequestAttributes) attributes).getRequest();
    }

    /**
     * 获取当前登录用户ID
     */
    public static String getLoginUserId() {
        // TODO: 从SecurityContext中获取当前登录用户ID
        return "anonymous";
    }

    /**
     * 获取请求参数
     */
    public static String getRequestParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            params.put(name, request.getParameter(name));
        }
        return JSON.toJSONString(params);
    }
} 