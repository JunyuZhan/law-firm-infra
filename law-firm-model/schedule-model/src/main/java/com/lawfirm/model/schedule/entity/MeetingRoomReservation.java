package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.DataEntity;
import com.lawfirm.model.schedule.entity.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 会议室预约实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_meeting_room_reservation")
public class MeetingRoomReservation extends DataEntity {
    
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
     * 预约者ID
     */
    @TableField("reserver_id")
    private Long reserverId;
    
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
     * 会议主题
     */
    @TableField("subject")
    private String subject;
    
    /**
     * 预约用途
     */
    @TableField("purpose")
    private String purpose;
    
    /**
     * 参与人数
     */
    @TableField("attendee_count")
    private Integer attendeeCount;
    
    /**
     * 审核人ID
     */
    @TableField("reviewer_id")
    private Long reviewerId;
    
    /**
     * 审核意见
     */
    @TableField("review_comments")
    private String reviewComments;
    
    /**
     * 状态（待审核、已确认、已取消）
     */
    @TableField("status")
    private ReservationStatus status;
} 