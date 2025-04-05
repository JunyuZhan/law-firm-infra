package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.schedule.entity.enums.SyncStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 外部日历同步实体类
 * 用于支持与外部日历系统（如Google Calendar、Outlook等）的同步
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_external_calendar_sync")
public class ExternalCalendarSync extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 外部日历类型（如Google、Outlook、iCloud等）
     */
    @TableField("calendar_type")
    private String calendarType;
    
    /**
     * 外部日历ID/标识
     */
    @TableField("external_calendar_id")
    private String externalCalendarId;
    
    /**
     * 外部日历名称
     */
    @TableField("calendar_name")
    private String calendarName;
    
    /**
     * 同步令牌/访问Token
     */
    @TableField("access_token")
    private String accessToken;
    
    /**
     * 刷新令牌
     */
    @TableField("refresh_token")
    private String refreshToken;
    
    /**
     * Token过期时间
     */
    @TableField("token_expires_at")
    private LocalDateTime tokenExpiresAt;
    
    /**
     * 最后同步时间
     */
    @TableField("last_sync_time")
    private LocalDateTime lastSyncTime;
    
    /**
     * 同步状态
     */
    @TableField("sync_status")
    private SyncStatus syncStatus;
    
    /**
     * 是否启用自动同步
     */
    @TableField("auto_sync_enabled")
    private Boolean autoSyncEnabled;
    
    /**
     * 同步频率（分钟）
     */
    @TableField("sync_interval")
    private Integer syncInterval;
    
    /**
     * 同步方向（单向导入、单向导出、双向）
     */
    @TableField("sync_direction")
    private String syncDirection;
} 