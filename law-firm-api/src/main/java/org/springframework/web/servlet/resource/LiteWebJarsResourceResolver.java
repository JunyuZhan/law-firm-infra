package org.springframework.web.servlet.resource;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import jakarta.servlet.http.HttpServletRequest;

/**
 * LiteWebJarsResourceResolver类的替代实现
 * 
 * 此类是用于兼容性目的，Spring Boot 3.2.3中已移除
 */
public class LiteWebJarsResourceResolver implements ResourceResolver {

    @Override
    @Nullable
    public Resource resolveResource(
            @Nullable HttpServletRequest request, String requestPath,
            List<? extends Resource> locations, ResourceResolverChain chain) {
        // 简单委托给链中的下一个解析器
        return chain.resolveResource(request, requestPath, locations);
    }

    @Override
    @Nullable
    public String resolveUrlPath(
            String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        // 简单委托给链中的下一个解析器
        return chain.resolveUrlPath(resourcePath, locations);
    }
} 