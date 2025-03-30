package com.lawfirm.api.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 静态资源过滤器
 * 处理所有静态资源请求
 */
@Slf4j
@Component
@Order(1) // 最高优先级
public class StaticResourceFilter implements Filter {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    private final PathMatcher pathMatcher = new AntPathMatcher();
    
    private final List<String> staticResourcePatterns = Arrays.asList(
        "/static/**", "/resources/**", "/public/**", "/assets/**",
        "/css/**", "/js/**", "/images/**", "/fonts/**",
        "/*.js", "/*.css", "/*.png", "/*.jpg", "/*.jpeg", "/*.gif", "/*.ico"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化静态资源过滤器，上下文路径: {}", contextPath);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        
        // 检查是否是静态资源请求
        if (isStaticResourceRequest(requestURI)) {
            log.debug("处理静态资源请求: {}", requestURI);
            response.setCharacterEncoding("UTF-8");
            
            // 设置适当的内容类型
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            setContentTypeByUri(requestURI, httpResponse);
        }
        
        // 继续过滤链
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 无需清理
    }
    
    /**
     * 判断是否为静态资源请求
     */
    private boolean isStaticResourceRequest(String uri) {
        if (uri == null) {
            return false;
        }
        
        // 处理带上下文路径的情况
        String path = uri;
        if (contextPath != null && !contextPath.equals("/") && uri.startsWith(contextPath)) {
            path = uri.substring(contextPath.length());
        }
        
        // 确保以/开头
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        
        // 检查是否匹配任一静态资源模式
        for (String pattern : staticResourcePatterns) {
            if (pathMatcher.match(pattern, path)) {
                log.debug("静态资源匹配: {} -> {}", path, pattern);
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 根据URI设置适当的内容类型
     */
    private void setContentTypeByUri(String uri, HttpServletResponse response) {
        if (uri.endsWith(".html") || uri.endsWith(".htm")) {
            response.setContentType("text/html;charset=UTF-8");
        } else if (uri.endsWith(".css")) {
            response.setContentType("text/css;charset=UTF-8");
        } else if (uri.endsWith(".js")) {
            response.setContentType("application/javascript;charset=UTF-8");
        } else if (uri.endsWith(".json")) {
            response.setContentType("application/json;charset=UTF-8");
        } else if (uri.endsWith(".png")) {
            response.setContentType("image/png");
        } else if (uri.endsWith(".jpg") || uri.endsWith(".jpeg")) {
            response.setContentType("image/jpeg");
        } else if (uri.endsWith(".gif")) {
            response.setContentType("image/gif");
        } else if (uri.endsWith(".ico")) {
            response.setContentType("image/x-icon");
        }
    }
} 