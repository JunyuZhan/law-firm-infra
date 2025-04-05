package com.lawfirm.model.schedule.constant;

/**
 * 日程事件常量类
 */
public class ScheduleEventConstant {
    
    /**
     * 事件类型前缀
     */
    public static final String EVENT_PREFIX = "schedule.event.";
    
    /**
     * 日程创建事件
     */
    public static final String SCHEDULE_CREATED = EVENT_PREFIX + "schedule.created";
    
    /**
     * 日程更新事件
     */
    public static final String SCHEDULE_UPDATED = EVENT_PREFIX + "schedule.updated";
    
    /**
     * 日程取消事件
     */
    public static final String SCHEDULE_CANCELLED = EVENT_PREFIX + "schedule.cancelled";
    
    /**
     * 日程完成事件
     */
    public static final String SCHEDULE_COMPLETED = EVENT_PREFIX + "schedule.completed";
    
    /**
     * 日程状态变更事件
     */
    public static final String SCHEDULE_STATUS_CHANGED = EVENT_PREFIX + "schedule.status.changed";
    
    /**
     * 日程提醒事件
     */
    public static final String SCHEDULE_REMINDER = EVENT_PREFIX + "schedule.reminder";
    
    /**
     * 日程提醒状态变更事件
     */
    public static final String REMINDER_STATUS_CHANGED = EVENT_PREFIX + "reminder.status.changed";
    
    /**
     * 日程冲突事件
     */
    public static final String SCHEDULE_CONFLICT = EVENT_PREFIX + "schedule.conflict";
    
    /**
     * 参与者添加事件
     */
    public static final String PARTICIPANT_ADDED = EVENT_PREFIX + "participant.added";
    
    /**
     * 参与者移除事件
     */
    public static final String PARTICIPANT_REMOVED = EVENT_PREFIX + "participant.removed";
    
    /**
     * 参与者响应事件
     */
    public static final String PARTICIPANT_RESPONSE = EVENT_PREFIX + "participant.response";
    
    /**
     * 会议室预订事件
     */
    public static final String MEETING_ROOM_BOOKED = EVENT_PREFIX + "meetingroom.booked";
    
    /**
     * 会议室预订确认事件
     */
    public static final String BOOKING_CONFIRMED = EVENT_PREFIX + "booking.confirmed";
    
    /**
     * 会议室预订取消事件
     */
    public static final String BOOKING_CANCELLED = EVENT_PREFIX + "booking.cancelled";
    
    /**
     * 会议室预订完成事件
     */
    public static final String BOOKING_COMPLETED = EVENT_PREFIX + "booking.completed";
    
    /**
     * 会议室预订冲突事件
     */
    public static final String BOOKING_CONFLICT = EVENT_PREFIX + "booking.conflict";
    
    /**
     * 案件关联事件
     */
    public static final String CASE_LINKED = EVENT_PREFIX + "case.linked";
    
    /**
     * 案件解除关联事件
     */
    public static final String CASE_UNLINKED = EVENT_PREFIX + "case.unlinked";
    
    /**
     * 任务关联事件
     */
    public static final String TASK_LINKED = EVENT_PREFIX + "task.linked";
    
    /**
     * 任务解除关联事件
     */
    public static final String TASK_UNLINKED = EVENT_PREFIX + "task.unlinked";
    
    /**
     * 日历同步事件
     */
    public static final String CALENDAR_SYNC = EVENT_PREFIX + "calendar.sync";
    
    /**
     * 事件优先级：高
     */
    public static final int PRIORITY_HIGH = 1;
    
    /**
     * 事件优先级：中
     */
    public static final int PRIORITY_MEDIUM = 2;
    
    /**
     * 事件优先级：低
     */
    public static final int PRIORITY_LOW = 3;
} 