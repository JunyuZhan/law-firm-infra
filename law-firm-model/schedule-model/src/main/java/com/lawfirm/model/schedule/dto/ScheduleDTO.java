package com.lawfirm.model.schedule.dto;

import com.lawfirm.model.schedule.entity.enums.PriorityLevel;
import com.lawfirm.model.schedule.entity.enums.ScheduleStatus;
import com.lawfirm.model.schedule.entity.enums.ScheduleType;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程DTO类
 */
@Data
public class ScheduleDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 日程标题
     */
    @NotBlank(message = "日程标题不能为空")
    private String title;
    
    /**
     * 日程内容/描述
     */
    private String content;
    
    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;
    
    /**
     * 是否全天事项
     */
    private Boolean allDay;
    
    /**
     * 地点
     */
    private String location;
    
    /**
     * 日程类型（会议、任务、约见、法庭出庭等）
     */
    @NotNull(message = "日程类型不能为空")
    private ScheduleType type;
    
    /**
     * 优先级（高、中、低）
     */
    private PriorityLevel priority;
    
    /**
     * 状态（计划中、进行中、已完成、已取消）
     */
    private ScheduleStatus status;
    
    /**
     * 所有者用户ID
     */
    private Long ownerId;
    
    /**
     * 是否私密日程
     */
    private Boolean isPrivate;
    
    /**
     * 参与者列表
     */
    private transient List<ScheduleParticipantDTO> participants;
    
    /**
     * 提醒设置列表
     */
    private transient List<ScheduleReminderDTO> reminders;
    
    /**
     * 关联的案件ID列表
     */
    private transient List<Long> caseIds;
    
    /**
     * 关联的单个案件ID（便于单个关联操作）
     */
    private Long caseId;
    
    /**
     * 关联的任务ID列表
     */
    private transient List<Long> taskIds;
    
    /**
     * 关联的单个任务ID（便于单个关联操作）
     */
    private Long taskId;
    
    /**
     * 会议室ID（如果是会议类型）
     */
    private Long meetingRoomId;
} 