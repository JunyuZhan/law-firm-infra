package com.lawfirm.model.base.message.entity;

import com.lawfirm.model.base.message.enums.MessageType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息实体
 */
@Data
@Entity
@Table(name = "message", indexes = {
    @Index(name = "idx_receiver", columnList = "receiver_id,is_read"),
    @Index(name = "idx_sender", columnList = "sender_id"),
    @Index(name = "idx_business", columnList = "business_type,business_id")
})
public class MessageEntity {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 消息标题
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 消息内容
     */
    @Column(name = "content", length = 4000)
    private String content;

    /**
     * 消息类型
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    /**
     * 发送者ID
     */
    @Column(name = "sender_id")
    private Long senderId;

    /**
     * 发送者名称
     */
    @Column(name = "sender_name")
    private String senderName;

    /**
     * 接收者ID
     */
    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    /**
     * 接收者名称
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 是否已读
     */
    @Column(name = "is_read")
    private Boolean isRead = false;

    /**
     * 读取时间
     */
    @Column(name = "read_time")
    private LocalDateTime readTime;

    /**
     * 消息模板ID
     */
    @Column(name = "template_id")
    private String templateId;

    /**
     * 消息参数（JSON格式）
     */
    @Column(name = "params")
    private String params;

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    private String businessType;

    /**
     * 业务ID
     */
    @Column(name = "business_id")
    private String businessId;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
} 