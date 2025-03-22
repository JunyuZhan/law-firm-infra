package com.lawfirm.api.filter;

import com.lawfirm.common.web.filter.XssFilter;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * API层XSS防护过滤器，扩展自通用XSS过滤器
 */
@Component("apiXssFilter")
@Order(Ordered.HIGHEST_PRECEDENCE + 10) // 确保在通用XssFilter之后运行
public class ApiXssFilter extends XssFilter {

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
        "/api/v1/auth/login",
        "/api/v1/auth/register"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        
        // 排除不需要XSS过滤的URL
        if (EXCLUDE_URLS.stream().anyMatch(requestURI::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        // 调用父类的过滤方法
        super.doFilter(request, response, chain);
    }
} 