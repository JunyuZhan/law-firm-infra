package com.lawfirm.schedule.constant;

/**
 * 日程模块常量定义
 */
public final class ScheduleConstants {
    
    private ScheduleConstants() {
        // 私有构造函数防止实例化
    }
    
    /**
     * API路径前缀
     */
    public static final String API_PREFIX = "/api/v1/schedules";
    
    /**
     * 日程事件API路径前缀
     */
    public static final String API_EVENT_PREFIX = "/api/v1/schedule-events";
    
    /**
     * 日程关联API路径前缀
     */
    public static final String API_RELATION_PREFIX = "/api/v1/schedule-relations";
    
    /**
     * 日程提醒API路径前缀
     */
    public static final String API_REMINDER_PREFIX = "/api/v1/schedule-reminders";
    
    /**
     * 日程参与者API路径前缀
     */
    public static final String API_PARTICIPANT_PREFIX = "/api/v1/schedule-participants";
    
    /**
     * 日程日历API路径前缀
     */
    public static final String API_CALENDAR_PREFIX = "/api/v1/schedule-calendars";
    
    /**
     * 外部日历API路径前缀
     */
    public static final String API_EXTERNAL_CALENDAR_PREFIX = "/api/v1/external-calendars";
    
    /**
     * 会议室API路径前缀
     */
    public static final String API_MEETING_ROOM_PREFIX = "/api/v1/meeting-rooms";
    
    /**
     * 会议室预订API路径前缀
     */
    public static final String API_MEETING_ROOM_BOOKING_PREFIX = "/api/v1/meeting-room-bookings";
} 