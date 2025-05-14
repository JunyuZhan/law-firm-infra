package com.lawfirm.api.constants;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * API文档路径常量类
 * 集中管理所有API文档相关路径，避免硬编码和重复定义
 * 
 * @author LawFirm Dev Team
 */
public class ApiDocPaths {
    
    /**
     * Swagger UI相关路径
     */
    public static final String[] SWAGGER_UI_PATHS = {
        "/swagger-ui.html", 
        "/swagger-ui/**"
    };
    
    /**
     * Knife4j UI相关路径
     */
    public static final String[] KNIFE4J_UI_PATHS = {
        "/doc.html"
    };
    
    /**
     * API文档数据路径
     */
    public static final String[] API_DOCS_PATHS = {
        "/v3/api-docs/**", 
        "/api-docs/**"
    };
    
    /**
     * API文档资源路径
     */
    public static final String[] RESOURCES_PATHS = {
        "/webjars/**", 
        "/swagger-resources/**",
        "/swagger-resources",
        "/swagger-config",
        "/swagger-config/**",
        "/swagger-uiConfiguration",
        "/swagger-resources/configuration/ui",
        "/swagger-resources/configuration/security"
    };
    
    /**
     * 自定义API文档访问路径
     */
    public static final String[] CUSTOM_DOC_PATHS = {
        "/api-docs/swagger",
        "/api-docs/ui"
    };
    
    /**
     * 所有API文档相关路径
     */
    public static final String[] ALL_DOC_PATHS = Stream.of(
        SWAGGER_UI_PATHS, 
        KNIFE4J_UI_PATHS, 
        API_DOCS_PATHS, 
        RESOURCES_PATHS,
        CUSTOM_DOC_PATHS
    ).flatMap(Arrays::stream).toArray(String[]::new);
} 