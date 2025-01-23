package com.lawfirm.core.message.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 用户消息设置
 */
@Data
@Accessors(chain = true)
public class UserMessageSetting implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 是否接收系统通知
     */
    private Boolean receiveSystemNotice = true;
    
    /**
     * 是否接收普通消息
     */
    private Boolean receiveNormalMessage = true;
    
    /**
     * 免打扰时间段（HH:mm-HH:mm）
     */
    private String doNotDisturbTime;
    
    /**
     * 订阅的消息类型
     */
    private Set<String> subscribedTypes;
    
    /**
     * 最后活跃时间
     */
    private LocalDateTime lastActiveTime;
    
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