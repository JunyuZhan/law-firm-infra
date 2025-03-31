package com.lawfirm.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * API文档专用控制器
 * <p>
 * 直接处理Knife4j和OpenAPI相关请求，确保返回有效的JSON数据格式
 * </p>
 */
@Slf4j
@RestController("ApiDocCustomController")
public class ApiDocCustomController {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 处理OpenAPI UI配置请求
     */
    @GetMapping("/swagger-resources/configuration/ui")
    public ResponseEntity<Map<String, Object>> handleSwaggerUiConfig() {
        log.info("处理Swagger UI配置请求");
        
        Map<String, Object> config = new HashMap<>();
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
        config.put("supportedSubmitMethods", new String[]{"get", "post", "put", "delete", "options"});
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(config);
    }

    /**
     * 处理OpenAPI安全配置请求
     */
    @GetMapping("/swagger-resources/configuration/security")
    public ResponseEntity<Map<String, Object>> handleSwaggerSecurityConfig() {
        log.info("处理Swagger安全配置请求");
        
        // 返回空的安全配置
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of());
    }
    
    /**
     * 处理文档列表请求
     */
    @GetMapping("/swagger-resources")
    public ResponseEntity<Object> handleSwaggerResources() {
        log.info("处理API文档资源列表请求");
        
        // 返回简单的文档列表
        String basePath = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        
        List<Map<String, Object>> resources = new ArrayList<>();
        
        Map<String, Object> systemResource = new HashMap<>();
        systemResource.put("name", "系统接口");
        systemResource.put("url", basePath + "/v3/api-docs/system");
        systemResource.put("swaggerVersion", "3.0");
        systemResource.put("location", basePath + "/v3/api-docs/system");
        
        Map<String, Object> businessResource = new HashMap<>();
        businessResource.put("name", "业务接口");
        businessResource.put("url", basePath + "/v3/api-docs/business");
        businessResource.put("swaggerVersion", "3.0");
        businessResource.put("location", basePath + "/v3/api-docs/business");
        
        Map<String, Object> authResource = new HashMap<>();
        authResource.put("name", "认证授权");
        authResource.put("url", basePath + "/v3/api-docs/认证授权");
        authResource.put("swaggerVersion", "3.0");
        authResource.put("location", basePath + "/v3/api-docs/认证授权");
        
        Map<String, Object> defaultResource = new HashMap<>();
        defaultResource.put("name", "律所管理系统API");
        defaultResource.put("url", basePath + "/v3/api-docs");
        defaultResource.put("swaggerVersion", "3.0");
        defaultResource.put("location", basePath + "/v3/api-docs");
        
        resources.add(systemResource);
        resources.add(businessResource);
        resources.add(authResource);
        resources.add(defaultResource);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resources);
    }
    
    /**
     * 处理API文档请求
     */
    @GetMapping("/v3/api-docs")
    public ResponseEntity<Map<String, Object>> handleApiDocs() {
        log.info("处理API文档主请求");
        
        // 返回符合OpenAPI 3.0规范的文档
        Map<String, Object> openapi = new HashMap<>();
        // 关键：指定openapi版本字段
        openapi.put("openapi", "3.0.1");
        
        // 设置info信息
        Map<String, Object> info = new HashMap<>();
        info.put("title", "律师事务所管理系统API");
        info.put("description", "律师事务所管理系统API文档");
        info.put("version", "1.0.0");
        info.put("termsOfService", "https://example.com/terms/");
        
        Map<String, Object> contact = new HashMap<>();
        contact.put("name", "API支持团队");
        contact.put("url", "https://example.com/contact/");
        contact.put("email", "support@example.com");
        info.put("contact", contact);
        
        Map<String, Object> license = new HashMap<>();
        license.put("name", "Apache 2.0");
        license.put("url", "https://www.apache.org/licenses/LICENSE-2.0.html");
        info.put("license", license);
        
        openapi.put("info", info);
        
        // 设置服务器
        List<Map<String, Object>> servers = new ArrayList<>();
        Map<String, Object> server = new HashMap<>();
        server.put("url", contextPath);
        server.put("description", "律所管理系统API服务器");
        servers.add(server);
        openapi.put("servers", servers);
        
        // 设置路径
        openapi.put("paths", new HashMap<>());
        
        // 设置组件
        Map<String, Object> components = new HashMap<>();
        components.put("schemas", new HashMap<>());
        components.put("securitySchemes", new HashMap<>());
        openapi.put("components", components);
        
        // 返回完整的OpenAPI文档
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(openapi);
    }
    
    /**
     * 处理分组API文档请求
     */
    @GetMapping("/v3/api-docs/{group}")
    public ResponseEntity<Map<String, Object>> handleGroupApiDocs(@PathVariable String group) {
        log.info("处理分组API文档请求: {}", group);
        
        // 返回符合OpenAPI 3.0规范的分组文档
        Map<String, Object> openapi = new HashMap<>();
        // 关键：指定openapi版本字段
        openapi.put("openapi", "3.0.1");
        
        // 设置info信息
        Map<String, Object> info = new HashMap<>();
        info.put("title", "律师事务所管理系统API - " + group);
        info.put("description", "律师事务所管理系统API文档 - " + group + "分组");
        info.put("version", "1.0.0");
        openapi.put("info", info);
        
        // 设置服务器
        List<Map<String, Object>> servers = new ArrayList<>();
        Map<String, Object> server = new HashMap<>();
        server.put("url", contextPath);
        server.put("description", "律所管理系统API服务器");
        servers.add(server);
        openapi.put("servers", servers);
        
        // 设置路径
        openapi.put("paths", new HashMap<>());
        
        // 设置组件
        Map<String, Object> components = new HashMap<>();
        components.put("schemas", new HashMap<>());
        components.put("securitySchemes", new HashMap<>());
        openapi.put("components", components);
        
        // 针对认证授权分组添加一些示例路径
        if ("认证授权".equals(group)) {
            Map<String, Object> paths = new HashMap<>();
            
            // 添加/login路径
            Map<String, Object> loginPath = new HashMap<>();
            Map<String, Object> postOp = new HashMap<>();
            postOp.put("summary", "用户登录");
            postOp.put("description", "使用用户名和密码进行登录认证");
            postOp.put("operationId", "login");
            postOp.put("tags", new String[]{"认证"});
            postOp.put("responses", Map.of(
                "200", Map.of(
                    "description", "登录成功",
                    "content", Map.of(
                        "application/json", Map.of(
                            "schema", Map.of(
                                "type", "object",
                                "properties", Map.of(
                                    "token", Map.of("type", "string"),
                                    "userId", Map.of("type", "string"),
                                    "username", Map.of("type", "string")
                                )
                            )
                        )
                    )
                )
            ));
            loginPath.put("post", postOp);
            paths.put("/auth/login", loginPath);
            
            openapi.put("paths", paths);
        }
        
        // 返回完整的OpenAPI文档
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(openapi);
    }
    
    /**
     * 处理API文档健康检查请求
     */
    @GetMapping("/v3/api-docs-ext")
    public ResponseEntity<Map<String, Object>> handleApiDocsExt() {
        log.info("处理API文档扩展信息请求");
        
        // 返回文档基本信息
        Map<String, Object> response = new HashMap<>();
        // 使用openapi字段而不是swagger
        response.put("openapi", "3.0.1");
        response.put("info", Map.of(
            "title", "律师事务所管理系统API",
            "version", "1.0.0",
            "description", "律师事务所管理系统API文档"
        ));
        response.put("basePath", contextPath);
        response.put("host", "localhost:8080");
        response.put("schemes", new String[]{"http"});
        response.put("consumes", new String[]{"application/json"});
        response.put("produces", new String[]{"application/json"});
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    
    /**
     * 处理分组信息请求
     */
    @GetMapping("/v3/api-docs/swagger-config")
    public ResponseEntity<Map<String, Object>> handleSwaggerConfig() {
        log.info("处理Swagger配置请求");
        
        String basePath = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        
        // 创建URL列表
        List<Map<String, Object>> urls = new ArrayList<>();
        urls.add(Map.of("name", "系统接口", "url", basePath + "/v3/api-docs/system"));
        urls.add(Map.of("name", "业务接口", "url", basePath + "/v3/api-docs/business"));
        urls.add(Map.of("name", "认证授权", "url", basePath + "/v3/api-docs/认证授权"));
        urls.add(Map.of("name", "律所管理系统API", "url", basePath + "/v3/api-docs"));
        
        // 返回配置
        Map<String, Object> config = new HashMap<>();
        config.put("configUrl", basePath + "/v3/api-docs/swagger-config");
        config.put("url", basePath + "/v3/api-docs");
        config.put("urls", urls);
        config.put("validatorUrl", "");
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(config);
    }
} 