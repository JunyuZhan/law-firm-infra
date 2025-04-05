package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 会议室预订实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_meeting_room_booking")
public class MeetingRoomBooking extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 会议室ID
     */
    @TableField("meeting_room_id")
    private Long meetingRoomId;
    
    /**
     * 关联的日程ID
     */
    @TableField("schedule_id")
    private Long scheduleId;
    
    /**
     * 预订人ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 会议标题
     */
    @TableField("title")
    private String title;
    
    /**
     * 会议描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;
    
    /**
     * 预订类型
     */
    @TableField("booking_type")
    private Integer bookingType;
    
    /**
     * 状态（待审核、已确认、已取消、已完成）
     */
    @TableField("booking_status")
    private Integer status;
    
    /**
     * 参与人数
     */
    @TableField("attendees_count")
    private Integer attendeesCount;
    
    /**
     * 预订备注
     */
    @TableField("remarks")
    private String remarks;
} 