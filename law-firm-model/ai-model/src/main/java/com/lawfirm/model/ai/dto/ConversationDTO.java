package com.lawfirm.model.ai.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 会话DTO
 * 用于QAService的会话管理
 */
public class ConversationDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    private String conversationId;  // 会话ID
    private String userId;          // 用户ID
    private String message;         // 当前消息
    private String modelId;         // 使用的模型ID
    private transient List<MessageItem> history; // 历史消息（可选，用于初始化会话）
    private transient Map<String, Object> parameters; // 额外参数
    
    public ConversationDTO() {
        this.history = new ArrayList<>();
        this.parameters = new java.util.HashMap<>();
    }
    
    // 消息项
    public static class MessageItem implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String role;     // 角色：user用户/assistant助手/system系统
        private String content;  // 消息内容
        private Long timestamp;  // 时间戳
        
        public MessageItem() {
            this.timestamp = System.currentTimeMillis();
        }
        
        public MessageItem(String role, String content) {
            this.role = role;
            this.content = content;
            this.timestamp = System.currentTimeMillis();
        }
        
        // Getter和Setter
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
        
        public Long getTimestamp() {
            return timestamp;
        }
        
        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
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
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getModelId() {
        return modelId;
    }
    
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    
    public List<MessageItem> getHistory() {
        return history;
    }
    
    public void setHistory(List<MessageItem> history) {
        this.history = history;
    }
    
    public void addMessage(MessageItem message) {
        this.history.add(message);
    }
    
    public void addUserMessage(String content) {
        this.history.add(new MessageItem("user", content));
    }
    
    public void addAssistantMessage(String content) {
        this.history.add(new MessageItem("assistant", content));
    }
    
    public Map<String, Object> getParameters() {
        return parameters;
    }
    
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
    
    public void addParameter(String key, Object value) {
        this.parameters.put(key, value);
    }
} 