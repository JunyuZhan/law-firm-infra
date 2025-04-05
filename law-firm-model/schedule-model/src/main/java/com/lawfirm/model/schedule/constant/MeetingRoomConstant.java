package com.lawfirm.model.schedule.constant;

/**
 * 会议室常量类
 */
public class MeetingRoomConstant {
    
    /**
     * 会议室状态：可用
     */
    public static final int STATUS_AVAILABLE = 1;
    
    /**
     * 会议室状态：维护中
     */
    public static final int STATUS_MAINTENANCE = 2;
    
    /**
     * 会议室状态：已预订
     */
    public static final int STATUS_BOOKED = 3;
    
    /**
     * 会议室状态：不可用
     */
    public static final int STATUS_UNAVAILABLE = 4;
    
    /**
     * 会议室类型：小型会议室
     */
    public static final int TYPE_SMALL = 1;
    
    /**
     * 会议室类型：中型会议室
     */
    public static final int TYPE_MEDIUM = 2;
    
    /**
     * 会议室类型：大型会议室
     */
    public static final int TYPE_LARGE = 3;
    
    /**
     * 会议室类型：VIP会议室
     */
    public static final int TYPE_VIP = 4;
    
    /**
     * 设备类型：投影仪
     */
    public static final int EQUIPMENT_PROJECTOR = 1;
    
    /**
     * 设备类型：视频会议系统
     */
    public static final int EQUIPMENT_VIDEO_CONF = 2;
    
    /**
     * 设备类型：白板
     */
    public static final int EQUIPMENT_WHITEBOARD = 3;
    
    /**
     * 设备类型：音响系统
     */
    public static final int EQUIPMENT_AUDIO = 4;
    
    /**
     * 预订类型：内部会议
     */
    public static final int BOOKING_TYPE_INTERNAL = 1;
    
    /**
     * 预订类型：客户会议
     */
    public static final int BOOKING_TYPE_CLIENT = 2;
    
    /**
     * 预订类型：培训
     */
    public static final int BOOKING_TYPE_TRAINING = 3;
    
    /**
     * 预订类型：其他
     */
    public static final int BOOKING_TYPE_OTHER = 4;
    
    /**
     * 预订状态：待确认
     */
    public static final int BOOKING_STATUS_PENDING = 1;
    
    /**
     * 预订状态：已确认
     */
    public static final int BOOKING_STATUS_CONFIRMED = 2;
    
    /**
     * 预订状态：已取消
     */
    public static final int BOOKING_STATUS_CANCELLED = 3;
    
    /**
     * 预订状态：已完成
     */
    public static final int BOOKING_STATUS_COMPLETED = 4;
} 