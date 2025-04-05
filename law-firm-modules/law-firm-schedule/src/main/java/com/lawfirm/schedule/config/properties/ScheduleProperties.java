package com.lawfirm.schedule.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 日程模块配置属性
 */
@Data
@Component("scheduleProperties")
@ConfigurationProperties(prefix = "lawfirm.schedule")
public class ScheduleProperties {
    
    /**
     * 模块是否启用
     */
    private boolean enabled = true;
    
    /**
     * 日程提醒配置
     */
    private ReminderProperties reminder = new ReminderProperties();
    
    /**
     * 日程同步配置
     */
    private SyncProperties sync = new SyncProperties();
    
    /**
     * 日程冲突检测配置
     */
    private ConflictProperties conflict = new ConflictProperties();
    
    /**
     * 日程重复规则配置
     */
    private RecurrenceProperties recurrence = new RecurrenceProperties();
    
    /**
     * 会议室配置
     */
    private MeetingRoomProperties meetingRoom = new MeetingRoomProperties();
    
    /**
     * 日程提醒配置
     */
    @Data
    public static class ReminderProperties {
        /**
         * 是否启用提醒
         */
        private boolean enabled = true;
        
        /**
         * 默认提醒时间（分钟）
         */
        private List<Integer> defaultTimes = Arrays.asList(15, 60, 1440);
        
        /**
         * 提醒渠道
         */
        private String channels = "email,system,sms";
        
        /**
         * 最大提醒次数
         */
        private int maxRetryCount = 3;
        
        /**
         * 提醒重试间隔（分钟）
         */
        private int retryInterval = 5;
    }
    
    /**
     * 日程同步配置
     */
    @Data
    public static class SyncProperties {
        /**
         * 是否启用同步
         */
        private boolean enabled = true;
        
        /**
         * 自动同步间隔（分钟）
         */
        private int interval = 30;
        
        /**
         * 支持的提供商列表
         */
        private List<String> providers = Arrays.asList("google", "outlook", "exchange");
        
        /**
         * 同步历史保留天数
         */
        private int historyDays = 90;
    }
    
    /**
     * 冲突检测配置
     */
    @Data
    public static class ConflictProperties {
        /**
         * 是否启用冲突检测
         */
        private boolean enabled = true;
        
        /**
         * 检测级别：warn/block
         */
        private String level = "warn";
        
        /**
         * 缓冲时间（分钟）
         */
        private int bufferMinutes = 15;
    }
    
    /**
     * 重复规则配置
     */
    @Data
    public static class RecurrenceProperties {
        /**
         * 是否启用重复规则
         */
        private boolean enabled = true;
        
        /**
         * 最大实例数
         */
        private int maxInstances = 50;
        
        /**
         * 支持的重复模式
         */
        private List<String> patterns = Arrays.asList("daily", "weekly", "monthly", "yearly");
    }
    
    /**
     * 会议室配置
     */
    @Data
    public static class MeetingRoomProperties {
        /**
         * 是否启用审批
         */
        private boolean approvalRequired = false;
        
        /**
         * 预订提前通知时间（小时）
         */
        private int bookingNoticeHours = 1;
        
        /**
         * 最大预订时长（小时）
         */
        private int maxBookingHours = 8;
        
        /**
         * 允许预订未来天数
         */
        private int maxFutureDays = 90;
    }
} 