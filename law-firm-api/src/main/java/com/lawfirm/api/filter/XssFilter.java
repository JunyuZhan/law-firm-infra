package com.lawfirm.api.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * XSS防护过滤器
 */
@Component
public class XssFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
        "/api/v1/auth/login",
        "/api/v1/auth/register"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        
        // 排除不需要XSS过滤的URL
        if (EXCLUDE_URLS.stream().anyMatch(requestURI::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        // 包装请求,实现XSS过滤
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request);
        chain.doFilter(xssRequest, response);
    }

    /**
     * XSS请求包装器
     */
    private static class XssHttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {
        
        public XssHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            return value != null ? HtmlUtils.htmlEscape(value) : null;
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values != null) {
                return Arrays.stream(values)
                    .map(HtmlUtils::htmlEscape)
                    .toArray(String[]::new);
            }
            return null;
        }
    }
} 