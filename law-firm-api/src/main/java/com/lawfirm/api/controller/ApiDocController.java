package com.lawfirm.api.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * API文档控制器
 * 提供API文档入口点和辅助功能
 */
@Slf4j
@Hidden
@Controller
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "springdoc.api-docs", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ApiDocController {

    @Value("${springdoc.api-docs.path:/v3/api-docs}")
    private String apiDocsPath;

    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerUiPath;

    @Autowired(required = false)
    private SpringDocConfigProperties springDocConfigProperties;

    /**
     * 重定向到Swagger UI首页
     */
    @GetMapping(value = "/api-docs", produces = MediaType.TEXT_HTML_VALUE)
    public RedirectView apiDocs() {
        log.debug("重定向到Swagger UI");
        return new RedirectView("/swagger-ui.html");
    }

    /**
     * 用于检查API文档服务状态
     */
    @GetMapping("/api-docs/health")
    public ResponseEntity<Map<String, Object>> apiDocHealth() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("encoding", StandardCharsets.UTF_8.name());
        result.put("message", "API文档服务运行正常");
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    /**
     * 重定向到API文档根目录
     */
    @RequestMapping(value = {"/swagger", "/docs"})
    public RedirectView redirectToSwagger() {
        return new RedirectView("/swagger-ui.html");
    }

    /**
     * API文档清除缓存 - 解决文档不更新的问题
     */
    @GetMapping("/v3/api-docs/clear-cache")
    @ResponseBody
    @Hidden
    public ResponseEntity<Map<String, String>> clearApiDocsCache() {
        Map<String, String> result = new HashMap<>();
        
        try {
            if (springDocConfigProperties != null) {
                // 使用反射调用setCacheDisabled方法以兼容不同版本的SpringDoc
                try {
                    Method method = springDocConfigProperties.getClass().getMethod("setCacheDisabled", boolean.class);
                    method.invoke(springDocConfigProperties, true);
                    log.info("API文档缓存已清空");
                    result.put("status", "success");
                    result.put("message", "API文档缓存已清空");
                } catch (NoSuchMethodException e) {
                    log.warn("SpringDocConfigProperties不支持setCacheDisabled方法，尝试其他方式清除缓存");
                    // 尝试通过设置系统属性的方式
                    System.setProperty("springdoc.cache.disabled", "true");
                    result.put("status", "partial");
                    result.put("message", "通过系统属性方式尝试清除缓存");
                }
            } else {
                log.warn("SpringDocConfigProperties不可用，无法清空缓存");
                result.put("status", "warning");
                result.put("message", "SpringDocConfigProperties不可用，无法清空缓存");
            }
        } catch (Exception e) {
            log.error("清空API文档缓存失败", e);
            result.put("status", "error");
            result.put("message", "清空API文档缓存失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        
        return ResponseEntity.ok(result);
    }
} 