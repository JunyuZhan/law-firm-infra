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
@Controller
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "springdoc.api-docs", name = "enabled", havingValue = "true", matchIfMissing = false)
public class ApiDocController {

    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerUiPath;

    @Value("${springdoc.api-docs.path:/v3/api-docs}")
    private String apiDocsPath;
    
    @Value("${springdoc.api-docs.enabled:true}")
    private boolean apiDocsEnabled;

    @Autowired
    private SpringDocConfigProperties springDocConfigProperties;

    /**
     * 重定向到Swagger UI
     */
    @Hidden
    @GetMapping("/api/docs")
    public RedirectView redirectToSwaggerUi() {
        return new RedirectView(swaggerUiPath);
    }

    /**
     * 文档入口点
     */
    @Hidden
    @GetMapping("/")
    public RedirectView index() {
        // 在开发环境中重定向到API文档
        return new RedirectView(swaggerUiPath);
    }

    /**
     * API文档健康检查
     * 用于确认API文档服务正常工作
     */
    @Hidden
    @GetMapping("/api-docs/health")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> apiDocsHealth() {
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("enabled", apiDocsEnabled);
        healthInfo.put("apiDocsPath", apiDocsPath);
        healthInfo.put("swaggerUiPath", swaggerUiPath);
        healthInfo.put("encoding", StandardCharsets.UTF_8.name());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-API-Doc-Encoding", StandardCharsets.UTF_8.name());
        
        return new ResponseEntity<>(healthInfo, headers, HttpStatus.OK);
    }

    /**
     * 字符编码测试端点
     * 用于测试API文档的编码问题
     */
    @Hidden
    @GetMapping(value = "/api-docs/encoding-test", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> encodingTest(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("测试中文", "这是一段测试文本");
        result.put("specialChars", "特殊字符: ÄÖÜäöüß");
        result.put("requestEncoding", request.getCharacterEncoding());
        result.put("systemEncoding", System.getProperty("file.encoding"));
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Content-Type-Options", "nosniff");
        
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }
} 