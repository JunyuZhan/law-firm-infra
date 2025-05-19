package com.lawfirm.common.web.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * XSS过滤器
 */
@Component("commonXssFilter")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XssFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(XssFilter.class);

    // 需要排除的路径列表
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/health",
            "/doc.html",          // Knife4j UI主路径
            "/swagger-ui",        // Swagger UI资源
            "/swagger-resources",  // Swagger资源
            "/v3/api-docs",       // OpenAPI v3定义
            "/webjars",           // Webjar资源
            "/knife4j",           // Knife4j特定资源
            "/api-docs",          // 兼容路径
            "/*.html",            // HTML静态资源
            "/favicon.ico",       // 网站图标
            "/error"              // 错误页面
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        if (request == null) {
            log.error("XssFilter received a null ServletRequest object. This should not happen. Skipping XSS filtering and passing to next filter.");
            chain.doFilter(request, response); // 仍然尝试传递，但后续过滤器也可能失败
            return;
        }

        if (!(request instanceof HttpServletRequest)) {
            log.warn("XssFilter received a non-HttpServletRequest: {}. Skipping XSS filtering.", request.getClass().getName());
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        
        // 检查是否是需要排除的路径
        boolean shouldExclude = EXCLUDE_PATHS.stream()
                .anyMatch(path::startsWith);
        
        if (shouldExclude) {
            // 对于排除的路径，不做XSS过滤
            chain.doFilter(request, response);
        } else {
            // 对其他路径进行XSS过滤
            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(httpRequest);
            chain.doFilter(xssRequest, response);
        }
    }

    private static class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

        public XssHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values != null) {
                int length = values.length;
                String[] escapeValues = new String[length];
                for (int i = 0; i < length; i++) {
                    escapeValues[i] = cleanXss(values[i]);
                }
                return escapeValues;
            }
            return values;
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            return cleanXss(value);
        }

        @Override
        public String getHeader(String name) {
            String value = super.getHeader(name);
            return cleanXss(value);
        }

        private String cleanXss(String value) {
            if (StrUtil.isEmpty(value)) {
                return value;
            }
            // 使用更通用的HtmlUtil.escape替换HtmlUtil.filter，后者可能过于激进
            return HtmlUtil.escape(value); 
        }
    }
} 