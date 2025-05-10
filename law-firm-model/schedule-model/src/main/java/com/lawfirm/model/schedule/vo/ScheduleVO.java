package com.lawfirm.model.schedule.vo;

import com.lawfirm.model.schedule.entity.enums.PriorityLevel;
import com.lawfirm.model.schedule.entity.enums.ScheduleStatus;
import com.lawfirm.model.schedule.entity.enums.ScheduleType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程VO类
 */
@Data
public class ScheduleVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 日程标题
     */
    private String title;
    
    /**
     * 日程内容/描述
     */
    private String content;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
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
    private ScheduleType type;
    
    /**
     * 日程类型描述
     */
    private String typeDesc;
    
    /**
     * 优先级（高、中、低）
     */
    private PriorityLevel priority;
    
    /**
     * 优先级描述
     */
    private String priorityDesc;
    
    /**
     * 状态（计划中、进行中、已完成、已取消）
     */
    private ScheduleStatus status;
    
    /**
     * 状态描述
     */
    private String statusDesc;
    
    /**
     * 所有者用户ID
     */
    private Long ownerId;
    
    /**
     * 所有者用户名
     */
    private String ownerName;
    
    /**
     * 所有者头像
     */
    private String ownerAvatar;
    
    /**
     * 所有者部门
     */
    private String ownerDepartment;
    
    /**
     * 是否私密日程
     */
    private Boolean isPrivate;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 参与者列表
     */
    private transient List<ScheduleParticipantVO> participants;
    
    /**
     * 提醒设置列表
     */
    private transient List<ScheduleReminderVO> reminders;
    
    /**
     * 关联的案件信息列表
     */
    private transient List<CaseInfoVO> caseList;
    
    /**
     * 关联的任务信息列表
     */
    private transient List<TaskInfoVO> taskList;
    
    /**
     * 会议室信息（如果是会议类型）
     */
    private transient MeetingRoomVO meetingRoom;
    
    /**
     * 案件关联列表
     */
    private transient List<ScheduleCaseRelationVO> caseRelations;
    
    /**
     * 任务关联列表
     */
    private transient List<ScheduleTaskRelationVO> taskRelations;
    
    /**
     * 案件信息VO
     */
    @Data
    public static class CaseInfoVO {
        private Long caseId;
        private String caseName;
        private String caseCode;
    }
    
    /**
     * 任务信息VO
     */
    @Data
    public static class TaskInfoVO {
        private Long taskId;
        private String taskName;
        private String taskCode;
    }
} 