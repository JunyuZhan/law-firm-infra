package com.lawfirm.api.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.annotations.Hidden;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Swagger配置相关控制器
 * 提供符合Knife4j和OpenAPI规范的配置接口
 * 
 * 注意：此控制器已被禁用，改为使用SpringDoc的默认配置
 */
//@RestController("swaggerConfigController")
//@RequestMapping("/knife4j")
@Hidden // 在API文档中隐藏该控制器
@Profile("disabled") // 使用一个不存在的profile禁用此bean
public class SwaggerConfigController {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Autowired
    private OpenAPI openAPI;
    
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 在应用上下文刷新时更新OpenAPI服务器URL
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        updateServerUrl();
    }

    /**
     * 提供swagger-config配置
     * 解决Knife4j前端请求swagger-config时404的问题
     */
    @GetMapping(value = "/swagger-config", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSwaggerConfig() {
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
     * 转发到springdoc的API文档
     */
    @GetMapping("/v3/api-docs")
    public String redirectToApiDocs() {
        return "redirect:" + contextPath + "/v3/api-docs";
    }
    
    /**
     * 更新OpenAPI对象的服务器URL
     * 确保包含上下文路径
     */
    private void updateServerUrl() {
        if (openAPI.getServers() == null) {
            openAPI.setServers(new ArrayList<>());
        }
        
        // 检查是否已经有相同的服务器URL
        boolean hasContextPathServer = openAPI.getServers().stream()
                .anyMatch(server -> server.getUrl().equals(contextPath));
        
        // 如果没有，添加一个新的
        if (!hasContextPathServer) {
            Server server = new Server();
            server.setUrl(contextPath);
            server.setDescription("应用服务器");
            openAPI.getServers().add(server);
        }
    }
} 