package com.lawfirm.model.ai.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import java.time.LocalDateTime;

/**
 * 用户反馈实体类
 * 用于存储用户对AI功能的反馈信息
 */
public class UserFeedback extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    private String userId;             // 用户ID
    private String feedbackContent;    // 反馈内容
    private String feedbackType;       // 反馈类型
    private String aiModelId;          // AI模型ID
    private LocalDateTime feedbackTime;// 反馈时间
    private Integer processingStatus;  // 处理状态：0待处理/1已处理/2已回复
    private String processorId;        // 处理人ID
    private String replyContent;       // 回复内容
    private LocalDateTime replyTime;   // 回复时间
    private String replyUserId;        // 回复用户ID
    
    public UserFeedback() {
        this.feedbackTime = LocalDateTime.now();
        this.processingStatus = 0;
    }
    
    // Getter和Setter
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getFeedbackContent() {
        return feedbackContent;
    }
    
    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
    
    public String getFeedbackType() {
        return feedbackType;
    }
    
    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }
    
    public String getAiModelId() {
        return aiModelId;
    }
    
    public void setAiModelId(String aiModelId) {
        this.aiModelId = aiModelId;
    }
    
    public LocalDateTime getFeedbackTime() {
        return feedbackTime;
    }
    
    public void setFeedbackTime(LocalDateTime feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
    
    public Integer getProcessingStatus() {
        return processingStatus;
    }
    
    public void setProcessingStatus(Integer processingStatus) {
        this.processingStatus = processingStatus;
    }
    
    public String getProcessorId() {
        return processorId;
    }
    
    public void setProcessorId(String processorId) {
        this.processorId = processorId;
    }
    
    public String getReplyContent() {
        return replyContent;
    }
    
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
    
    public LocalDateTime getReplyTime() {
        return replyTime;
    }
    
    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }
    
    public String getReplyUserId() {
        return replyUserId;
    }
    
    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }
} 