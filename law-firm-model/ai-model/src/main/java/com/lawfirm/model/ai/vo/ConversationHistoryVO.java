package com.lawfirm.model.ai.vo;

import com.lawfirm.model.base.vo.BaseVO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会话历史VO
 * 用于QAService返回会话历史
 */
public class ConversationHistoryVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    private String conversationId;   // 会话ID
    private String userId;           // 用户ID
    private String title;            // 会话标题
    private Date lastActiveTime;     // 最后活动时间
    private String conversationStatus; // 会话状态：active进行中/closed已结束
    private String modelId;          // 使用的模型ID
    private transient List<MessageVO> messages; // 消息列表
    
    /**
     * 默认构造函数，仅初始化自身字段，不调用父类方法
     */
    public ConversationHistoryVO() {
        this.messages = new ArrayList<>();
        this.lastActiveTime = new Date();
        this.conversationStatus = "active";
    }
    
    /**
     * 创建一个完全初始化的ConversationHistoryVO实例
     * @return 初始化的ConversationHistoryVO实例
     */
    public static ConversationHistoryVO createDefault() {
        ConversationHistoryVO vo = new ConversationHistoryVO();
        vo.setCreateTime(LocalDateTime.now());
        vo.setStatus(0); // 0表示正常状态
        return vo;
    }
    
    // 消息VO
    public static class MessageVO implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String messageId;   // 消息ID
        private String role;        // 角色：user用户/assistant助手/system系统
        private String content;     // 消息内容
        private Date timestamp;     // 消息时间
        private transient List<String> references; // 引用列表（可选）
        
        public MessageVO() {
            this.timestamp = new Date();
            this.references = new ArrayList<>();
        }
        
        // Getter和Setter
        public String getMessageId() {
            return messageId;
        }
        
        public void setMessageId(String messageId) {
            this.messageId = messageId;
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
        
        public Date getTimestamp() {
            return timestamp;
        }
        
        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }
        
        public List<String> getReferences() {
            return references;
        }
        
        public void setReferences(List<String> references) {
            this.references = references;
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Date getLastActiveTime() {
        return lastActiveTime;
    }
    
    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
    
    public String getConversationStatus() {
        return conversationStatus;
    }
    
    public void setConversationStatus(String conversationStatus) {
        this.conversationStatus = conversationStatus;
    }
    
    public String getModelId() {
        return modelId;
    }
    
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    
    public List<MessageVO> getMessages() {
        return messages;
    }
    
    public void setMessages(List<MessageVO> messages) {
        this.messages = messages;
    }
    
    public void addMessage(MessageVO message) {
        this.messages.add(message);
        this.lastActiveTime = new Date();
    }
} 