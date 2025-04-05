package com.lawfirm.model.schedule.vo;

import com.lawfirm.model.schedule.entity.enums.ReminderStatus;
import com.lawfirm.model.schedule.entity.enums.ReminderType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日程提醒VO类
 */
@Data
public class ScheduleReminderVO {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 关联的日程ID
     */
    private Long scheduleId;
    
    /**
     * 提醒时间
     */
    private LocalDateTime reminderTime;
    
    /**
     * 提醒类型（系统提醒、邮件提醒、短信提醒）
     */
    private ReminderType reminderType;
    
    /**
     * 提醒类型描述
     */
    private String reminderTypeDesc;
    
    /**
     * 提醒状态
     */
    private ReminderStatus reminderStatus;
    
    /**
     * 提醒状态描述
     */
    private String reminderStatusDesc;
    
    /**
     * 提醒内容
     */
    private String content;
    
    /**
     * 接收提醒的用户ID
     */
    private Long userId;
    
    /**
     * 接收提醒的用户名
     */
    private String userName;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 