package com.lawfirm.model.ai.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息实体类
 * 用于存储会话中的单条消息
 */
public class Message extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    private String messageId;         // 消息ID
    private String conversationId;    // 所属会话ID
    private String role;              // 角色：user用户/assistant助手/system系统
    private String content;           // 消息内容
    private LocalDateTime sendTime;   // 发送时间
    private Integer tokenCount;       // Token数量
    private String references;        // 引用来源（JSON格式）
    private Double confidence;        // 置信度（0-1）
    
    // 构造函数
    public Message() {
        this.sendTime = LocalDateTime.now();
    }
    
    public Message(String conversationId, String role, String content) {
        this();
        this.conversationId = conversationId;
        this.role = role;
        this.content = content;
    }
    
    // Getter和Setter
    public String getMessageId() {
        return messageId;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public String getConversationId() {
        return conversationId;
    }
    
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDateTime getSendTime() {
        return sendTime;
    }
    
    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
    
    public Integer getTokenCount() {
        return tokenCount;
    }
    
    public void setTokenCount(Integer tokenCount) {
        this.tokenCount = tokenCount;
    }
    
    public String getReferences() {
        return references;
    }
    
    public void setReferences(String references) {
        this.references = references;
    }
    
    public Double getConfidence() {
        return confidence;
    }
    
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
} 