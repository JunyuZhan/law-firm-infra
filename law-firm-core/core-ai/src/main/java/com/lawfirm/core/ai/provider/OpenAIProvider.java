package com.lawfirm.core.ai.provider;

import com.lawfirm.core.ai.config.ModelConfig;
import com.lawfirm.core.ai.exception.AIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OpenAI服务提供者实现
 */
@Component
public class OpenAIProvider implements AIProvider {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAIProvider.class);
    
    @Autowired
    private ModelConfig modelConfig;
    
    private String apiKey;
    private String baseUrl;
    private String defaultModel;
    private String proxy;
    private String organizationId;
    
    @PostConstruct
    public void init() {
        Map<String, Object> config = new HashMap<>();
        config.put("apiKey", modelConfig.getOpenai().getApiKey());
        config.put("baseUrl", modelConfig.getOpenai().getBaseUrl());
        config.put("defaultModel", modelConfig.getOpenai().getDefaultModel());
        config.put("proxy", modelConfig.getOpenai().getProxy());
        config.put("organizationId", modelConfig.getOpenai().getOrganizationId());
        
        initialize(config);
    }
    
    @Override
    public String getName() {
        return "openai";
    }
    
    @Override
    public boolean initialize(Map<String, Object> config) {
        try {
            this.apiKey = (String) config.get("apiKey");
            this.baseUrl = (String) config.get("baseUrl");
            this.defaultModel = (String) config.get("defaultModel");
            this.proxy = (String) config.get("proxy");
            this.organizationId = (String) config.get("organizationId");
            
            if (this.apiKey == null || this.apiKey.trim().isEmpty()) {
                logger.error("OpenAI API密钥未配置");
                return false;
            }
            
            // 初始化OpenAI客户端
            // 这里实际项目中应该使用OpenAI的Java客户端库进行初始化
            logger.info("初始化OpenAI服务提供者，模型：{}", this.defaultModel);
            return true;
        } catch (Exception e) {
            logger.error("初始化OpenAI提供者失败", e);
            return false;
        }
    }
    
    @Override
    public String sendTextRequest(String prompt, Map<String, Object> options) {
        try {
            logger.info("向OpenAI发送文本请求");
            
            String model = options != null && options.containsKey("model") 
                    ? (String) options.get("model") 
                    : this.defaultModel;
            
            // 实际项目中调用OpenAI API
            // 这里仅作为示例，实际需要使用SDK进行调用
            simulateDelay();
            return "OpenAI回复: " + prompt;
        } catch (Exception e) {
            logger.error("发送文本请求失败", e);
            throw new AIException("OpenAI请求失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String sendChatRequest(Map<String, Object>[] messages, Map<String, Object> options) {
        try {
            logger.info("向OpenAI发送聊天请求");
            
            String model = options != null && options.containsKey("model") 
                    ? (String) options.get("model") 
                    : this.defaultModel;
            
            // 实际项目中调用OpenAI API
            // 这里仅作为示例，实际需要使用SDK进行调用
            simulateDelay();
            return "OpenAI聊天回复";
        } catch (Exception e) {
            logger.error("发送聊天请求失败", e);
            throw new AIException("OpenAI聊天请求失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public float[] createEmbedding(String text) {
        try {
            logger.info("创建OpenAI嵌入向量");
            
            // 实际项目中调用OpenAI API创建嵌入向量
            // 这里仅作为示例，返回固定大小的数组
            simulateDelay();
            return new float[1536]; // OpenAI的嵌入向量一般为1536维
        } catch (Exception e) {
            logger.error("创建嵌入向量失败", e);
            throw new AIException("OpenAI创建嵌入向量失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String[] getAvailableModels() {
        try {
            // 实际项目中应调用OpenAI API获取可用模型列表
            return new String[]{"gpt-4", "gpt-4-turbo", "gpt-3.5-turbo", "text-embedding-ada-002"};
        } catch (Exception e) {
            logger.error("获取可用模型列表失败", e);
            throw new AIException("获取OpenAI可用模型列表失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, Object> checkStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("name", getName());
        status.put("isInitialized", apiKey != null && !apiKey.isEmpty());
        status.put("defaultModel", defaultModel);
        return status;
    }
    
    @Override
    public void shutdown() {
        // 关闭资源
        logger.info("关闭OpenAI服务提供者");
    }
    
    // 模拟延迟，实际实现中移除此方法
    private void simulateDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 