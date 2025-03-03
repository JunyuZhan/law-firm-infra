package com.lawfirm.common.web.response;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.web.constant.WebConstants;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP响应处理工具类
 * 提供直接向HttpServletResponse写入内容的各种方法
 */
@Slf4j
public class ResponseUtils {

    private ResponseUtils() {
        // 工具类不应被实例化
    }
    
    /**
     * 写入错误响应
     *
     * @param response 响应对象
     * @param message 错误消息
     */
    public static void writeErrorResponse(HttpServletResponse response, String message) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            
            String json = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":null}", 
                WebConstants.ERROR_CODE, escapeJson(message));
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error("写入错误响应失败", e);
        }
    }
    
    /**
     * 写入错误响应
     *
     * @param response 响应对象
     * @param code 错误码
     * @param message 错误消息
     */
    public static void writeErrorResponse(HttpServletResponse response, int code, String message) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(code);
            
            String json = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":null}", 
                code, escapeJson(message));
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error("写入错误响应失败", e);
        }
    }
    
    /**
     * 使用CommonResult写入错误响应
     *
     * @param response 响应对象
     * @param resultCode 结果码枚举
     */
    public static void writeErrorResponse(HttpServletResponse response, ResultCode resultCode) {
        CommonResult<?> result = CommonResult.error(resultCode);
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(resultCode.getCode());
            
            String json = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":null}", 
                result.getCode(), escapeJson(result.getMessage()));
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error("写入错误响应失败", e);
        }
    }
    
    /**
     * 写入成功响应
     *
     * @param response 响应对象
     * @param message 成功消息
     */
    public static void writeSuccessResponse(HttpServletResponse response, String message) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(HttpServletResponse.SC_OK);
            
            String json = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":null}", 
                WebConstants.SUCCESS_CODE, escapeJson(message));
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error("写入成功响应失败", e);
        }
    }
    
    /**
     * 写入成功响应（带数据）
     *
     * @param response 响应对象
     * @param data 响应数据
     */
    public static <T> void writeSuccessResponse(HttpServletResponse response, T data) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(HttpServletResponse.SC_OK);
            
            // 简单数据类型，如字符串、数字等，可以直接序列化
            // 复杂对象应该使用JSON库序列化，此处简化处理
            String dataStr = data == null ? "null" : 
                (data instanceof String ? "\"" + escapeJson(data.toString()) + "\"" : data.toString());
            
            String json = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":%s}", 
                WebConstants.SUCCESS_CODE, "操作成功", dataStr);
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error("写入成功响应失败", e);
        }
    }
    
    /**
     * 转义JSON字符串
     * 
     * @param input 输入字符串
     * @return 转义后的字符串
     */
    private static String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\\') {
                out.append("\\\\");
            } else if (c == '\"') {
                out.append("\\\"");
            } else if (c == '\b') {
                out.append("\\b");
            } else if (c == '\f') {
                out.append("\\f");
            } else if (c == '\n') {
                out.append("\\n");
            } else if (c == '\r') {
                out.append("\\r");
            } else if (c == '\t') {
                out.append("\\t");
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
} 