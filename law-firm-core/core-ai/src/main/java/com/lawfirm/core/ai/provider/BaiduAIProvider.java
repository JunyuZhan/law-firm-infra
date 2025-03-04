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
 * 百度AI服务提供者实现
 */
@Component
public class BaiduAIProvider implements AIProvider {
    
    private static final Logger logger = LoggerFactory.getLogger(BaiduAIProvider.class);
    
    @Autowired
    private ModelConfig modelConfig;
    
    private String apiKey;
    private String secretKey;
    private String baseUrl;
    private String defaultModel;
    
    @PostConstruct
    public void init() {
        Map<String, Object> config = new HashMap<>();
        config.put("apiKey", modelConfig.getBaidu().getApiKey());
        config.put("secretKey", modelConfig.getBaidu().getSecretKey());
        config.put("baseUrl", modelConfig.getBaidu().getBaseUrl());
        config.put("defaultModel", modelConfig.getBaidu().getDefaultModel());
        
        initialize(config);
    }
    
    @Override
    public String getName() {
        return "baidu";
    }
    
    @Override
    public boolean initialize(Map<String, Object> config) {
        try {
            this.apiKey = (String) config.get("apiKey");
            this.secretKey = (String) config.get("secretKey");
            this.baseUrl = (String) config.get("baseUrl");
            this.defaultModel = (String) config.get("defaultModel");
            
            if (this.apiKey == null || this.apiKey.trim().isEmpty() || 
                this.secretKey == null || this.secretKey.trim().isEmpty()) {
                logger.error("百度AI API密钥或密钥ID未配置");
                return false;
            }
            
            // 初始化百度AI客户端
            // 这里实际项目中应该使用百度AI的Java客户端库进行初始化
            logger.info("初始化百度AI服务提供者，模型：{}", this.defaultModel);
            return true;
        } catch (Exception e) {
            logger.error("初始化百度AI提供者失败", e);
            return false;
        }
    }
    
    @Override
    public String sendTextRequest(String prompt, Map<String, Object> options) {
        try {
            logger.info("向百度AI发送文本请求");
            
            String model = options != null && options.containsKey("model") 
                    ? (String) options.get("model") 
                    : this.defaultModel;
            
            // 实际项目中调用百度AI API
            // 这里仅作为示例，实际需要使用SDK进行调用
            simulateDelay();
            return "百度AI回复: " + prompt;
        } catch (Exception e) {
            logger.error("发送文本请求失败", e);
            throw new AIException("百度AI请求失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String sendChatRequest(Map<String, Object>[] messages, Map<String, Object> options) {
        try {
            logger.info("向百度AI发送聊天请求");
            
            String model = options != null && options.containsKey("model") 
                    ? (String) options.get("model") 
                    : this.defaultModel;
            
            // 实际项目中调用百度AI API
            // 这里仅作为示例，实际需要使用SDK进行调用
            simulateDelay();
            return "百度AI聊天回复";
        } catch (Exception e) {
            logger.error("发送聊天请求失败", e);
            throw new AIException("百度AI聊天请求失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public float[] createEmbedding(String text) {
        try {
            logger.info("创建百度AI嵌入向量");
            
            // 实际项目中调用百度AI API创建嵌入向量
            // 这里仅作为示例，返回固定大小的数组
            simulateDelay();
            return new float[768]; // 文心大模型的嵌入向量一般为768维
        } catch (Exception e) {
            logger.error("创建嵌入向量失败", e);
            throw new AIException("百度AI创建嵌入向量失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String[] getAvailableModels() {
        try {
            // 实际项目中应调用百度AI API获取可用模型列表
            return new String[]{"ERNIE-Bot-4", "ERNIE-Bot-turbo", "ERNIE-Bot", "ERNIE-Speed"};
        } catch (Exception e) {
            logger.error("获取可用模型列表失败", e);
            throw new AIException("获取百度AI可用模型列表失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, Object> checkStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("name", getName());
        status.put("isInitialized", apiKey != null && !apiKey.isEmpty() && secretKey != null && !secretKey.isEmpty());
        status.put("defaultModel", defaultModel);
        return status;
    }
    
    @Override
    public void shutdown() {
        // 关闭资源
        logger.info("关闭百度AI服务提供者");
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