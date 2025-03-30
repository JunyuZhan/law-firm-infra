package com.lawfirm.api.filter;

import com.lawfirm.common.web.filter.XssFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

/**
 * API层XSS防护过滤器，扩展自通用XSS过滤器
 */
@Slf4j
@Component("apiXssFilter")
@Order(Ordered.HIGHEST_PRECEDENCE + 10) // 确保在通用XssFilter之后运行
public class ApiXssFilter extends XssFilter {

    private final List<String> excludeUrls;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 从配置文件中读取排除URL列表
     */
    public ApiXssFilter(@Value("${api.security.xss.exclude-urls:}") List<String> excludeUrls,
                        @Value("${server.servlet.context-path:/api}") String contextPath) {
        this.excludeUrls = new ArrayList<>(excludeUrls);
        
        // 处理上下文路径前缀
        String pathPrefix = "";
        if (contextPath != null && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        
        // 默认排除路径
        addExcludeUrlIfNotExist(pathPrefix + "/v1/auth/login");
        addExcludeUrlIfNotExist(pathPrefix + "/v1/auth/register");
        addExcludeUrlIfNotExist(pathPrefix + "/v1/document/upload");
        addExcludeUrlIfNotExist(pathPrefix + "/v1/knowledge/content");
        
        // 添加默认的排除URL
        String pathPrefixDefault = StringUtils.hasText(contextPath) && !"/".equals(contextPath) ? contextPath : "";
        
        // 排除常见静态资源
        addExcludeUrlIfNotExist(pathPrefixDefault + "/static/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/resources/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/public/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/assets/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/css/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/js/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/images/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/fonts/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/favicon.ico");
        
        // 排除健康检查端点
        addExcludeUrlIfNotExist(pathPrefixDefault + "/actuator/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/health/**");
        
        // 排除文件上传和下载请求
        addExcludeUrlIfNotExist(pathPrefixDefault + "/files/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/file/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/download/**");
        addExcludeUrlIfNotExist(pathPrefixDefault + "/upload/**");
        
        log.info("XSS过滤器排除路径: {}", excludeUrls);
    }
    
    /**
     * 如果路径不存在，则添加到排除列表
     */
    private void addExcludeUrlIfNotExist(String url) {
        if (!excludeUrls.contains(url)) {
            excludeUrls.add(url);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        
        // 排除不需要XSS过滤的URL
        if (shouldExclude(httpRequest)) {
            log.debug("XSS过滤器排除路径: {}", requestURI);
            chain.doFilter(request, response);
            return;
        }

        // 调用父类的过滤方法
        super.doFilter(request, response, chain);
    }
    
    /**
     * 判断当前请求是否应该被排除XSS检查
     */
    private boolean shouldExclude(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        
        // 检查请求方法，GET请求不进行XSS过滤
        if ("GET".equals(request.getMethod())) {
            return true;
        }
        
        // 检查文件上传请求，不进行XSS过滤
        if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
            return true;
        }
        
        // 检查排除的URL
        for (String pattern : excludeUrls) {
            AntPathMatcher matcher = new AntPathMatcher();
            if (matcher.match(pattern, requestURI)) {
                log.debug("XSS过滤器排除路径: {}", requestURI);
                return true;
            }
        }
        
        return false;
    }
} 