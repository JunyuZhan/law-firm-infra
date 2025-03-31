package com.lawfirm.api.advice;

import com.lawfirm.api.VbenResult;
import com.lawfirm.api.util.ApiDocPathHelper;
import com.lawfirm.common.core.api.CommonResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 统一响应格式转换器
 * 将所有响应转换为前端期望的格式
 * 
 * 与ApiDocConfiguration配合，排除API文档路径
 */
@RestControllerAdvice(basePackages = {"com.lawfirm.api.controller"})
public class VbenResponseAdvice implements ResponseBodyAdvice<Object> {
    
    private static final Logger log = LoggerFactory.getLogger(VbenResponseAdvice.class);

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Autowired
    private ApiDocPathHelper apiDocPathHelper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // API文档路径完全不处理
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String path = request.getRequestURI();
            
            // 记录请求路径，方便调试
            log.debug("VbenResponseAdvice检查请求: {}", path);
            
            // API文档路径直接排除
            if (apiDocPathHelper.isApiDocPath(path)) {
                log.debug("排除API文档路径: {}", path);
                return false;
            }
        }
        
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 直接检查路径
        String path = request.getURI().getPath();
        
        // API文档路径直接返回原始响应
        if (apiDocPathHelper.isApiDocPath(path)) {
            log.debug("放行API文档响应: {}", path);
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