package com.lawfirm.core.ai.provider;

import com.lawfirm.core.ai.exception.AIException;
import com.lawfirm.core.ai.utils.AIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Dify AI服务提供者
 * 支持本地部署的Dify服务
 */
@Slf4j
@Component
public class DifyAIProvider implements AIProvider {

    private final RestTemplate restTemplate;

    @Value("${lawfirm.ai.providers.dify.api-url:http://localhost:5001}")
    private String apiUrl;

    @Value("${lawfirm.ai.providers.dify.api-key:}")
    private String apiKey;

    @Value("${lawfirm.ai.providers.dify.timeout:30000}")
    private int timeout;

    public DifyAIProvider(@Qualifier("aiRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getName() {
        return "dify";
    }

    @Override
    public boolean initialize(Map<String, Object> config) {
        if (config != null) {
            if (config.containsKey("api-url")) {
                this.apiUrl = (String) config.get("api-url");
            }
            if (config.containsKey("api-key")) {
                this.apiKey = (String) config.get("api-key");
            }
            if (config.containsKey("timeout")) {
                this.timeout = Integer.parseInt(config.get("timeout").toString());
            }
        }
        
        log.info("Dify服务提供者初始化成功, API地址: {}", apiUrl);
        return true;
    }

    @Override
    public String sendTextRequest(String prompt, Map<String, Object> options) {
        try {
            log.info("向Dify发送文本请求, prompt长度: {}", prompt.length());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("inputs", new HashMap<>());
            requestBody.put("query", prompt);
            requestBody.put("response_mode", "blocking");
            
            // 合并额外参数
            if (options != null && options.containsKey("user")) {
                requestBody.put("user", options.get("user"));
            }
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            String completionUrl = apiUrl + "/v1/chat-messages";
            ResponseEntity<String> response = restTemplate.postForEntity(completionUrl, request, String.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> result = AIUtils.parseResult(response.getBody());
                
                // 提取Dify响应中的文本内容
                if (result.containsKey("answer")) {
                    return (String) result.get("answer");
                }
                return response.getBody();
            } else {
                throw new AIException("Dify API请求失败: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Dify文本请求异常", e);
            throw new AIException("Dify文本请求失败: " + e.getMessage(), e);
        }
    }

    @Override
    public String sendChatRequest(Map<String, Object>[] messages, Map<String, Object> options) {
        try {
            log.info("向Dify发送对话请求, 消息数量: {}", messages.length);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);
            
            // 获取最后一条消息作为当前查询
            Map<String, Object> lastMessage = messages[messages.length - 1];
            String query = lastMessage.containsKey("content") ? (String) lastMessage.get("content") : "";
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("inputs", new HashMap<>());
            requestBody.put("query", query);
            
            // 添加会话ID如果存在
            if (options != null && options.containsKey("conversation_id")) {
                requestBody.put("conversation_id", options.get("conversation_id"));
            }
            
            requestBody.put("response_mode", "blocking");
            
            // 合并额外参数
            if (options != null && options.containsKey("user")) {
                requestBody.put("user", options.get("user"));
            }
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            String chatUrl = apiUrl + "/v1/chat-messages";
            ResponseEntity<String> response = restTemplate.postForEntity(chatUrl, request, String.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> result = AIUtils.parseResult(response.getBody());
                
                // 提取Dify响应中的文本内容
                if (result.containsKey("answer")) {
                    return (String) result.get("answer");
                }
                return response.getBody();
            } else {
                throw new AIException("Dify API请求失败: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Dify对话请求异常", e);
            throw new AIException("Dify对话请求失败: " + e.getMessage(), e);
        }
    }

    @Override
    public float[] createEmbedding(String text) {
        log.warn("Dify提供者暂不支持直接创建嵌入向量，请使用其他提供者");
        throw new AIException("Dify提供者不支持创建嵌入向量");
    }

    @Override
    public String[] getAvailableModels() {
        // Dify通常是使用应用而不是直接暴露模型列表
        // 可以返回一个默认应用列表或者调用Dify API获取应用列表
        return new String[]{"dify-default"};
    }

    @Override
    public Map<String, Object> checkStatus() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            
            HttpEntity<String> request = new HttpEntity<>(headers);
            
            // 检查API状态，这里使用简单的应用信息API
            String statusUrl = apiUrl + "/v1/provider";
            ResponseEntity<String> response = restTemplate.getForEntity(statusUrl, String.class, request);
            
            Map<String, Object> statusInfo = new HashMap<>();
            statusInfo.put("status", response.getStatusCode().is2xxSuccessful() ? "available" : "unavailable");
            statusInfo.put("statusCode", response.getStatusCode().value());
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                statusInfo.put("details", AIUtils.parseResult(response.getBody()));
            }
            
            return statusInfo;
        } catch (Exception e) {
            log.error("检查Dify服务状态失败", e);
            Map<String, Object> errorStatus = new HashMap<>();
            errorStatus.put("status", "unavailable");
            errorStatus.put("error", e.getMessage());
            return errorStatus;
        }
    }

    @Override
    public void shutdown() {
        log.info("关闭Dify服务提供者");
        // Dify API客户端不需要特殊关闭操作
    }
} 