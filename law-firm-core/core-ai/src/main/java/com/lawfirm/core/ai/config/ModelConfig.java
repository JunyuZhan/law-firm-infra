package com.lawfirm.core.ai.config;

import com.lawfirm.model.ai.enums.AiModelTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * AI模型配置�? *
 * @author JunyuZhan */
@Data
@Configuration
@ConfigurationProperties(prefix = "lawfirm.ai.model")
public class ModelConfig {

    /**
     * OpenAI配置
     */
    private OpenAIConfig openai = new OpenAIConfig();
    
    /**
     * 百度AI配置
     */
    private BaiduAIConfig baidu = new BaiduAIConfig();
    
    /**
     * 本地AI模型配置
     */
    private LocalAIConfig local = new LocalAIConfig();
    
    /**
     * 通用模型参数
     */
    private Map<String, ModelParam> modelParams = new HashMap<>();
    
    /**
     * OpenAI配置�?     */
    @Data
    public static class OpenAIConfig {
        /**
         * API密钥
         */
        private String apiKey;
        
        /**
         * 基础URL
         */
        private String baseUrl = "https://api.openai.com/v1";
        
        /**
         * 默认模型
         */
        private String defaultModel = "gpt-4";
        
        /**
         * 代理设置
         */
        private String proxy;
        
        /**
         * 组织ID
         */
        private String organizationId;
    }
    
    /**
     * 百度AI配置�?     */
    @Data
    public static class BaiduAIConfig {
        /**
         * API密钥
         */
        private String apiKey;
        
        /**
         * 密钥ID
         */
        private String secretKey;
        
        /**
         * 基础URL
         */
        private String baseUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop";
        
        /**
         * 默认模型
         */
        private String defaultModel = "ERNIE-Bot-4";
    }
    
    /**
     * 本地AI配置�?     */
    @Data
    public static class LocalAIConfig {
        /**
         * 模型路径
         */
        private String modelPath;
        
        /**
         * 模型类型
         */
        private String modelType = "llama";
        
        /**
         * 是否启用GPU加�?         */
        private Boolean enableGpu = false;
        
        /**
         * 模型线程�?         */
        private Integer threads = 4;
    }
    
    /**
     * 模型参数�?     */
    @Data
    public static class ModelParam {
        /**
         * 模型类型
         */
        private AiModelTypeEnum modelType;
        
        /**
         * 温度参数
         */
        private Float temperature = 0.7f;
        
        /**
         * 最大输出token�?         */
        private Integer maxTokens = 4096;
        
        /**
         * Top P采样
         */
        private Float topP = 1.0f;
        
        /**
         * 重复惩罚
         */
        private Float frequencyPenalty = 0.0f;
        
        /**
         * 主题惩罚
         */
        private Float presencePenalty = 0.0f;
    }
} 
