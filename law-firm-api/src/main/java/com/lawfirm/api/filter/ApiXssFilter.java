package com.lawfirm.api.filter;

import com.lawfirm.common.web.filter.XssFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * API层XSS防护过滤器，扩展自通用XSS过滤器
 */
@Component("apiXssFilter")
@Order(Ordered.HIGHEST_PRECEDENCE + 10) // 确保在通用XssFilter之后运行
public class ApiXssFilter extends XssFilter {

    private final List<String> excludeUrls;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 从配置文件中读取排除URL列表
     */
    public ApiXssFilter(@Value("${api.security.xss.exclude-urls:}") List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
        if (excludeUrls.isEmpty()) {
            // 默认排除路径
            this.excludeUrls.add("/api/v1/auth/login");
            this.excludeUrls.add("/api/v1/auth/register");
            this.excludeUrls.add("/api/v1/document/upload");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        
        // 排除不需要XSS过滤的URL
        if (shouldExclude(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // 调用父类的过滤方法
        super.doFilter(request, response, chain);
    }
    
    /**
     * 判断请求是否应该被排除XSS过滤
     */
    private boolean shouldExclude(String requestURI) {
        return excludeUrls.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }
} 