package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 日程提醒实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_reminder")
public class ScheduleReminder extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 关联的日程ID
     */
    @TableField("schedule_id")
    private Long scheduleId;
    
    /**
     * 提醒时间
     */
    @TableField("reminder_time")
    private LocalDateTime reminderTime;
    
    /**
     * 提醒类型（系统提醒、邮件提醒、短信提醒）
     */
    @TableField("reminder_type")
    private Integer reminderType;
    
    /**
     * 提醒状态（待提醒、已提醒、已忽略）
     */
    @TableField("reminder_status")
    private Integer reminderStatus;
    
    /**
     * 提醒内容
     */
    @TableField("content")
    private String content;
    
    /**
     * 接收提醒的用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 提醒结果
     */
    @TableField("result")
    private String result;
    
    /**
     * 是否已发送
     */
    @TableField("is_sent")
    private Boolean isSent;
    
    /**
     * 发送时间
     */
    @TableField("sent_time")
    private LocalDateTime sentTime;
} 