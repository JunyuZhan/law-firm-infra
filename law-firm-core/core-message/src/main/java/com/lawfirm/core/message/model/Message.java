package com.lawfirm.core.message.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import com.lawfirm.model.base.message.enums.MessageType;

@Data
@Builder
public class Message {
    /**
     * 消息ID
     */
    private String id;

    /**
     * 消息类型
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
     * 是否已读
     */
    private boolean read;

    /**
     * 读取时间
     */
    private LocalDateTime readTime;

    /**
     * 消息模板ID
     */
    private String templateId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 消息参数
     */
    private Map<String, Object> params;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 