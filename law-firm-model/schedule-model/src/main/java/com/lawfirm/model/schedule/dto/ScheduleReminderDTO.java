package com.lawfirm.model.schedule.dto;

import com.lawfirm.model.schedule.entity.enums.ReminderType;
import com.lawfirm.model.schedule.entity.enums.ReminderStatus;
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
     * 提醒状态（待提醒、已提醒、已忽略）
     */
    private ReminderStatus reminderStatus;
    
    /**
     * 提醒内容
     */
    private String content;
    
    /**
     * 接收提醒的用户ID
     */
    private Long userId;
    
    /**
     * 提醒结果
     */
    private String result;
    
    /**
     * 是否已发送
     */
    private Boolean isSent;
    
    /**
     * 发送时间
     */
    private LocalDateTime sentTime;
    
    /**
     * 兼容getRemindTime方法，与旧代码兼容
     */
    public LocalDateTime getRemindTime() {
        return this.reminderTime;
    }
} 