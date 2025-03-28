package com.lawfirm.api.advice;

import com.lawfirm.common.core.api.CommonResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Vue-Vben-Admin响应格式转换器
 * 将系统CommonResult转换为Vben期望的格式
 */
@RestControllerAdvice
public class VbenResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 只处理CommonResult类型的响应
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 如果响应体已经是CommonResult类型
        if (body instanceof CommonResult) {
            CommonResult<?> commonResult = (CommonResult<?>) body;
            
            // 创建符合Vben格式的Map
            Map<String, Object> vbenResult = new HashMap<>(4);
            vbenResult.put("code", commonResult.getCode());
            vbenResult.put("result", commonResult.getData());
            vbenResult.put("message", commonResult.getMessage());
            vbenResult.put("type", commonResult.isSuccess() ? "success" : "error");
            
            return vbenResult;
        }
        
        // 原样返回非CommonResult类型
        return body;
    }
} 