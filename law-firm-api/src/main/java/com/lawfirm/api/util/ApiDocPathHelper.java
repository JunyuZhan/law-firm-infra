package com.lawfirm.api.util;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import com.lawfirm.common.security.constants.SecurityConstants;

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
     * 判断URI是否为API文档路径
     * 
     * @param uri 请求URI
     * @return 如果是API文档路径则返回true
     */
    public boolean isApiDocPath(String uri) {
        if (uri == null) {
            return false;
        }

        // 规范化上下文路径，确保它以/开头（如果非空非根）且不以/结尾
        String normalizedContextPath = "";
        if (StringUtils.hasText(contextPath) && !contextPath.equals("/")) {
             normalizedContextPath = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
             if (normalizedContextPath.endsWith("/")) {
                 normalizedContextPath = normalizedContextPath.substring(0, normalizedContextPath.length() - 1);
             }
        }

        // 移除上下文路径前缀（如果存在）
        String pathToCheck = uri;
        if (!normalizedContextPath.isEmpty() && uri.startsWith(normalizedContextPath)) {
            pathToCheck = uri.substring(normalizedContextPath.length());
            // 如果移除后为空（即请求的是上下文根），则将其设为 "/"
            if (pathToCheck.isEmpty()) {
                 pathToCheck = "/";
            }
        }

        // 确保路径以/开头 for matching against patterns that start with /
        if (!pathToCheck.startsWith("/")) {
            pathToCheck = "/" + pathToCheck;
        }

        // Use SecurityConstants directly
        for (String docPathPattern : SecurityConstants.API_DOC_PATHS) { 
            if (pathMatcher.match(docPathPattern, pathToCheck)) {
                log.debug("Path '{}' (original URI '{}') matches API doc pattern '{}' from SecurityConstants", pathToCheck, uri, docPathPattern);
                return true;
            }
        }

        // 如果循环未匹配，则认为不是API文档路径
        log.trace("Path '{}' (original URI '{}') did not match any API doc pattern from SecurityConstants.", pathToCheck, uri);
        return false; 
    }
    
    /**
     * 获取带上下文路径的API文档路径数组
     * 
     * @return 包含上下文路径前缀的API文档路径数组
     */
    public String[] getApiDocPathsWithContext() {
        String pathPrefix = "";
        if (contextPath != null && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        
        final String finalPathPrefix = pathPrefix; // Need final variable for lambda
        // Use SecurityConstants here as well
        return Arrays.stream(SecurityConstants.API_DOC_PATHS)
                     .map(path -> finalPathPrefix + path)
                     .toArray(String[]::new);
    }
    
    /**
     * 获取原始API文档路径数组
     * 
     * @return API文档路径数组
     */
    public List<String> getApiDocPaths() {
        return Arrays.asList(SecurityConstants.API_DOC_PATHS);
    }
} 