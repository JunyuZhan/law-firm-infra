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

    // 排除路径列表 - 更加完整的排除列表
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
        "/v3/api-docs",
        "/swagger-ui",
        "/doc.html",
        "/swagger-resources",
        "/webjars/",
        "/raw-json",
        "/api-docs",
        "/swagger-config",
        "/swagger",
        "/api/v3/api-docs",
        "/api/swagger-ui",
        "/api/doc.html",
        "/api/swagger-resources"
    );

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果路径或类名包含API文档关键字，直接返回false不进行处理
        if (returnType == null || returnType.getContainingClass() == null) {
            return false;
        }
        
        String className = returnType.getContainingClass().getName();
        // 如果类名包含以下关键字，不进行处理
        if (className.contains("springdoc") || 
            className.contains("swagger") || 
            className.contains("knife4j") ||
            className.contains("springfox") ||
            className.contains("OpenAPI") ||
            className.contains("Swagger") ||
            className.contains("ApiResource") ||
            className.contains("ApiDoc")) {
            return false;
        }
        
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 检查请求路径                         
        String path = request.getURI().getPath();
        
        // 对于API文档相关的请求，直接返回原始内容
        for (String excludePath : EXCLUDE_PATHS) {
            if (path.contains(excludePath)) {
                return body;
            }
        }
        
        // 如果是API文档相关内容类型，直接返回
        String contentType = selectedContentType.toString().toLowerCase();
        if (contentType.contains("swagger") || 
            contentType.contains("api-docs") || 
            contentType.contains("openapi") ||
            contentType.contains("application/yaml")) {
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