package com.lawfirm.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

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
    
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 提供swagger-config配置
     * 解决Knife4j前端请求swagger-config时404的问题
     */
    @GetMapping(value = "/swagger-config", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public Map<String, Object> getSwaggerConfig() {
        Map<String, Object> config = new HashMap<>();
        
        // 构建URLs列表
        List<Map<String, String>> urls = new ArrayList<>();
        
        // 添加我们的特殊文档端点
        Map<String, String> defaultUrl = new HashMap<>();
        defaultUrl.put("url", contextPath + "/v3/api-docs/raw-json");
        defaultUrl.put("name", "默认分组");
        urls.add(defaultUrl);
        
        // 添加其他API分组（如果有的话）
        Map<String, String> clientsUrl = new HashMap<>();
        clientsUrl.put("url", contextPath + "/v3/api-docs/raw-json?group=客户管理");
        clientsUrl.put("name", "客户管理");
        urls.add(clientsUrl);
        
        Map<String, String> casesUrl = new HashMap<>();
        casesUrl.put("url", contextPath + "/v3/api-docs/raw-json?group=案件管理");
        casesUrl.put("name", "案件管理");
        urls.add(casesUrl);
        
        Map<String, String> contractsUrl = new HashMap<>();
        contractsUrl.put("url", contextPath + "/v3/api-docs/raw-json?group=合同管理");
        contractsUrl.put("name", "合同管理");
        urls.add(contractsUrl);
        
        // 添加更多模块的API分组
        Map<String, String> knowledgeUrl = new HashMap<>();
        knowledgeUrl.put("url", contextPath + "/v3/api-docs/raw-json?group=知识管理");
        knowledgeUrl.put("name", "知识管理");
        urls.add(knowledgeUrl);
        
        Map<String, String> documentUrl = new HashMap<>();
        documentUrl.put("url", contextPath + "/v3/api-docs/raw-json?group=文档管理");
        documentUrl.put("name", "文档管理");
        urls.add(documentUrl);
        
        Map<String, String> financeUrl = new HashMap<>();
        financeUrl.put("url", contextPath + "/v3/api-docs/raw-json?group=财务管理");
        financeUrl.put("name", "财务管理");
        urls.add(financeUrl);
        
        Map<String, String> personnelUrl = new HashMap<>();
        personnelUrl.put("url", contextPath + "/v3/api-docs/raw-json?group=人事管理");
        personnelUrl.put("name", "人事管理");
        urls.add(personnelUrl);
        
        Map<String, String> systemUrl = new HashMap<>();
        systemUrl.put("url", contextPath + "/v3/api-docs/raw-json?group=系统管理");
        systemUrl.put("name", "系统管理");
        urls.add(systemUrl);
        
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
    
    /**
     * 提供原始的OpenAPI文档JSON
     * 绕过所有响应处理器，直接返回规范化的JSON字符串
     */
    @GetMapping(value = "/raw-json", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<String> getRawOpenApiJson(String group) {
        // 用预先准备好的静态内容作为API文档
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"openapi\": \"3.0.1\",\n");
        json.append("  \"info\": {\n");
        json.append("    \"title\": \"律师事务所管理系统API\",\n");
        json.append("    \"description\": \"律师事务所管理系统API文档\",\n");
        json.append("    \"version\": \"1.0.0\"\n");
        json.append("  },\n");
        json.append("  \"servers\": [\n");
        json.append("    {\n");
        json.append("      \"url\": \"").append(contextPath).append("\",\n");
        json.append("      \"description\": \"默认服务器\"\n");
        json.append("    }\n");
        json.append("  ],\n");
        
        // 添加一些基本的路径和接口定义
        json.append("  \"paths\": {\n");
        
        if (group == null || "默认分组".equals(group) || "所有接口".equals(group)) {
            // 默认分组 - 登录相关接口
            json.append("    \"/api/login\": {\n");
            json.append("      \"post\": {\n");
            json.append("        \"tags\": [\"认证\"],\n");
            json.append("        \"summary\": \"用户登录\",\n");
            json.append("        \"requestBody\": {\n");
            json.append("          \"content\": {\n");
            json.append("            \"application/json\": {\n");
            json.append("              \"schema\": {\n");
            json.append("                \"type\": \"object\",\n");
            json.append("                \"properties\": {\n");
            json.append("                  \"username\": { \"type\": \"string\", \"example\": \"admin\" },\n");
            json.append("                  \"password\": { \"type\": \"string\", \"example\": \"123456\" }\n");
            json.append("                }\n");
            json.append("              }\n");
            json.append("            }\n");
            json.append("          }\n");
            json.append("        },\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"登录成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
            
            json.append("    \"/api/auth/login\": {\n");
            json.append("      \"post\": {\n");
            json.append("        \"tags\": [\"认证\"],\n");
            json.append("        \"summary\": \"用户登录(旧接口)\",\n");
            json.append("        \"requestBody\": {\n");
            json.append("          \"content\": {\n");
            json.append("            \"application/json\": {\n");
            json.append("              \"schema\": {\n");
            json.append("                \"type\": \"object\",\n");
            json.append("                \"properties\": {\n");
            json.append("                  \"username\": { \"type\": \"string\", \"example\": \"admin\" },\n");
            json.append("                  \"password\": { \"type\": \"string\", \"example\": \"123456\" }\n");
            json.append("                }\n");
            json.append("              }\n");
            json.append("            }\n");
            json.append("          }\n");
            json.append("        },\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"登录成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
        } else if ("客户管理".equals(group)) {
            // 客户管理接口
            json.append("    \"/api/client\": {\n");
            json.append("      \"get\": {\n");
            json.append("        \"tags\": [\"客户管理\"],\n");
            json.append("        \"summary\": \"查询客户列表\",\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"查询成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
        } else if ("案件管理".equals(group)) {
            // 案件管理接口
            json.append("    \"/api/cases\": {\n");
            json.append("      \"get\": {\n");
            json.append("        \"tags\": [\"案件管理\"],\n");
            json.append("        \"summary\": \"查询案件列表\",\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"查询成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
        } else if ("合同管理".equals(group)) {
            // 合同管理接口
            json.append("    \"/api/contract\": {\n");
            json.append("      \"get\": {\n");
            json.append("        \"tags\": [\"合同管理\"],\n");
            json.append("        \"summary\": \"查询合同列表\",\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"查询成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
        } else if ("知识管理".equals(group)) {
            // 知识管理接口
            json.append("    \"/api/knowledge\": {\n");
            json.append("      \"get\": {\n");
            json.append("        \"tags\": [\"知识管理\"],\n");
            json.append("        \"summary\": \"查询知识文档列表\",\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"查询成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
        } else if ("文档管理".equals(group)) {
            // 文档管理接口
            json.append("    \"/api/document\": {\n");
            json.append("      \"get\": {\n");
            json.append("        \"tags\": [\"文档管理\"],\n");
            json.append("        \"summary\": \"查询文档列表\",\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"查询成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
        } else if ("财务管理".equals(group)) {
            // 财务管理接口
            json.append("    \"/api/finance\": {\n");
            json.append("      \"get\": {\n");
            json.append("        \"tags\": [\"财务管理\"],\n");
            json.append("        \"summary\": \"查询财务数据\",\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"查询成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
        } else if ("人事管理".equals(group)) {
            // 人事管理接口
            json.append("    \"/api/personnel\": {\n");
            json.append("      \"get\": {\n");
            json.append("        \"tags\": [\"人事管理\"],\n");
            json.append("        \"summary\": \"查询人员列表\",\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"查询成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
        } else if ("系统管理".equals(group)) {
            // 系统管理接口
            json.append("    \"/api/system\": {\n");
            json.append("      \"get\": {\n");
            json.append("        \"tags\": [\"系统管理\"],\n");
            json.append("        \"summary\": \"查询系统配置\",\n");
            json.append("        \"responses\": {\n");
            json.append("          \"200\": {\n");
            json.append("            \"description\": \"查询成功\"\n");
            json.append("          }\n");
            json.append("        }\n");
            json.append("      }\n");
            json.append("    },\n");
        }
        
        // 移除最后一个逗号，并关闭paths对象
        if (json.charAt(json.length() - 2) == ',') {
            json.setLength(json.length() - 2);
            json.append("\n");
        }
        json.append("  }\n");
        json.append("}\n");
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .body(json.toString());
    }
} 