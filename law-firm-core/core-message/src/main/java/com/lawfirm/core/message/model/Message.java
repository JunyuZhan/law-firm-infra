package com.lawfirm.core.message.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息模型
 */
@Data
@Accessors(chain = true)
public class Message implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 消息ID
     */
    private String id;
    
    /**
     * 消息类型（普通消息/模板消息/系统通知）
     */
    private MessageType type;
    
    /**
     * 消息标题
     */
    private String title;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 消息模板ID（如果是模板消息）
     */
    private String templateId;
    
    /**
     * 模板参数（如果是模板消息）
     */
    private Object templateParams;
    
    /**
     * 发送者ID
     */
    private Long senderId;
    
    /**
     * 发送者名称
     */
    private String senderName;
    
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
     * 优先级（0-9，数字越大优先级越高）
     */
    private Integer priority = 0;
    
    /**
     * 是否已读
     */
    private Boolean read = false;
    
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
    
    /**
     * 租户ID
     */
    private Long tenantId;
} 