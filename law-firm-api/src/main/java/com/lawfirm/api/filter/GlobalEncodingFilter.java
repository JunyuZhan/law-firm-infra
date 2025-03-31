package com.lawfirm.api.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingResponseWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 全局字符编码过滤器
 * <p>
 * 确保所有请求和响应都使用UTF-8编码，解决中文显示问题
 * </p>
 */
@Slf4j
@Component
@Order(0)  // 最高优先级
public class GlobalEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化全局字符编码过滤器");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // 设置请求编码
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        }
        
        // 设置响应编码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        
        String uri = httpRequest.getRequestURI();
        // 针对API文档请求进行特殊处理
        if (uri.contains("/v3/api-docs") || uri.contains("/swagger-resources") || uri.contains("/knife4j")) {
            // 记录请求信息用于调试
            if (log.isDebugEnabled()) {
                log.debug("处理API文档请求: {}, 方法: {}, 内容类型: {}", 
                          uri, httpRequest.getMethod(), httpRequest.getContentType());
            }
            
            // 设置内容类型为JSON
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            
            // 添加跨域头
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", "*");
            
            // 添加缓存控制
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setHeader("Expires", "0");
            
            // 对OPTIONS请求直接返回成功，不继续过滤链
            if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            
            // 对于真正的API文档请求，使用响应包装器
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);
            
            try {
                // 执行过滤链
                chain.doFilter(request, responseWrapper);
                
                // 检查响应状态
                if (responseWrapper.getStatus() >= 400) {
                    log.warn("API文档请求响应错误状态码: {}", responseWrapper.getStatus());
                    // 如果是错误状态，重写为有效的JSON
                    responseWrapper.resetBuffer();
                    responseWrapper.setStatus(HttpServletResponse.SC_OK);
                    
                    PrintWriter writer = responseWrapper.getWriter();
                    writer.write("{\"message\":\"API文档请求处理错误\",\"status\":\"error\"}");
                    writer.flush();
                } else {
                    // 正常情况下，复制内容回原始响应
                    byte[] content = responseWrapper.getContentAsByteArray();
                    
                    // 确保内容是有效的JSON
                    if (content.length == 0) {
                        // 如果内容为空，返回空JSON对象
                        responseWrapper.resetBuffer();
                        PrintWriter writer = responseWrapper.getWriter();
                        writer.write("{}");
                        writer.flush();
                    } else if (!isValidJson(new String(content, StandardCharsets.UTF_8))) {
                        // 如果内容不是有效JSON，重写为有效的JSON
                        log.warn("API文档返回的内容不是有效JSON，将替换为空对象");
                        responseWrapper.resetBuffer();
                        PrintWriter writer = responseWrapper.getWriter();
                        writer.write("{}");
                        writer.flush();
                    }
                }
                
                // 输出内容
                responseWrapper.copyBodyToResponse();
                
            } catch (Exception e) {
                log.error("处理API文档请求时发生错误", e);
                // 重置响应
                httpResponse.reset();
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                httpResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
                
                // 返回错误信息
                PrintWriter writer = httpResponse.getWriter();
                writer.write("{\"message\":\"处理API文档请求时发生错误\",\"status\":\"error\"}");
                writer.flush();
            }
            
            return;
        }
        
        // 继续执行过滤链（非API文档请求）
        chain.doFilter(request, response);
    }
    
    /**
     * 检查字符串是否为有效的JSON
     */
    private boolean isValidJson(String json) {
        if (json == null || json.isEmpty()) {
            return false;
        }
        
        // 简单检查JSON格式
        json = json.trim();
        return (json.startsWith("{") && json.endsWith("}")) || 
               (json.startsWith("[") && json.endsWith("]"));
    }

    @Override
    public void destroy() {
        // 无需特殊清理
    }
} 