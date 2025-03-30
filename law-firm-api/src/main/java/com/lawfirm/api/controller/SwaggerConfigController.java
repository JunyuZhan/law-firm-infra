package com.lawfirm.api.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

/**
 * Swagger配置相关控制器
 * 提供符合Knife4j和OpenAPI规范的配置接口
 */
@RestController("swaggerConfigController")
@RequestMapping
public class SwaggerConfigController {
    
    private final Logger log = LoggerFactory.getLogger(SwaggerConfigController.class);

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Autowired
    private OpenAPI openAPI;
    
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 提供swagger-config配置
     * 解决Knife4j前端请求swagger-config时404的问题
     */
    @GetMapping(value = "/swagger-config", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSwaggerConfig() {
        log.info("处理swagger-config请求");
        Map<String, Object> config = new HashMap<>();
        
        // 构建URLs列表
        List<Map<String, String>> urls = new ArrayList<>();
        
        // 从Spring上下文中获取所有GroupedOpenApi对象
        Collection<GroupedOpenApi> apis = applicationContext.getBeansOfType(GroupedOpenApi.class).values();
        
        // 如果没有找到分组，添加默认分组
        if (apis.isEmpty()) {
            Map<String, String> defaultUrl = new HashMap<>();
            defaultUrl.put("url", contextPath + "/v3/api-docs");
            defaultUrl.put("name", "默认分组");
            urls.add(defaultUrl);
        } else {
            // 遍历所有API分组并添加
            for (GroupedOpenApi api : apis) {
                Map<String, String> groupUrl = new HashMap<>();
                String group = api.getGroup();
                groupUrl.put("url", contextPath + "/v3/api-docs" + (group == null ? "" : "?group=" + group));
                groupUrl.put("name", group == null ? "默认分组" : group);
                urls.add(groupUrl);
            }
        }
        
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
        config.put("supportedSubmitMethods", 
                   new String[]{"get", "put", "post", "delete", "options", "head", "patch"});
        
        return config;
    }
    
    /**
     * 处理可能为Base64编码的API文档响应
     */
    @GetMapping(value = "/custom-swagger-config", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> handleSwaggerConfig() {
        log.info("处理自定义swagger-config请求");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getSwaggerConfig());
    }
    
    /**
     * 处理可能为Base64编码的API文档子路径
     */
    @GetMapping(value = "/v3/api-docs/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> handleApiDocs(@RequestParam(value = "group", required = false) String group) {
        log.info("处理/v3/api-docs请求，group: {}", group);
        // 在这里，我们直接返回JSON数据，绕过可能的Base64编码逻辑
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(buildApiDocResponse(group));
    }
    
    /**
     * 构建API文档响应对象
     */
    private Object buildApiDocResponse(String group) {
        // 这里简化处理，直接返回一个包含基本信息的OpenAPI规范对象
        Map<String, Object> apiDoc = new HashMap<>();
        apiDoc.put("openapi", "3.0.1");
        apiDoc.put("info", Map.of(
            "title", "律师事务所管理系统API文档",
            "version", "1.0.0",
            "description", "提供律师事务所各模块的API接口"
        ));
        apiDoc.put("paths", new HashMap<>());
        apiDoc.put("components", new HashMap<>());
        apiDoc.put("tags", new ArrayList<>());
        
        // 如果有完整的OpenAPI对象，可以从中获取更多信息
        if (openAPI != null && openAPI.getInfo() != null) {
            Map<String, Object> info = new HashMap<>();
            Info openAPIInfo = openAPI.getInfo();
            info.put("title", openAPIInfo.getTitle());
            info.put("version", openAPIInfo.getVersion());
            info.put("description", openAPIInfo.getDescription());
            apiDoc.put("info", info);
        }
        
        return apiDoc;
    }
    
    /**
     * 检查内容是否为Base64编码，如果是则解码
     */
    private String decodeIfBase64(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        
        // 检查是否以"ey"开头（Base64编码的特征）
        if (content.startsWith("ey")) {
            try {
                byte[] decoded = Base64.getDecoder().decode(content);
                return new String(decoded, StandardCharsets.UTF_8);
            } catch (IllegalArgumentException e) {
                log.warn("Base64解码失败", e);
                return content;
            }
        }
        
        return content;
    }
} 