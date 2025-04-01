package com.lawfirm.api.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.lawfirm.api.util.ApiDocPathHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * API文档内容过滤器
 * <p>
 * 专门处理API文档相关的请求响应，确保内容不被压缩或错误编码
 * </p>
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 5) // 比安全过滤器稍低的优先级
@RequiredArgsConstructor
public class ApiDocContentFilter implements Filter {

    private final ApiDocPathHelper apiDocPathHelper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        
        // 检查是否是API文档路径
        if (apiDocPathHelper.isApiDocPath(requestURI)) {
            log.debug("处理API文档请求: {}", requestURI);
            
            // 设置适当的字符集和内容类型
            httpResponse.setCharacterEncoding("UTF-8");
            
            // 禁用响应压缩
            httpResponse.setHeader("Content-Encoding", "identity");
            
            // 对于API文档JSON响应，强制设置正确的内容类型
            if (requestURI.contains("/api-docs") || requestURI.contains("/swagger-config") || 
                requestURI.contains("/v3/api-docs")) {
                httpResponse.setContentType("application/json;charset=UTF-8");
                log.debug("强制设置API文档JSON响应内容类型: {}", requestURI);
            }
            
            // 包装响应以便于可能需要修改内容
            ContentCachingResponseWrapper responseWrapper = 
                new ContentCachingResponseWrapper(httpResponse);
            
            try {
                // 继续过滤器链
                chain.doFilter(request, responseWrapper);
                
                // 如果是JSON响应，检查内容
                String contentType = responseWrapper.getContentType();
                if (contentType != null && contentType.contains("application/json")) {
                    byte[] content = responseWrapper.getContentAsByteArray();
                    
                    if (content.length > 0) {
                        // 打印前10个字符用于调试
                        String preview = new String(content, 0, Math.min(20, content.length), "UTF-8");
                        log.debug("API文档JSON响应内容预览: {} ...", preview);
                        
                        // 检查内容是否以预期的JSON开头
                        if (!preview.startsWith("{") && !preview.startsWith("[")) {
                            log.warn("API文档响应不是有效的JSON格式: {} ...", preview);
                        }
                    }
                }
                
                // 提交响应
                responseWrapper.copyBodyToResponse();
            } catch (Exception e) {
                log.error("处理API文档响应时发生错误", e);
                throw e;
            }
        } else {
            // 非API文档路径，正常处理
            chain.doFilter(request, response);
        }
    }
} 