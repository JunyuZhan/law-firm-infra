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
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * 日程实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_schedule")
@Schema(description = "日程实体类")
public class Schedule extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 日程标题
     */
    @Schema(description = "日程标题")
    @TableField("title")
    private String title;
    
    /**
     * 日程内容/描述
     */
    @Schema(description = "日程内容/描述")
    @TableField("content")
    private String content;
    
    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;
    
    /**
     * 是否全天事项
     */
    @Schema(description = "是否全天事项")
    @TableField("all_day")
    private Boolean allDay;
    
    /**
     * 地点
     */
    @Schema(description = "地点")
    @TableField("location")
    private String location;
    
    /**
     * 日程类型（会议、任务、约见、法庭出庭等）
     */
    @Schema(description = "日程类型（会议、任务、约见、法庭出庭等）")
    @TableField("type")
    private Integer type;
    
    /**
     * 优先级（高、中、低）
     */
    @Schema(description = "优先级（高、中、低）")
    @TableField("priority")
    private Integer priority;
    
    /**
     * 状态（计划中、进行中、已完成、已取消）
     */
    @Schema(description = "状态（计划中、进行中、已完成、已取消）")
    @TableField("schedule_status")
    private Integer status;
    
    /**
     * 所有者用户ID
     */
    @Schema(description = "所有者用户ID")
    @TableField("owner_id")
    private Long ownerId;
    
    /**
     * 是否私密日程
     */
    @Schema(description = "是否私密日程")
    @TableField("is_private")
    private Boolean isPrivate;
    
    /**
     * 会议室ID（如果是会议类型）
     */
    @Schema(description = "会议室ID（如果是会议类型）")
    @TableField("meeting_room_id")
    private Long meetingRoomId;
} 