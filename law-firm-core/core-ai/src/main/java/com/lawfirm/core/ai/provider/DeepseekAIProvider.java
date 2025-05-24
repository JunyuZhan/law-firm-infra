package com.lawfirm.core.ai.provider;

import com.lawfirm.core.ai.config.ModelConfig;
import com.lawfirm.core.ai.exception.AIException;
import com.lawfirm.model.ai.entity.AIModelConfig;
import com.lawfirm.model.ai.service.AIModelConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Deepseek AI服务提供者实现
 */
@Slf4j
@Component("deepseekAIProvider")
public class DeepseekAIProvider implements AIProvider {
    @Autowired
    private ModelConfig modelConfig;
    @Autowired
    private AIModelConfigService aiModelConfigService;
    @Autowired
    @Qualifier("aiRestTemplate")
    private RestTemplate restTemplate;

    private String apiKey;
    private String endpoint;
    private String modelName;

    @PostConstruct
    public void init() {
        Map<String, Object> config = new HashMap<>();
        config.put("apiKey", modelConfig.getDeepseek() != null ? modelConfig.getDeepseek().getApiKey() : null);
        config.put("endpoint", modelConfig.getDeepseek() != null ? modelConfig.getDeepseek().getEndpoint() : null);
        config.put("modelName", modelConfig.getDeepseek() != null ? modelConfig.getDeepseek().getModelName() : null);
        initialize(config);
    }

    @Override
    public String getName() {
        return "deepseek";
    }

    @Override
    public boolean initialize(Map<String, Object> config) {
        try {
            this.apiKey = (String) config.get("apiKey");
            this.endpoint = (String) config.get("endpoint");
            this.modelName = (String) config.get("modelName");
            if (this.apiKey == null || this.apiKey.trim().isEmpty()) {
                log.error("Deepseek API密钥未配置");
                return false;
            }
            log.info("初始化Deepseek服务提供者，模型：{}", this.modelName);
            return true;
        } catch (Exception e) {
            log.error("初始化Deepseek提供者失败", e);
            return false;
        }
    }

    @Override
    public String sendTextRequest(String prompt, Map<String, Object> options) {
        loadLatestConfig();
        try {
            log.info("向Deepseek发送文本请求");
            String url = this.endpoint + "/v1/completions";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(this.apiKey);
            Map<String, Object> body = new HashMap<>();
            body.put("model", this.modelName);
            body.put("prompt", prompt);
            body.put("max_tokens", options != null && options.get("max_tokens") != null ? options.get("max_tokens") : 512);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object choicesObj = response.getBody().get("choices");
                if (choicesObj instanceof List<?> choices && !choices.isEmpty()) {
                    Object first = choices.get(0);
                    if (first instanceof Map<?, ?> firstMap && firstMap.get("text") != null) {
                        return String.valueOf(firstMap.get("text"));
                    }
                }
            }
            throw new AIException("Deepseek返回内容异常");
        } catch (Exception e) {
            log.error("发送文本请求失败", e);
            throw new AIException("Deepseek请求失败: " + e.getMessage(), e);
        }
    }

    @Override
    public String sendChatRequest(Map<String, Object>[] messages, Map<String, Object> options) {
        loadLatestConfig();
        try {
            log.info("向Deepseek发送聊天请求");
            String url = this.endpoint + "/v1/chat/completions";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(this.apiKey);
            Map<String, Object> body = new HashMap<>();
            body.put("model", this.modelName);
            body.put("messages", messages);
            body.put("max_tokens", options != null && options.get("max_tokens") != null ? options.get("max_tokens") : 512);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object choicesObj = response.getBody().get("choices");
                if (choicesObj instanceof List<?> choices && !choices.isEmpty()) {
                    Object first = choices.get(0);
                    if (first instanceof Map<?, ?> firstMap && firstMap.get("message") != null) {
                        Map<?, ?> messageMap = (Map<?, ?>) firstMap.get("message");
                        if (messageMap != null && messageMap.get("content") != null) {
                            return String.valueOf(messageMap.get("content"));
                        }
                    }
                }
            }
            throw new AIException("Deepseek聊天返回内容异常");
        } catch (Exception e) {
            log.error("发送聊天请求失败", e);
            throw new AIException("Deepseek聊天请求失败: " + e.getMessage(), e);
        }
    }

    @Override
    public float[] createEmbedding(String text) {
        loadLatestConfig();
        try {
            log.info("创建Deepseek嵌入向量");
            String url = this.endpoint + "/v1/embeddings";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(this.apiKey);
            Map<String, Object> body = new HashMap<>();
            body.put("model", this.modelName);
            body.put("input", text);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object dataObj = response.getBody().get("data");
                if (dataObj instanceof List<?> dataList && !dataList.isEmpty()) {
                    Object first = dataList.get(0);
                    if (first instanceof Map<?, ?> firstMap && firstMap.get("embedding") instanceof List<?> embeddingList) {
                        float[] arr = new float[embeddingList.size()];
                        for (int i = 0; i < embeddingList.size(); i++) {
                            arr[i] = ((Number) embeddingList.get(i)).floatValue();
                        }
                        return arr;
                    }
                }
            }
            throw new AIException("Deepseek嵌入向量返回内容异常");
        } catch (Exception e) {
            log.error("创建嵌入向量失败", e);
            throw new AIException("Deepseek创建嵌入向量失败: " + e.getMessage(), e);
        }
    }

    @Override
    public String[] getAvailableModels() {
        loadLatestConfig();
        try {
            log.info("获取Deepseek可用模型列表");
            String url = this.endpoint + "/v1/models";
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(this.apiKey);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object dataObj = response.getBody().get("data");
                if (dataObj instanceof List<?> dataList && !dataList.isEmpty()) {
                    String[] arr = new String[dataList.size()];
                    for (int i = 0; i < dataList.size(); i++) {
                        arr[i] = String.valueOf(dataList.get(i));
                    }
                    return arr;
                }
            }
            return new String[]{this.modelName};
        } catch (Exception e) {
            log.error("获取可用模型列表失败", e);
            throw new AIException("获取Deepseek可用模型列表失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> checkStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("name", getName());
        status.put("isInitialized", apiKey != null && !apiKey.isEmpty());
        status.put("modelName", modelName);
        return status;
    }

    @Override
    public void shutdown() {
        log.info("关闭Deepseek服务提供者");
    }

    private void simulateDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void loadLatestConfig() {
        AIModelConfig config = aiModelConfigService.getDefault();
        if (config != null && "deepseek".equalsIgnoreCase(config.getProvider())) {
            this.apiKey = config.getApiKey();
            this.endpoint = config.getEndpoint();
            this.modelName = config.getModelName();
        }
    }
} 