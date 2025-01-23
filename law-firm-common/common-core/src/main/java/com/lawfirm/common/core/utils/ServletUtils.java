package com.lawfirm.common.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet工具类
 */
public class ServletUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取请求参数
     */
    public static String getRequestParams(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        
        Map<String, String> paramMap = new HashMap<>();
        
        // 获取URL参数
        request.getParameterMap().forEach((key, values) -> {
            if (values != null && values.length > 0) {
                paramMap.put(key, StringUtils.arrayToCommaDelimitedString(values));
            }
        });
        
        // 获取body参数
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            try {
                String body = getRequestBody(request);
                if (StringUtils.hasText(body)) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> bodyMap = objectMapper.readValue(body, Map.class);
                    bodyMap.forEach((key, value) -> paramMap.put(key, String.valueOf(value)));
                }
            } catch (IOException e) {
                // ignore
            }
        }
        
        try {
            return objectMapper.writeValueAsString(paramMap);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    /**
     * 获取请求体
     */
    public static String getRequestBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            // ignore
        }
        return sb.toString();
    }

    /**
     * 获取当前登录用户ID
     */
    public static Long getLoginUserId() {
        String userId = getRequest().getHeader("X-User-Id");
        return userId != null ? Long.parseLong(userId) : null;
    }
} 