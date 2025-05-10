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
        // 排除已经是CommonResult类型的响应和全局异常处理返回
        boolean isCommonResult = returnType.getParameterType().isAssignableFrom(CommonResult.class);
        boolean isErrorController = returnType.getDeclaringClass().getName().contains("ErrorController");
        boolean isSpringDocController = returnType.getDeclaringClass().getName().contains("OpenApi");
        
        // 排除健康检查接口
        boolean isHealthController = returnType.getDeclaringClass().getName().contains("HealthCheckController");
        
        // 不处理已经是标准格式、错误控制器、API文档控制器和健康检查控制器的响应
        return !isCommonResult && !isErrorController && !isSpringDocController && !isHealthController;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
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