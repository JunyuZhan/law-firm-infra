package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 外部日历实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("schedule_external_calendar")
public class ExternalCalendar extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日历名称
     */
    private String name;

    /**
     * 日历描述
     */
    private String description;

    /**
     * 颜色（十六进制颜色码）
     */
    private String color;

    /**
     * 所有者用户ID
     */
    private Long userId;

    /**
     * 提供商类型：1-Google, 2-Microsoft, 3-Apple, 4-其他
     */
    private Integer providerType;
    
    /**
     * 外部日历ID（提供商平台的ID）
     */
    private String externalId;
    
    /**
     * 访问令牌
     */
    private String accessToken;
    
    /**
     * 刷新令牌
     */
    private String refreshToken;
    
    /**
     * 令牌过期时间
     */
    private LocalDateTime tokenExpireTime;
    
    /**
     * 上次同步时间
     */
    private LocalDateTime lastSyncTime;
    
    /**
     * 状态：1-正常, 2-同步失败, 3-授权过期, 4-已暂停
     */
    @TableField("status")
    private Integer providerStatus;
} 