package com.lawfirm.api.controller;

import com.lawfirm.common.core.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * API文档诊断控制器
 * 用于检查API文档配置问题
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ApiDocDiagnosticController {

    @Autowired
    private Environment environment;

    /**
     * API文档配置诊断
     * 用于检查API文档配置是否正确
     */
    @GetMapping("/doc-config")
    public CommonResult<Map<String, Object>> getApiDocConfig() {
        Map<String, Object> config = new HashMap<>();
        
        // SpringDoc配置
        config.put("springdoc.api-docs.enabled", environment.getProperty("springdoc.api-docs.enabled", "未配置"));
        config.put("springdoc.swagger-ui.enabled", environment.getProperty("springdoc.swagger-ui.enabled", "未配置"));
        config.put("springdoc.api-docs.path", environment.getProperty("springdoc.api-docs.path", "未配置"));
        config.put("springdoc.api-docs.json-base64-encoded", environment.getProperty("springdoc.api-docs.json-base64-encoded", "未配置"));
        
        // Knife4j配置
        config.put("knife4j.enable", environment.getProperty("knife4j.enable", "未配置"));
        config.put("knife4j.production", environment.getProperty("knife4j.production", "未配置"));
        
        // 系统属性
        config.put("spring.mvc.pathmatch.matching-strategy", environment.getProperty("spring.mvc.pathmatch.matching-strategy", "未配置"));
        
        // 当前环境
        config.put("spring.profiles.active", environment.getProperty("spring.profiles.active", "未配置"));
        
        // 添加API文档URL
        String serverPort = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "/");
        if (!contextPath.startsWith("/")) {
            contextPath = "/" + contextPath;
        }
        if (contextPath.equals("/")) {
            contextPath = "";
        }
        
        config.put("api-docs-url", "http://localhost:" + serverPort + contextPath + "/v3/api-docs");
        config.put("knife4j-url", "http://localhost:" + serverPort + contextPath + "/doc.html");
        
        return CommonResult.success(config, "API文档配置诊断");
    }
    
    /**
     * 简单的健康检查端点
     */
    @GetMapping("/doc-health")
    public CommonResult<String> health() {
        return CommonResult.success("API文档服务正常运行中");
    }
} 