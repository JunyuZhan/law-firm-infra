package com.lawfirm.model.base.message.entity;

import com.lawfirm.model.base.message.enums.MessageType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户消息设置实体
 */
@Data
@Entity
@Table(name = "user_message_setting", indexes = {
    @Index(name = "idx_user_type", columnList = "user_id,type", unique = true)
})
public class UserMessageSettingEntity {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 消息类型
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    /**
     * 是否接收站内信
     */
    @Column(name = "receive_site_message")
    private Boolean receiveSiteMessage = true;

    /**
     * 是否接收邮件
     */
    @Column(name = "receive_email")
    private Boolean receiveEmail = false;

    /**
     * 是否接收短信
     */
    @Column(name = "receive_sms")
    private Boolean receiveSms = false;

    /**
     * 是否接收微信消息
     */
    @Column(name = "receive_wechat")
    private Boolean receiveWechat = false;

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