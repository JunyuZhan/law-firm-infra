package com.lawfirm.model.ai.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AI模型配置实体类
 * 用于存储不同AI模型的配置信息
 */
public class AIModelConfig extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "模型ID")
    private String modelId;          // 模型ID
    @Schema(description = "模型名称")
    private String modelName;        // 模型名称
    @Schema(description = "服务提供商（如OpenAI、百度、Deepseek等）")
    private String provider;         // 服务提供商（如OpenAI、百度等）
    @Schema(description = "模型类型")
    private String modelType;        // 模型类型
    @Schema(description = "API版本")
    private String apiVersion;       // API版本
    @Schema(description = "模型版本")
    private String modelVersion;     // 模型版本
    @Schema(description = "服务端点URL")
    private String endpoint;         // 服务端点URL
    @Schema(description = "API密钥（加密存储）")
    private String apiKey;           // API密钥（加密存储）
    @Schema(description = "最大Token数")
    private Integer maxTokens;       // 最大Token数
    @Schema(description = "温度参数（控制创造性，0-2）")
    private Double temperature;      // 温度参数（控制创造性，0-2）
    @Schema(description = "Top P参数")
    private Double topP;             // Top P参数
    @Schema(description = "提示词模板（系统提示词）")
    private String promptTemplate;   // 提示词模板（系统提示词）
    @Schema(description = "最后使用时间")
    private LocalDateTime lastUsedTime; // 最后使用时间
    @Schema(description = "额外配置（JSON格式）")
    private String configJson;       // 额外配置（JSON格式）
    @Schema(description = "描述")
    private String description;      // 描述
    
    /**
     * 默认构造函数，仅初始化自身字段，不调用父类方法
     */
    public AIModelConfig() {
        this.temperature = 0.7;
        this.topP = 1.0;
        this.maxTokens = 2048;
    }
    
    /**
     * 创建一个完全初始化的AiModelConfig实例
     * @return 初始化的AiModelConfig实例
     */
    public static AIModelConfig createDefault() {
        AIModelConfig config = new AIModelConfig();
        config.setStatus(0); // 正常状态
        return config;
    }
    
    // Getter和Setter
    public String getModelId() {
        return modelId;
    }
    
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    
    public String getModelName() {
        return modelName;
    }
    
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    public String getModelType() {
        return modelType;
    }
    
    public void setModelType(String modelType) {
        this.modelType = modelType;
    }
    
    public String getApiVersion() {
        return apiVersion;
    }
    
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
    
    public String getModelVersion() {
        return modelVersion;
    }
    
    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }
    
    public String getEndpoint() {
        return endpoint;
    }
    
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    public String getApiKey() {
        return apiKey;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public Integer getMaxTokens() {
        return maxTokens;
    }
    
    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }
    
    public Double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    
    public Double getTopP() {
        return topP;
    }
    
    public void setTopP(Double topP) {
        this.topP = topP;
    }
    
    public String getPromptTemplate() {
        return promptTemplate;
    }
    
    public void setPromptTemplate(String promptTemplate) {
        this.promptTemplate = promptTemplate;
    }
    
    @Override
    public Integer getStatus() {
        return super.getStatus();
    }
    
    @Override
    public AIModelConfig setStatus(Integer status) {
        super.setStatus(status);
        return this;
    }
    
    public LocalDateTime getLastUsedTime() {
        return lastUsedTime;
    }
    
    public void setLastUsedTime(LocalDateTime lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }
    
    public String getConfigJson() {
        return configJson;
    }
    
    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}