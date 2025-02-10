package com.lawfirm.model.base.message.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户消息设置实体
 */
@Data
@Entity
@Table(name = "user_message_setting")
@Accessors(chain = true)
public class UserMessageSettingEntity {
    
    @Id
    private Long userId;
    
    /**
     * 是否接收站内信
     */
    private Boolean receiveSiteMessage;
    
    /**
     * 是否接收邮件
     */
    private Boolean receiveEmail;
    
    /**
     * 是否接收短信
     */
    private Boolean receiveSms;
    
    /**
     * 是否接收微信消息
     */
    private Boolean receiveWechat;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 