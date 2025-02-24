package com.lawfirm.core.message.model.entity;

import com.lawfirm.model.base.message.enums.MessageType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_message_setting")
@Accessors(chain = true)
public class UserMessageSettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MessageType type;

    @Column(name = "receive_site_message", nullable = false)
    private Boolean receiveSiteMessage = true;

    @Column(name = "receive_email", nullable = false)
    private Boolean receiveEmail = false;

    @Column(name = "receive_sms", nullable = false)
    private Boolean receiveSms = false;

    @Column(name = "receive_wechat", nullable = false)
    private Boolean receiveWechat = false;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
} 