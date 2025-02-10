package com.lawfirm.model.base.message.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 消息实体
 */
@Data
@Entity
@Table(name = "message")
@Accessors(chain = true)
public class MessageEntity {
    
    @Id
    private String id;
    
    /**
     * 消息类型
     */
    private String type;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 内容
     */
    private String content;
    
    /**
     * 模板ID
     */
    private String templateId;
    
    /**
     * 模板参数
     */
    private String params;
    
    /**
     * 发送者ID
     */
    private Long senderId;
    
    /**
     * 接收者ID
     */
    private Long receiverId;
    
    /**
     * 接收者名称
     */
    private String receiverName;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 业务ID
     */
    private String businessId;
    
    /**
     * 优先级
     */
    private Integer priority;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 是否已读
     */
    private Boolean isRead;
    
    /**
     * 阅读时间
     */
    private LocalDateTime readTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 