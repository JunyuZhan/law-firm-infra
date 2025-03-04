package com.lawfirm.core.ai.utils;

import com.lawfirm.common.util.string.StringExtUtils;
import com.lawfirm.model.ai.enums.ModelStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * 模型工具类
 * 提供AI模型相关的工具方法
 */
public class ModelUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ModelUtils.class);
    
    /**
     * 默认模型参数
     */
    private static final Map<String, Object> DEFAULT_MODEL_PARAMS = new HashMap<>();
    
    static {
        DEFAULT_MODEL_PARAMS.put("temperature", 0.7);
        DEFAULT_MODEL_PARAMS.put("max_tokens", 2000);
        DEFAULT_MODEL_PARAMS.put("top_p", 1.0);
        DEFAULT_MODEL_PARAMS.put("frequency_penalty", 0.0);
        DEFAULT_MODEL_PARAMS.put("presence_penalty", 0.0);
    }
    
    /**
     * 获取默认模型参数
     * 
     * @return 默认参数Map
     */
    public static Map<String, Object> getDefaultModelParams() {
        return new HashMap<>(DEFAULT_MODEL_PARAMS);
    }
    
    /**
     * 合并模型参数
     * 
     * @param userParams 用户参数
     * @return 合并后的参数
     */
    public static Map<String, Object> mergeModelParams(Map<String, Object> userParams) {
        Map<String, Object> mergedParams = getDefaultModelParams();
        
        if (userParams != null && !userParams.isEmpty()) {
            mergedParams.putAll(userParams);
        }
        
        return mergedParams;
    }
    
    /**
     * 验证模型参数
     * 
     * @param params 参数Map
     * @return 是否有效
     */
    public static boolean validateModelParams(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return false;
        }
        
        try {
            // 验证temperature
            Object temp = params.get("temperature");
            if (temp != null) {
                double temperature = Double.parseDouble(temp.toString());
                if (temperature < 0.0 || temperature > 1.0) {
                    return false;
                }
            }
            
            // 验证max_tokens
            Object tokens = params.get("max_tokens");
            if (tokens != null) {
                int maxTokens = Integer.parseInt(tokens.toString());
                if (maxTokens <= 0) {
                    return false;
                }
            }
            
            // 验证top_p
            Object topP = params.get("top_p");
            if (topP != null) {
                double p = Double.parseDouble(topP.toString());
                if (p < 0.0 || p > 1.0) {
                    return false;
                }
            }
            
            return true;
        } catch (NumberFormatException e) {
            logger.error("模型参数验证失败", e);
            return false;
        }
    }
    
    /**
     * 检查模型状态是否可用
     * 
     * @param status 模型状态
     * @return 是否可用
     */
    public static boolean isModelAvailable(ModelStatus status) {
        return status == ModelStatus.DEPLOYED;
    }
    
    /**
     * 计算token数量（简单估算）
     * 
     * @param text 文本
     * @return token数量
     */
    public static int estimateTokenCount(String text) {
        if (StringUtils.isEmpty(text)) {
            return 0;
        }
        
        // 简单估算：按空格分词，每个单词算1个token
        // 实际应该使用具体模型的tokenizer
        String[] words = text.trim().split("\\s+");
        return words.length;
    }
    
    /**
     * 检查是否超出token限制
     * 
     * @param text 文本
     * @param maxTokens 最大token数
     * @return 是否超出限制
     */
    public static boolean isExceedTokenLimit(String text, int maxTokens) {
        return estimateTokenCount(text) > maxTokens;
    }
    
    /**
     * 获取模型错误描述
     * 
     * @param errorCode 错误码
     * @return 错误描述
     */
    public static String getModelErrorDescription(String errorCode) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("MODEL_NOT_FOUND", "模型不存在");
        errorMap.put("MODEL_OFFLINE", "模型已下线");
        errorMap.put("TOKEN_LIMIT_EXCEEDED", "超出Token限制");
        errorMap.put("INVALID_PARAMETERS", "无效的参数");
        errorMap.put("API_ERROR", "API调用错误");
        
        return errorMap.getOrDefault(errorCode, "未知错误");
    }
    
    /**
     * 构建模型调用配置
     * 
     * @param modelId 模型ID
     * @param params 参数
     * @return 配置Map
     */
    public static Map<String, Object> buildModelConfig(String modelId, Map<String, Object> params) {
        Map<String, Object> config = new HashMap<>();
        config.put("model_id", modelId);
        config.put("params", mergeModelParams(params));
        config.put("timestamp", System.currentTimeMillis());
        
        return config;
    }
} 