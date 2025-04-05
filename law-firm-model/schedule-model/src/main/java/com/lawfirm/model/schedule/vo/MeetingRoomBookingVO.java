package com.lawfirm.model.schedule.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会议室预订视图对象
 */
@Data
public class MeetingRoomBookingVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 会议室ID
     */
    private Long meetingRoomId;
    
    /**
     * 会议室名称
     */
    private String meetingRoomName;
    
    /**
     * 会议室位置
     */
    private String meetingRoomLocation;
    
    /**
     * 会议室容量
     */
    private Integer meetingRoomCapacity;
    
    /**
     * 关联的日程ID
     */
    private Long scheduleId;
    
    /**
     * 预订人ID
     */
    private Long userId;
    
    /**
     * 预订人姓名
     */
    private String userName;
    
    /**
     * 预订人部门
     */
    private String userDepartment;
    
    /**
     * 会议标题
     */
    private String title;
    
    /**
     * 会议描述
     */
    private String description;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 预订类型
     */
    private Integer bookingType;
    
    /**
     * 预订类型名称
     */
    private String bookingTypeName;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;
    
    /**
     * 参与人数
     */
    private Integer attendeesCount;
    
    /**
     * 预订备注
     */
    private String remarks;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 设备列表
     */
    private String facilities;
    
    /**
     * 是否可以取消
     */
    private Boolean canCancel;
    
    /**
     * 是否可以修改
     */
    private Boolean canModify;
} 