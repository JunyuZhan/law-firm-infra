package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

/**
 * API文档测试运行器
 * 在应用启动后测试API文档URL是否可访问
 */
@Slf4j
@Component
@Order(Integer.MAX_VALUE) // 最后执行
public class ApiDocTestRunner implements ApplicationRunner {

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String baseUrl = "http://localhost:" + serverPort;
        
        // 只测试关键路径
        String[] testPaths = {
            contextPath + "/knife4j/doc.html",
            contextPath + "/v3/api-docs",
            contextPath + "/v3/api-docs/swagger-config"
        };
        
        log.info("测试API文档URL可访问性...");
        
        boolean anySuccess = false;
        
        for (String path : testPaths) {
            try {
                // 使用URI创建URL，避免使用已弃用的URL构造函数
                URI uri = new URI(baseUrl + path);
                URL url = uri.toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000); // 更短的超时时间
                connection.setReadTimeout(1000);
                
                int responseCode = connection.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    log.info("✅ API文档URL可访问: {}", url);
                    anySuccess = true;
                } else {
                    log.warn("❌ API文档URL不可访问: {} - 状态码: {}", url, responseCode);
                }
                
                connection.disconnect();
            } catch (Exception e) {
                log.warn("❌ 测试API文档URL失败: {} - 错误: {}", baseUrl + path, e.getMessage());
            }
        }
        
        if (anySuccess) {
            log.info("API文档已成功部署，可通过 {}{}/ 访问", baseUrl, contextPath);
        } else {
            log.warn("API文档部署可能存在问题，请检查配置");
        }
    }
} 