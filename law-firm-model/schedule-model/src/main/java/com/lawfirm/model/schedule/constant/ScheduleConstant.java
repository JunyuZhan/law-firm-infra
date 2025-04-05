package com.lawfirm.model.schedule.constant;

/**
 * 日程模块常量类
 */
public class ScheduleConstant {
    
    /**
     * 日程相关表名
     */
    public static final String TABLE_SCHEDULE = "t_schedule";
    public static final String TABLE_SCHEDULE_PARTICIPANT = "t_schedule_participant";
    public static final String TABLE_SCHEDULE_REMINDER = "t_schedule_reminder";
    public static final String TABLE_MEETING_ROOM = "t_meeting_room";
    public static final String TABLE_MEETING_ROOM_RESERVATION = "t_meeting_room_reservation";
    public static final String TABLE_SCHEDULE_CASE_RELATION = "t_schedule_case_relation";
    public static final String TABLE_SCHEDULE_TASK_RELATION = "t_schedule_task_relation";
    
    /**
     * 日程类型常量
     */
    public static class ScheduleType {
        public static final int MEETING = 1;
        public static final int TASK = 2;
        public static final int APPOINTMENT = 3;
        public static final int COURT = 4;
        public static final int TRAVEL = 5;
        public static final int LEAVE = 6;
        public static final int OTHER = 99;
        
        public static final String MEETING_DESC = "会议";
        public static final String TASK_DESC = "任务";
        public static final String APPOINTMENT_DESC = "约见";
        public static final String COURT_DESC = "法庭出庭";
        public static final String TRAVEL_DESC = "出差";
        public static final String LEAVE_DESC = "休假";
        public static final String OTHER_DESC = "其他";
    }
    
    /**
     * 优先级常量
     */
    public static class PriorityLevel {
        public static final int HIGH = 1;
        public static final int MEDIUM = 2;
        public static final int LOW = 3;
        
        public static final String HIGH_DESC = "高";
        public static final String MEDIUM_DESC = "中";
        public static final String LOW_DESC = "低";
    }
    
    /**
     * 日程状态常量
     */
    public static class ScheduleStatus {
        public static final int PLANNED = 1;
        public static final int IN_PROGRESS = 2;
        public static final int COMPLETED = 3;
        public static final int CANCELLED = 4;
        
        public static final String PLANNED_DESC = "计划中";
        public static final String IN_PROGRESS_DESC = "进行中";
        public static final String COMPLETED_DESC = "已完成";
        public static final String CANCELLED_DESC = "已取消";
    }
    
    /**
     * 参与者类型常量
     */
    public static class ParticipantType {
        public static final int ORGANIZER = 1;
        public static final int REQUIRED = 2;
        public static final int OPTIONAL = 3;
        
        public static final String ORGANIZER_DESC = "组织者";
        public static final String REQUIRED_DESC = "必要参与者";
        public static final String OPTIONAL_DESC = "可选参与者";
    }
    
    /**
     * 响应状态常量
     */
    public static class ResponseStatus {
        public static final int ACCEPTED = 1;
        public static final int DECLINED = 2;
        public static final int TENTATIVE = 3;
        public static final int PENDING = 4;
        
        public static final String ACCEPTED_DESC = "已接受";
        public static final String DECLINED_DESC = "已拒绝";
        public static final String TENTATIVE_DESC = "暂定";
        public static final String PENDING_DESC = "未回复";
    }
    
    /**
     * 提醒类型常量
     */
    public static class ReminderType {
        public static final int SYSTEM = 1;
        public static final int EMAIL = 2;
        public static final int SMS = 3;
        public static final int APP_PUSH = 4;
        
        public static final String SYSTEM_DESC = "系统提醒";
        public static final String EMAIL_DESC = "邮件提醒";
        public static final String SMS_DESC = "短信提醒";
        public static final String APP_PUSH_DESC = "APP推送";
    }
    
    /**
     * 提醒状态常量
     */
    public static class ReminderStatus {
        public static final int PENDING = 1;
        public static final int REMINDED = 2;
        public static final int IGNORED = 3;
        public static final int FAILED = 4;
        
        public static final String PENDING_DESC = "待提醒";
        public static final String REMINDED_DESC = "已提醒";
        public static final String IGNORED_DESC = "已忽略";
        public static final String FAILED_DESC = "发送失败";
    }
    
    /**
     * 会议室状态常量
     */
    public static class RoomStatus {
        public static final int AVAILABLE = 1;
        public static final int MAINTENANCE = 2;
        public static final int DISABLED = 3;
        
        public static final String AVAILABLE_DESC = "可用";
        public static final String MAINTENANCE_DESC = "维护中";
        public static final String DISABLED_DESC = "停用";
    }
    
    /**
     * 预约状态常量
     */
    public static class ReservationStatus {
        public static final int PENDING = 1;
        public static final int CONFIRMED = 2;
        public static final int REJECTED = 3;
        public static final int CANCELLED = 4;
        
        public static final String PENDING_DESC = "待审核";
        public static final String CONFIRMED_DESC = "已确认";
        public static final String REJECTED_DESC = "已拒绝";
        public static final String CANCELLED_DESC = "已取消";
    }
    
    /**
     * 时间常量
     */
    public static class TimeConstants {
        public static final long MINUTE_IN_MILLIS = 60 * 1000L;
        public static final long HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS;
        public static final long DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS;
        public static final long WEEK_IN_MILLIS = 7 * DAY_IN_MILLIS;
    }
    
    /**
     * 缓存相关常量
     */
    public static class CacheConstants {
        public static final String MEETING_ROOM_CACHE_PREFIX = "schedule:meeting_room:";
        public static final String SCHEDULE_CACHE_PREFIX = "schedule:schedule:";
        public static final long MEETING_ROOM_CACHE_EXPIRE = 24 * 60 * 60; // 24小时
        public static final long SCHEDULE_CACHE_EXPIRE = 12 * 60 * 60; // 12小时
    }
    
    /**
     * 权限相关常量
     */
    public static class PermissionConstants {
        public static final String SCHEDULE_ADMIN = "schedule:admin";
        public static final String SCHEDULE_CREATE = "schedule:create";
        public static final String SCHEDULE_UPDATE = "schedule:update";
        public static final String SCHEDULE_DELETE = "schedule:delete";
        public static final String SCHEDULE_VIEW = "schedule:view";
        public static final String MEETING_ROOM_ADMIN = "schedule:meeting_room:admin";
        public static final String MEETING_ROOM_RESERVE = "schedule:meeting_room:reserve";
    }
} 