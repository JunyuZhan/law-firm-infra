package com.lawfirm.model.ai.service;

/**
 * AI模型服务接口
 * 提供AI模型的调用和管理功能
 */
public interface AIModelService {
    
    /**
     * 获取默认AI模型
     * 
     * @return 默认模型ID
     */
    String getDefaultModel();
    
    /**
     * 切换当前使用的AI模型
     * 
     * @param modelId 模型ID
     * @return 是否切换成功
     */
    boolean switchModel(String modelId);
    
    /**
     * 获取模型状态
     * 
     * @param modelId 模型ID
     * @return 模型状态
     */
    String getModelStatus(String modelId);
    
    /**
     * 提交用户反馈
     * 
     * @param userId 用户ID
     * @param feedbackContent 反馈内容
     * @param feedbackType 反馈类型
     * @return 反馈ID
     */
    String submitFeedback(String userId, String feedbackContent, String feedbackType);
    
    /**
     * 获取模型性能指标
     * 
     * @param modelId 模型ID
     * @return 性能指标JSON
     */
    String getModelMetrics(String modelId);
} 