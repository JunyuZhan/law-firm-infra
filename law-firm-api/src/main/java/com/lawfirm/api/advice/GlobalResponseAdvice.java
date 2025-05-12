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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 检查是否为API文档相关请求
     */
    private boolean isApiDocRequest(String path) {
        if (path == null) {
            return false;
        }
        
        return path.contains("/v3/api-docs") || 
               path.contains("/swagger-ui") || 
               path.contains("/swagger-resources") ||
               path.contains("/webjars/swagger-ui");
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 排除已经是CommonResult类型的响应和特定控制器
        boolean isCommonResult = returnType.getParameterType().isAssignableFrom(CommonResult.class);
        boolean isErrorController = returnType.getDeclaringClass().getName().contains("ErrorController");
        
        // 排除所有与OpenAPI/Swagger相关的控制器和路径
        boolean isApiDocController = returnType.getDeclaringClass().getName().contains("OpenApi") || 
                returnType.getDeclaringClass().getName().contains("Swagger") ||
                returnType.getDeclaringClass().getName().contains("springdoc") ||
                returnType.getDeclaringClass().getName().contains("ApiDoc");
        
        // 排除健康检查接口
        boolean isHealthController = returnType.getDeclaringClass().getName().contains("HealthCheckController");
        
        // 排除返回HTML内容的方法
        boolean isHtmlContent = false;
        
        // 检查是否有GetMapping注解且produces是HTML
        if (returnType.hasMethodAnnotation(GetMapping.class)) {
            GetMapping getMapping = returnType.getMethodAnnotation(GetMapping.class);
            if (getMapping != null && getMapping.produces().length > 0) {
                for (String produce : getMapping.produces()) {
                    if (produce.contains(MediaType.TEXT_HTML_VALUE) || 
                        produce.contains("application/json") ||
                        produce.contains("application/yaml") ||
                        produce.contains("*/*")) {
                        isHtmlContent = true;
                        break;
                    }
                }
            }
        }
        
        // 检查是否有RequestMapping注解且produces是HTML或API文档相关
        if (!isHtmlContent && returnType.hasMethodAnnotation(RequestMapping.class)) {
            RequestMapping requestMapping = returnType.getMethodAnnotation(RequestMapping.class);
            if (requestMapping != null && requestMapping.produces().length > 0) {
                for (String produce : requestMapping.produces()) {
                    if (produce.contains(MediaType.TEXT_HTML_VALUE) || 
                        produce.contains("application/json") ||
                        produce.contains("application/yaml") ||
                        produce.contains("*/*")) {
                        isHtmlContent = true;
                        break;
                    }
                }
            }
            
            // 检查路径是否与API文档相关
            if (requestMapping.path().length > 0) {
                for (String path : requestMapping.path()) {
                    if (isApiDocRequest(path)) {
                        isApiDocController = true;
                        break;
                    }
                }
            }
        }
        
        // 不处理已经是标准格式、错误控制器、API文档控制器、健康检查控制器的响应和HTML内容
        return !isCommonResult && !isErrorController && !isApiDocController && !isHealthController && !isHtmlContent;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
        // 获取请求路径
        String requestPath = request.getURI().getPath();
        
        // 完全排除API文档请求的处理
        if (isApiDocRequest(requestPath)) {
            return body;
        }
        
        // 如果是HTML内容类型或API文档相关的内容类型，直接返回
        if (MediaType.TEXT_HTML.equals(selectedContentType) || 
            (selectedContentType != null && selectedContentType.toString().contains("text/html"))) {
            return body;
        }
        
        // 如果是字符串类型，需要特殊处理
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
        
        // 如果body已经是CommonResult类型，直接返回
        if (body instanceof CommonResult) {
            return body;
        }
        
        // 其他类型直接包装成CommonResult
        return CommonResult.success(body);
    }
} 