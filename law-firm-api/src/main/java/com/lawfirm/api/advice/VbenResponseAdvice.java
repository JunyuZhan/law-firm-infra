package com.lawfirm.api.advice;

import com.lawfirm.api.VbenResult;
import com.lawfirm.common.core.api.CommonResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.List;

/**
 * 统一响应格式转换器
 * 将所有响应转换为前端期望的格式
 */
@RestControllerAdvice
public class VbenResponseAdvice implements ResponseBodyAdvice<Object> {

    // 排除路径列表
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
        "/v3/api-docs",
        "/swagger-ui",
        "/doc.html",
        "/swagger-resources",
        "/webjars/",
        "/raw-json"
    );

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 排除Swagger/Knife4j相关的包和类
        String className = returnType.getContainingClass().getName();
        return !(className.contains("springdoc") || 
            className.contains("swagger") || 
            className.contains("knife4j") ||
            className.contains("springfox") ||
            className.contains("SwaggerConfigController"));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 如果请求路径包含API文档相关路径，不进行处理
        String path = request.getURI().getPath();
        
        // 检查是否应该排除当前路径
        if (EXCLUDE_PATHS.stream().anyMatch(path::contains)) {
            return body;
        }
                                      
        // 如果响应体已经是VbenResult类型，直接返回
        if (body instanceof VbenResult) {
            return body;
        }
        
        // 如果响应体是CommonResult类型，转换为VbenResult格式
        if (body instanceof CommonResult) {
            CommonResult<?> commonResult = (CommonResult<?>) body;
            
            if (commonResult.isSuccess()) {
                return VbenResult.success(commonResult.getData(), commonResult.getMessage());
            } else {
                return VbenResult.error(commonResult.getCode(), commonResult.getMessage());
            }
        }
        
        // 其他类型响应，包装为成功响应
        return VbenResult.success(body);
    }
} 