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
import java.io.File;

/**
 * 本地AI服务提供者实现
 */
@Component
public class LocalAIProvider implements AIProvider {
    
    private static final Logger logger = LoggerFactory.getLogger(LocalAIProvider.class);
    
    @Autowired
    private ModelConfig modelConfig;
    
    private String modelPath;
    private String modelType;
    private boolean enableGpu;
    private int threads;
    
    @PostConstruct
    public void init() {
        Map<String, Object> config = new HashMap<>();
        config.put("modelPath", modelConfig.getLocal().getModelPath());
        config.put("modelType", modelConfig.getLocal().getModelType());
        config.put("enableGpu", modelConfig.getLocal().getEnableGpu());
        config.put("threads", modelConfig.getLocal().getThreads());
        
        initialize(config);
    }
    
    @Override
    public String getName() {
        return "local";
    }
    
    @Override
    public boolean initialize(Map<String, Object> config) {
        try {
            this.modelPath = (String) config.get("modelPath");
            this.modelType = (String) config.get("modelType");
            this.enableGpu = config.get("enableGpu") != null ? (Boolean) config.get("enableGpu") : false;
            this.threads = config.get("threads") != null ? (Integer) config.get("threads") : 4;
            
            if (this.modelPath == null || this.modelPath.trim().isEmpty()) {
                logger.error("本地AI模型路径未配置");
                return false;
            }
            
            // 检查模型文件是否存在
            File modelFile = new File(this.modelPath);
            if (!modelFile.exists() || !modelFile.isFile()) {
                logger.error("本地AI模型文件不存在: {}", this.modelPath);
                return false;
            }
            
            // 初始化本地AI模型
            // 这里实际项目中应该加载本地模型
            logger.info("初始化本地AI服务提供者，模型类型：{}, GPU加速：{}", this.modelType, this.enableGpu);
            return true;
        } catch (Exception e) {
            logger.error("初始化本地AI提供者失败", e);
            return false;
        }
    }
    
    @Override
    public String sendTextRequest(String prompt, Map<String, Object> options) {
        try {
            logger.info("向本地AI模型发送文本请求");
            
            // 实际项目中调用本地AI模型
            // 这里仅作为示例
            simulateDelay();
            return "本地AI模型回复: " + prompt;
        } catch (Exception e) {
            logger.error("发送文本请求失败", e);
            throw new AIException("本地AI请求失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String sendChatRequest(Map<String, Object>[] messages, Map<String, Object> options) {
        try {
            logger.info("向本地AI模型发送聊天请求");
            
            // 实际项目中调用本地AI模型
            // 这里仅作为示例
            simulateDelay();
            return "本地AI模型聊天回复";
        } catch (Exception e) {
            logger.error("发送聊天请求失败", e);
            throw new AIException("本地AI聊天请求失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public float[] createEmbedding(String text) {
        try {
            logger.info("创建本地AI嵌入向量");
            
            // 实际项目中调用本地AI模型创建嵌入向量
            // 这里仅作为示例，返回固定大小的数组
            simulateDelay();
            return new float[384]; // 本地嵌入模型一般维度较小，这里假设为384维
        } catch (Exception e) {
            logger.error("创建嵌入向量失败", e);
            throw new AIException("本地AI创建嵌入向量失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String[] getAvailableModels() {
        try {
            // 本地只有一个模型
            return new String[]{this.modelType};
        } catch (Exception e) {
            logger.error("获取可用模型列表失败", e);
            throw new AIException("获取本地AI可用模型列表失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, Object> checkStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("name", getName());
        status.put("isInitialized", modelPath != null && !modelPath.isEmpty());
        status.put("modelType", modelType);
        status.put("enableGpu", enableGpu);
        status.put("threads", threads);
        return status;
    }
    
    @Override
    public void shutdown() {
        // 释放模型资源
        logger.info("关闭本地AI服务提供者");
    }
    
    // 模拟延迟，实际实现中移除此方法
    private void simulateDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(300); // 本地模型一般比云服务稍慢
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 