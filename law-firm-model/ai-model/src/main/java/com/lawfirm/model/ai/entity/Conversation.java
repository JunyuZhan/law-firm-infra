package com.lawfirm.model.ai.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * AI对话会话实体类
 * 用于存储用户与AI的对话历史
 */
public class Conversation extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    private String conversationId;    // 会话ID（UUID）
    private String userId;            // 用户ID
    private String title;             // 会话标题
    private LocalDateTime lastActiveTime; // 最后活动时间
    private Integer conversationStatus;   // 会话状态：0进行中/1已结束/2已归档
    private String modelId;           // 使用的AI模型ID
    private Integer messageCount;     // 消息数量
    private String summary;           // 会话摘要
    private String category;          // 对话类别（如法律咨询、文档分析等）
    
    // 构造函数
    public Conversation() {
        this.lastActiveTime = LocalDateTime.now();
        this.conversationStatus = 0;
        this.messageCount = 0;
    }
    
    // Getter和Setter
    public String getConversationId() {
        return conversationId;
    }
    
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public LocalDateTime getLastActiveTime() {
        return lastActiveTime;
    }
    
    public void setLastActiveTime(LocalDateTime lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
    
    public Integer getConversationStatus() {
        return conversationStatus;
    }
    
    public void setConversationStatus(Integer conversationStatus) {
        this.conversationStatus = conversationStatus;
    }
    
    public String getModelId() {
        return modelId;
    }
    
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    
    public Integer getMessageCount() {
        return messageCount;
    }
    
    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }
    
    public void incrementMessageCount() {
        this.messageCount++;
        this.lastActiveTime = LocalDateTime.now();
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
} 