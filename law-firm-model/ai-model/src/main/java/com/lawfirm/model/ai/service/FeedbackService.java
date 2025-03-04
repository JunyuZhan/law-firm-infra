package com.lawfirm.model.ai.service;

import java.util.List;
import java.util.Map;

/**
 * 用户反馈服务接口
 * 提供用户反馈收集、分析和管理功能
 */
public interface FeedbackService {
    
    /**
     * 提交用户反馈
     * 
     * @param userId 用户ID
     * @param feedbackContent 反馈内容
     * @param feedbackType 反馈类型
     * @param aiModelId 相关AI模型ID(可选)
     * @return 反馈ID
     */
    String submitFeedback(String userId, String feedbackContent, String feedbackType, String aiModelId);
    
    /**
     * 获取用户反馈列表
     * 
     * @param userId 用户ID (可选，为null时获取所有)
     * @param feedbackType 反馈类型 (可选，为null时获取所有)
     * @param startTime 开始时间 (可选)
     * @param endTime 结束时间 (可选)
     * @return 反馈列表
     */
    List<Map<String, Object>> getFeedbackList(String userId, String feedbackType, Long startTime, Long endTime);
    
    /**
     * 获取用户反馈统计
     * 
     * @param feedbackType 反馈类型 (可选，为null时统计所有)
     * @param timeSpan 时间跨度 (天)
     * @return 统计结果
     */
    Map<String, Object> getFeedbackStats(String feedbackType, int timeSpan);
    
    /**
     * 获取AI模型相关的反馈
     * 
     * @param modelId AI模型ID
     * @return 反馈列表
     */
    List<Map<String, Object>> getModelFeedback(String modelId);
    
    /**
     * 更新反馈状态
     * 
     * @param feedbackId 反馈ID
     * @param status 新状态
     * @return 是否更新成功
     */
    boolean updateFeedbackStatus(String feedbackId, String status);
    
    /**
     * 回复用户反馈
     * 
     * @param feedbackId 反馈ID
     * @param replyContent 回复内容
     * @param replyUserId 回复用户ID
     * @return 是否回复成功
     */
    boolean replyToFeedback(String feedbackId, String replyContent, String replyUserId);
} 