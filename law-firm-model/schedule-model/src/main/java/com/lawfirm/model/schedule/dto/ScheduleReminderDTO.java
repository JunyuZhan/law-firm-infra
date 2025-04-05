package com.lawfirm.model.schedule.dto;

import com.lawfirm.model.schedule.entity.enums.ReminderType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 日程提醒DTO类
 */
@Data
public class ScheduleReminderDTO {
    
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
    @NotNull(message = "提醒时间不能为空")
    private LocalDateTime reminderTime;
    
    /**
     * 提醒类型（系统提醒、邮件提醒、短信提醒）
     */
    @NotNull(message = "提醒类型不能为空")
    private ReminderType reminderType;
    
    /**
     * 提醒内容
     */
    private String content;
    
    /**
     * 接收提醒的用户ID
     */
    private Long userId;
} 