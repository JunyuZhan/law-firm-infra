package com.lawfirm.common.web.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
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

    // 需要排除的路径列表
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/swagger-ui.html", 
            "/swagger-ui/", 
            "/v3/api-docs", 
            "/v3/api-docs/",
            "/webjars/",
            "/doc.html",
            "/health"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        
        // 检查是否是需要排除的路径
        boolean shouldExclude = EXCLUDE_PATHS.stream()
                .anyMatch(path::startsWith);
        
        if (shouldExclude) {
            // 对于Swagger相关的路径，不做XSS过滤
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
            return HtmlUtil.filter(value);
        }
    }
} 