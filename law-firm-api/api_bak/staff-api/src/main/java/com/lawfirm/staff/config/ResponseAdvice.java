package com.lawfirm.staff.config;

import com.lawfirm.common.model.response.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应处理
 *
 * @author JunyuZhan
 */
@RestControllerAdvice(basePackages = "com.lawfirm.staff.controller")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, 
            MediaType selectedContentType, Class selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        // 如果响应结果已经是Result类型，直接返�?        if (body instanceof Result) {
            return body;
        }
        // 如果是String类型，需要特殊处�?        if (body instanceof String) {
            return Result.success(body);
        }
        // 其他类型，统一包装成Result
        return Result.success(body);
    }
} 
