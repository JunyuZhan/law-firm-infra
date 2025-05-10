package com.lawfirm.core.ai.service.impl;

import com.lawfirm.core.ai.provider.AIProvider;
import com.lawfirm.core.ai.provider.AIProviderFactory;
import com.lawfirm.core.ai.config.AIConfig;
import com.lawfirm.core.ai.config.ModelConfig;
import com.lawfirm.core.ai.exception.AIException;
import com.lawfirm.model.ai.service.AIModelService;
import com.lawfirm.model.ai.enums.ModelStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * AI模型服务实现类
 */
@Service
@RequiredArgsConstructor
public class AIModelServiceImpl implements AIModelService {
    
    private static final Logger logger = LoggerFactory.getLogger(AIModelServiceImpl.class);
    
    private final AIProviderFactory providerFactory;
    private final AIConfig aiConfig;
    private final ModelConfig modelConfig;
    
    // 当前使用的模型ID
    private String currentModelId;
    
    // 模型状态缓存
    private final Map<String, String> modelStatusCache = new ConcurrentHashMap<>();
    
    // 反馈ID计数器
    private final AtomicLong feedbackIdCounter = new AtomicLong(1);
    
    @Override
    public String getDefaultModel() {
        logger.info("获取默认AI模型");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            String providerName = provider.getName();
            
            String modelId;
            switch (providerName) {
                case "openai":
                    modelId = modelConfig.getOpenai().getDefaultModel();
                    break;
                case "baidu":
                    modelId = modelConfig.getBaidu().getDefaultModel();
                    break;
                case "local":
                    modelId = modelConfig.getLocal().getModelType();
                    break;
                default:
                    // 默认返回第一个可用模型
                    String[] models = provider.getAvailableModels();
                    modelId = models.length > 0 ? models[0] : "unknown";
            }
            
            // 初始化当前模型ID（如果未设置）
            if (currentModelId == null) {
                currentModelId = modelId;
            }
            
            return modelId;
        } catch (Exception e) {
            logger.error("获取默认AI模型失败", e);
            throw new AIException("获取默认AI模型失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean switchModel(String modelId) {
        logger.info("切换AI模型: {}", modelId);
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 检查模型是否可用
            String[] availableModels = provider.getAvailableModels();
            boolean modelExists = Arrays.asList(availableModels).contains(modelId);
            
            if (!modelExists) {
                logger.warn("模型不存在或不可用: {}", modelId);
                return false;
            }
            
            // 更新当前模型ID
            currentModelId = modelId;
            logger.info("成功切换到模型: {}", modelId);
            
            return true;
        } catch (Exception e) {
            logger.error("切换AI模型失败", e);
            throw new AIException("切换AI模型失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String getModelStatus(String modelId) {
        logger.info("获取模型状态: {}", modelId);
        
        try {
            // 先检查缓存
            if (modelStatusCache.containsKey(modelId)) {
                return modelStatusCache.get(modelId);
            }
            
            // 模拟获取模型状态逻辑
            // 实际项目中应该从模型服务或数据库获取
            String status;
            
            AIProvider provider = providerFactory.getDefaultProvider();
            String[] availableModels = provider.getAvailableModels();
            boolean modelExists = Arrays.asList(availableModels).contains(modelId);
            
            if (modelExists) {
                status = ModelStatus.DEPLOYED.name();
            } else {
                status = ModelStatus.FAILED.name();
            }
            
            // 更新缓存
            modelStatusCache.put(modelId, status);
            
            return status;
        } catch (Exception e) {
            logger.error("获取模型状态失败", e);
            throw new AIException("获取模型状态失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String submitFeedback(String userId, String feedbackContent, String feedbackType) {
        logger.info("提交用户反馈, 用户: {}, 类型: {}", userId, feedbackType);
        
        try {
            // 生成反馈ID
            String feedbackId = "FB-" + feedbackIdCounter.getAndIncrement();
            
            // 实际项目中应该保存到数据库
            logger.info("保存用户反馈, ID: {}, 内容: {}", feedbackId, feedbackContent);
            
            return feedbackId;
        } catch (Exception e) {
            logger.error("提交用户反馈失败", e);
            throw new AIException("提交用户反馈失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String getModelMetrics(String modelId) {
        logger.info("获取模型性能指标: {}", modelId);
        
        try {
            // 模拟获取模型性能指标
            // 实际项目中应该从监控系统或数据库获取
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("modelId", modelId);
            metrics.put("averageResponseTime", 120); // 毫秒
            metrics.put("requestCount", 1000);
            metrics.put("successRate", 0.98);
            metrics.put("errorRate", 0.02);
            metrics.put("timestamp", System.currentTimeMillis());
            
            // 转换为JSON字符串
            // 实际实现中应使用JSON库
            return formatJson(metrics);
        } catch (Exception e) {
            logger.error("获取模型性能指标失败", e);
            throw new AIException("获取模型性能指标失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 简单实现将Map转换为JSON字符串
     * 实际项目中应使用Jackson或Gson等JSON库
     * 
     * @param map 要转换的Map
     * @return JSON字符串
     */
    private String formatJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            sb.append("\"").append(entry.getKey()).append("\":");
            
            Object value = entry.getValue();
            if (value instanceof String) {
                sb.append("\"").append(value).append("\"");
            } else {
                sb.append(value);
            }
            
            if (it.hasNext()) {
                sb.append(",");
            }
        }
        
        sb.append("}");
        return sb.toString();
    }
} 