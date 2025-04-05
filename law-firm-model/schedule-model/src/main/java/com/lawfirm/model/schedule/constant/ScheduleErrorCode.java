package com.lawfirm.model.schedule.constant;

/**
 * 日程模块错误码常量类
 */
public class ScheduleErrorCode {
    
    /**
     * 通用错误码前缀：10600
     */
    private static final String PREFIX = "10600";
    
    /**
     * 通用错误信息
     */
    public static final String COMMON_ERROR = PREFIX + "00";
    public static final String COMMON_ERROR_MSG = "系统处理失败";
    
    /**
     * 日程相关错误码: 10601xx
     */
    public static final String SCHEDULE_NOT_FOUND = PREFIX + "101";
    public static final String SCHEDULE_NOT_FOUND_MSG = "日程不存在";
    
    public static final String SCHEDULE_CREATE_FAILED = PREFIX + "102";
    public static final String SCHEDULE_CREATE_FAILED_MSG = "创建日程失败";
    
    public static final String SCHEDULE_UPDATE_FAILED = PREFIX + "103";
    public static final String SCHEDULE_UPDATE_FAILED_MSG = "更新日程失败";
    
    public static final String SCHEDULE_DELETE_FAILED = PREFIX + "104";
    public static final String SCHEDULE_DELETE_FAILED_MSG = "删除日程失败";
    
    public static final String SCHEDULE_TIME_INVALID = PREFIX + "105";
    public static final String SCHEDULE_TIME_INVALID_MSG = "日程时间无效";
    
    public static final String SCHEDULE_CONFLICT = PREFIX + "106";
    public static final String SCHEDULE_CONFLICT_MSG = "日程时间冲突";
    
    public static final String SCHEDULE_PERMISSION_DENIED = PREFIX + "107";
    public static final String SCHEDULE_PERMISSION_DENIED_MSG = "无权操作该日程";
    
    /**
     * 参与者相关错误码: 10602xx
     */
    public static final String PARTICIPANT_NOT_FOUND = PREFIX + "201";
    public static final String PARTICIPANT_NOT_FOUND_MSG = "参与者不存在";
    
    public static final String PARTICIPANT_ADD_FAILED = PREFIX + "202";
    public static final String PARTICIPANT_ADD_FAILED_MSG = "添加参与者失败";
    
    public static final String PARTICIPANT_REMOVE_FAILED = PREFIX + "203";
    public static final String PARTICIPANT_REMOVE_FAILED_MSG = "移除参与者失败";
    
    public static final String PARTICIPANT_RESPONSE_FAILED = PREFIX + "204";
    public static final String PARTICIPANT_RESPONSE_FAILED_MSG = "响应日程邀请失败";
    
    /**
     * 提醒相关错误码: 10603xx
     */
    public static final String REMINDER_NOT_FOUND = PREFIX + "301";
    public static final String REMINDER_NOT_FOUND_MSG = "提醒不存在";
    
    public static final String REMINDER_ADD_FAILED = PREFIX + "302";
    public static final String REMINDER_ADD_FAILED_MSG = "添加提醒失败";
    
    public static final String REMINDER_UPDATE_FAILED = PREFIX + "303";
    public static final String REMINDER_UPDATE_FAILED_MSG = "更新提醒失败";
    
    public static final String REMINDER_DELETE_FAILED = PREFIX + "304";
    public static final String REMINDER_DELETE_FAILED_MSG = "删除提醒失败";
    
    public static final String REMINDER_SEND_FAILED = PREFIX + "305";
    public static final String REMINDER_SEND_FAILED_MSG = "发送提醒失败";
    
    /**
     * 会议室相关错误码: 10604xx
     */
    public static final String MEETING_ROOM_NOT_FOUND = PREFIX + "401";
    public static final String MEETING_ROOM_NOT_FOUND_MSG = "会议室不存在";
    
    public static final String MEETING_ROOM_CREATE_FAILED = PREFIX + "402";
    public static final String MEETING_ROOM_CREATE_FAILED_MSG = "创建会议室失败";
    
    public static final String MEETING_ROOM_UPDATE_FAILED = PREFIX + "403";
    public static final String MEETING_ROOM_UPDATE_FAILED_MSG = "更新会议室失败";
    
    public static final String MEETING_ROOM_DELETE_FAILED = PREFIX + "404";
    public static final String MEETING_ROOM_DELETE_FAILED_MSG = "删除会议室失败";
    
    public static final String MEETING_ROOM_UNAVAILABLE = PREFIX + "405";
    public static final String MEETING_ROOM_UNAVAILABLE_MSG = "会议室不可用";
    
    public static final String MEETING_ROOM_CAPACITY_EXCEEDED = PREFIX + "406";
    public static final String MEETING_ROOM_CAPACITY_EXCEEDED_MSG = "超出会议室容量";
    
    /**
     * 会议室预约相关错误码: 10605xx
     */
    public static final String RESERVATION_NOT_FOUND = PREFIX + "501";
    public static final String RESERVATION_NOT_FOUND_MSG = "预约不存在";
    
    public static final String RESERVATION_CREATE_FAILED = PREFIX + "502";
    public static final String RESERVATION_CREATE_FAILED_MSG = "创建预约失败";
    
    public static final String RESERVATION_UPDATE_FAILED = PREFIX + "503";
    public static final String RESERVATION_UPDATE_FAILED_MSG = "更新预约失败";
    
    public static final String RESERVATION_DELETE_FAILED = PREFIX + "504";
    public static final String RESERVATION_DELETE_FAILED_MSG = "删除预约失败";
    
    public static final String RESERVATION_APPROVE_FAILED = PREFIX + "505";
    public static final String RESERVATION_APPROVE_FAILED_MSG = "审批预约失败";
    
    public static final String RESERVATION_REJECT_FAILED = PREFIX + "506";
    public static final String RESERVATION_REJECT_FAILED_MSG = "拒绝预约失败";
    
    public static final String RESERVATION_CONFLICT = PREFIX + "507";
    public static final String RESERVATION_CONFLICT_MSG = "会议室预约时间冲突";
    
    /**
     * 关联关系相关错误码: 10606xx
     */
    public static final String RELATION_CREATE_FAILED = PREFIX + "601";
    public static final String RELATION_CREATE_FAILED_MSG = "创建关联关系失败";
    
    public static final String RELATION_DELETE_FAILED = PREFIX + "602";
    public static final String RELATION_DELETE_FAILED_MSG = "删除关联关系失败";
    
    public static final String CASE_NOT_FOUND = PREFIX + "603";
    public static final String CASE_NOT_FOUND_MSG = "案件不存在";
    
    public static final String TASK_NOT_FOUND = PREFIX + "604";
    public static final String TASK_NOT_FOUND_MSG = "任务不存在";
} 