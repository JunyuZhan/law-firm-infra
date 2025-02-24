package com.lawfirm.core.message.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserMessageSetting {
    /**
     * 设置ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 消息类型
     */
    private MessageType type;

    /**
     * 是否接收邮件通知
     */
    private Boolean emailEnabled;

    /**
     * 是否接收短信通知
     */
    private Boolean smsEnabled;

    /**
     * 是否接收站内信
     */
    private Boolean internalEnabled;

    /**
     * 是否接收WebSocket通知
     */
    private Boolean websocketEnabled;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 