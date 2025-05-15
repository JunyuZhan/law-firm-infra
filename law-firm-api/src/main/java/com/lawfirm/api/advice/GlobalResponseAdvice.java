package com.lawfirm.api.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.core.api.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应处理器
 * 确保所有API响应使用统一的格式
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 获取类名和包名进行检查
        String className = returnType.getContainingClass().getName();
        String packageName = returnType.getContainingClass().getPackageName();
        
        // 排除已经是CommonResult类型的响应和特定控制器
        boolean isCommonResult = CommonResult.class.isAssignableFrom(returnType.getParameterType());
        boolean isResponseEntityType = ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
        boolean isErrorController = className.contains("ErrorController");
        boolean isHealthController = className.contains("HealthCheckController");
        boolean isRestExceptionHandler = className.contains("RestExceptionHandler");
        
        // 不处理以下情况:
        // 1. 已经是标准CommonResult格式的响应 
        // 2. RestExceptionHandler处理的响应
        // 3. 错误控制器和健康检查控制器的响应
        return !isCommonResult && !isResponseEntityType && !isErrorController && 
               !isHealthController && !isRestExceptionHandler;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
        // 获取请求路径
        String path = request.getURI().getPath();
        
        // 静态资源和Actuator路径不包装
        if (path.startsWith("/webjars") || 
            path.startsWith("/static") || 
            path.startsWith("/assets") ||
            path.startsWith("/actuator")) {
            return body;
        }
        
        // HTML类型响应不包装
        if (MediaType.TEXT_HTML.equals(selectedContentType) ||
            (selectedContentType != null && selectedContentType.toString().contains("text/html"))) {
            log.debug("HTML类型请求，不包装响应: {}", request.getURI().getPath());
            return body;
        }
        
        // 如果已经是CommonResult或ResponseEntity<CommonResult>类型，则不再包装
        if (body instanceof CommonResult || 
            (body instanceof ResponseEntity && ((ResponseEntity<?>)body).getBody() instanceof CommonResult)) {
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