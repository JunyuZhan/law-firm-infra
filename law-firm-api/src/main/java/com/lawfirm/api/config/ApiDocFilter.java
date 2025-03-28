package com.lawfirm.api.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * API文档诊断过滤器
 * 用于记录和诊断API文档相关请求
 */
@Component
@Order(1) // 确保在安全过滤器之前执行
@ConditionalOnProperty(name = "api.doc.filter.enabled", havingValue = "true", matchIfMissing = false)
public class ApiDocFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ApiDocFilter.class);
    
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        
        // 简单记录API文档相关请求
        if (isApiDocRequest(requestURI)) {
            log.info("API文档请求: {}", requestURI);
            
            // 关闭浏览器缓存，避免缓存问题
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setDateHeader("Expires", 0);
            
            if (requestURI.contains("/swagger-config")) {
                log.info("处理swagger-config请求");
            }
        }
        
        // 继续过滤链
        chain.doFilter(request, response);
    }
    
    /**
     * 判断是否为API文档相关请求
     */
    private boolean isApiDocRequest(String requestURI) {
        return requestURI.contains("/doc.html") || 
               requestURI.contains("/v3/api-docs") || 
               requestURI.contains("/swagger-resources") ||
               requestURI.contains("/swagger-ui") ||
               requestURI.contains("/webjars/");
    }
}