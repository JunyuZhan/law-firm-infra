package com.lawfirm.model.schedule.constant;

/**
 * 会议室错误码常量类
 */
public class MeetingRoomErrorCode {
    
    /**
     * 会议室不存在
     */
    public static final String MEETING_ROOM_NOT_EXIST = "MEETING_ROOM_NOT_EXIST";
    
    /**
     * 会议室已被预订
     */
    public static final String MEETING_ROOM_ALREADY_BOOKED = "MEETING_ROOM_ALREADY_BOOKED";
    
    /**
     * 会议室不可用
     */
    public static final String MEETING_ROOM_UNAVAILABLE = "MEETING_ROOM_UNAVAILABLE";
    
    /**
     * 会议室容量不足
     */
    public static final String MEETING_ROOM_CAPACITY_NOT_ENOUGH = "MEETING_ROOM_CAPACITY_NOT_ENOUGH";
    
    /**
     * 预订时间冲突
     */
    public static final String BOOKING_TIME_CONFLICT = "BOOKING_TIME_CONFLICT";
    
    /**
     * 预订不存在
     */
    public static final String BOOKING_NOT_EXIST = "BOOKING_NOT_EXIST";
    
    /**
     * 无权操作该预订
     */
    public static final String BOOKING_NO_PERMISSION = "BOOKING_NO_PERMISSION";
    
    /**
     * 预订时间无效
     */
    public static final String BOOKING_TIME_INVALID = "BOOKING_TIME_INVALID";
    
    /**
     * 预订已取消
     */
    public static final String BOOKING_ALREADY_CANCELLED = "BOOKING_ALREADY_CANCELLED";
    
    /**
     * 预订已完成
     */
    public static final String BOOKING_ALREADY_COMPLETED = "BOOKING_ALREADY_COMPLETED";
    
    /**
     * 预订未确认
     */
    public static final String BOOKING_NOT_CONFIRMED = "BOOKING_NOT_CONFIRMED";
    
    /**
     * 预订开始时间已过
     */
    public static final String BOOKING_START_TIME_PASSED = "BOOKING_START_TIME_PASSED";
    
    /**
     * 设备不可用
     */
    public static final String EQUIPMENT_UNAVAILABLE = "EQUIPMENT_UNAVAILABLE";
    
    /**
     * 会议室超出预订时间
     */
    public static final String MEETING_ROOM_BOOKING_OVERTIME = "MEETING_ROOM_BOOKING_OVERTIME";
    
    /**
     * 创建预订失败
     */
    public static final String BOOKING_CREATE_FAILED = "BOOKING_CREATE_FAILED";
    
    /**
     * 更新预订失败
     */
    public static final String BOOKING_UPDATE_FAILED = "BOOKING_UPDATE_FAILED";
    
    /**
     * 取消预订失败
     */
    public static final String BOOKING_CANCEL_FAILED = "BOOKING_CANCEL_FAILED";
} 