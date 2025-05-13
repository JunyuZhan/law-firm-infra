package com.lawfirm.api.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.core.api.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应处理器
 * 确保所有API响应使用统一的格式，特别兼容vue-vben-admin前端
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 获取类名进行检查
        String className = returnType.getContainingClass().getName();
        
        // 排除已经是CommonResult类型的响应和特定控制器
        boolean isCommonResult = returnType.getParameterType().isAssignableFrom(CommonResult.class);
        boolean isErrorController = returnType.getDeclaringClass().getName().contains("ErrorController");
        boolean isHealthController = returnType.getDeclaringClass().getName().contains("HealthCheckController");
        boolean isHomeController = returnType.getDeclaringClass().getName().contains("HomeController");
        
        // 排除API文档相关类
        boolean isApiDocClass = className.contains("springdoc") || 
                                 className.contains("swagger") ||
                                 className.contains("openapi") ||
                                 className.contains("knife4j") ||
                                 className.contains("ApiDoc");
        
        // 不处理已经是标准格式或特定控制器的响应，或API文档相关响应
        return !isCommonResult && !isErrorController && !isHealthController && 
               !isHomeController && !isApiDocClass;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
        // 获取请求路径
        String path = request.getURI().getPath();
        
        // 排除API文档相关路径
        if (path.contains("/v3/api-docs") || 
            path.contains("/swagger-ui") || 
            path.contains("/doc.html") || 
            path.contains("/api-docs") ||
            path.contains("/webjars") ||
            path.contains("/swagger-resources")) {
            log.debug("API文档请求，不包装响应: {}", path);
            return body;
        }
        
        // HTML类型响应不包装
        if (MediaType.TEXT_HTML.equals(selectedContentType) ||
            (selectedContentType != null && selectedContentType.toString().contains("text/html"))) {
            log.debug("HTML类型请求，不包装响应: {}", request.getURI().getPath());
            return body;
        }
        
        // 字符串类型特殊处理
        if (body instanceof String) {
            // 将字符串类型转换为CommonResult后序列化为JSON字符串
            CommonResult<String> result = CommonResult.success((String) body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return objectMapper.writeValueAsString(result);
        }
        
        // 如果body为null，返回空成功结果
        if (body == null) {
            return CommonResult.success();
        }
        
        // 其他情况，包装为统一响应格式
        return CommonResult.success(body);
    }
} 