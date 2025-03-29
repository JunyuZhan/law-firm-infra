package com.lawfirm.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Swagger配置相关控制器
 * 用于处理Knife4j前端请求的swagger-config接口
 */
@RestController("swaggerConfigController")
@RequestMapping("/v3/api-docs")
public class SwaggerConfigController {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 提供swagger-config配置
     * 解决Knife4j前端请求swagger-config时404的问题
     */
    @GetMapping(value = "/swagger-config", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSwaggerConfig() {
        Map<String, Object> config = new HashMap<>();
        
        // 构建URLs列表
        List<Map<String, String>> urls = new ArrayList<>();
        Map<String, String> defaultUrl = new HashMap<>();
        defaultUrl.put("url", contextPath + "/v3/api-docs");
        defaultUrl.put("name", "默认分组");
        urls.add(defaultUrl);
        
        // 设置配置参数
        config.put("urls", urls);
        config.put("deepLinking", true);
        config.put("displayOperationId", false);
        config.put("defaultModelsExpandDepth", 1);
        config.put("defaultModelExpandDepth", 1);
        config.put("defaultModelRendering", "example");
        config.put("displayRequestDuration", false);
        config.put("docExpansion", "none");
        config.put("filter", false);
        config.put("showExtensions", false);
        config.put("showCommonExtensions", false);
        config.put("supportedSubmitMethods", new String[]{"get", "put", "post", "delete", "options", "head", "patch"});
        
        return config;
    }
} 