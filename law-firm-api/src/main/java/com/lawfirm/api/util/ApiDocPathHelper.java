package com.lawfirm.api.util;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * API文档路径工具类
 * <p>
 * 统一处理API文档路径判断逻辑，避免重复代码
 * </p>
 */
@Slf4j
@Component
public class ApiDocPathHelper {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    /**
     * API文档路径常量
     */
    private static final List<String> API_DOC_PATHS = Arrays.asList(
        "/doc.html", "/doc.html/**", "/doc/**",
        "/swagger-ui.html", "/swagger-ui/**", 
        "/v3/api-docs", "/v3/api-docs/**", "/v3/api-docs-ext/**",
        "/swagger-resources/**", "/swagger-resources",
        "/swagger-config/**", "/swagger-config",
        "/webjars/**", "/webjars",
        "/knife4j/**", "/knife4j",
        "/api-docs/**", "/api-docs",
        "/v2/api-docs/**", "/v2/api-docs",
        "/configuration/ui", "/configuration/security",
        "/favicon.ico", "/markdown/**"
    );
    
    /**
     * 判断URI是否为API文档路径
     * 
     * @param uri 请求URI
     * @return 如果是API文档路径则返回true
     */
    public boolean isApiDocPath(String uri) {
        if (uri == null) {
            return false;
        }
        
        // 移除上下文路径前缀（如果存在）
        String pathToCheck = uri;
        if (contextPath != null && !contextPath.isEmpty() && uri.startsWith(contextPath)) {
            pathToCheck = uri.substring(contextPath.length());
        }
        
        // 确保路径以/开头
        if (!pathToCheck.startsWith("/")) {
            pathToCheck = "/" + pathToCheck;
        }
        
        // 检查是否匹配API文档路径
        for (String docPath : API_DOC_PATHS) {
            if (pathMatcher.match(docPath, pathToCheck) || 
                pathToCheck.startsWith(docPath + "/") || 
                pathToCheck.startsWith(docPath)) {
                return true;
            }
        }
        
        // 关键字检查
        return pathToCheck.contains("/v3/api-docs") || 
               pathToCheck.contains("/swagger-ui") || 
               pathToCheck.contains("/openapi") ||
               pathToCheck.contains("/knife4j") ||
               pathToCheck.contains("/doc.html");
    }
    
    /**
     * 获取带上下文路径的API文档路径数组
     * 
     * @return 包含上下文路径前缀的API文档路径数组
     */
    public String[] getApiDocPathsWithContext() {
        final String pathPrefix;
        if (contextPath != null && !contextPath.equals("/")) {
            String prefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
            if (prefix.endsWith("/")) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
            pathPrefix = prefix;
        } else {
            pathPrefix = "";
        }
        
        return API_DOC_PATHS.stream()
            .map(path -> pathPrefix + path)
            .toArray(String[]::new);
    }
    
    /**
     * 获取原始API文档路径数组
     * 
     * @return API文档路径数组
     */
    public List<String> getApiDocPaths() {
        return API_DOC_PATHS;
    }
} 