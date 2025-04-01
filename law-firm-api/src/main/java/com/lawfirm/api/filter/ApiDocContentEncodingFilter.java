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

import com.lawfirm.api.util.ApiDocPathHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * API文档内容编码过滤器
 * <p>
 * 处理API文档响应中的编码问题
 * </p>
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
@RequiredArgsConstructor
public class ApiDocContentEncodingFilter implements Filter {

    private final ApiDocPathHelper apiDocPathHelper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        
        if (apiDocPathHelper.isApiDocPath(requestURI)) {
            log.debug("API文档请求 - 设置编码并跳过Base64检测: {}", requestURI);
            
            // 设置适当的字符集和内容类型
            httpResponse.setCharacterEncoding("UTF-8");
            
            // 对于API文档JSON响应，强制设置正确的内容类型
            if (requestURI.contains("/api-docs") || requestURI.contains("/swagger-config") || 
                requestURI.contains("/v3/api-docs")) {
                httpResponse.setContentType("application/json;charset=UTF-8");
                
                // 禁用压缩和编码
                httpResponse.setHeader("Content-Encoding", "identity");
            }
        }
        
        // 直接传递请求，不做任何Base64处理
        chain.doFilter(request, response);
    }
} 