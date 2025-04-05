package com.lawfirm.model.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.schedule.entity.enums.PriorityLevel;
import com.lawfirm.model.schedule.entity.enums.ScheduleStatus;
import com.lawfirm.model.schedule.entity.enums.ScheduleType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 日程实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_schedule")
public class Schedule extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 日程标题
     */
    @TableField("title")
    private String title;
    
    /**
     * 日程内容/描述
     */
    @TableField("content")
    private String content;
    
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
     * 是否全天事项
     */
    @TableField("all_day")
    private Boolean allDay;
    
    /**
     * 地点
     */
    @TableField("location")
    private String location;
    
    /**
     * 日程类型（会议、任务、约见、法庭出庭等）
     */
    @TableField("type")
    private ScheduleType type;
    
    /**
     * 优先级（高、中、低）
     */
    @TableField("priority")
    private PriorityLevel priority;
    
    /**
     * 状态（计划中、进行中、已完成、已取消）
     */
    @TableField("schedule_status")
    private ScheduleStatus scheduleStatus;
    
    /**
     * 所有者用户ID
     */
    @TableField("owner_id")
    private Long ownerId;
    
    /**
     * 是否私密日程
     */
    @TableField("is_private")
    private Boolean isPrivate;
    
    /**
     * 会议室ID（如果是会议类型）
     */
    @TableField("meeting_room_id")
    private Long meetingRoomId;
} 