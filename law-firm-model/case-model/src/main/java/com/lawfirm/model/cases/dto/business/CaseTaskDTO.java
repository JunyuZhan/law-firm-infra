package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.task.TaskPriorityEnum;
import com.lawfirm.model.cases.enums.task.TaskStatusEnum;
import com.lawfirm.model.cases.enums.task.TaskTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件任务数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseTaskDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 任务类型
     */
    private TaskTypeEnum taskType;

    /**
     * 任务状态
     */
    private TaskStatusEnum taskStatus;

    /**
     * 任务优先级
     */
    private TaskPriorityEnum taskPriority;

    /**
     * 任务描述
     */
    private String taskDescription;

    /**
     * 开始时间
     */
    private transient LocalDateTime startTime;

    /**
     * 截止时间
     */
    private transient LocalDateTime deadline;

    /**
     * 完成时间
     */
    private transient LocalDateTime completionTime;

    /**
     * 预计工时（小时）
     */
    private Double estimatedHours;

    /**
     * 实际工时（小时）
     */
    private Double actualHours;

    /**
     * 负责人ID
     */
    private Long assigneeId;

    /**
     * 负责人姓名
     */
    private String assigneeName;

    /**
     * 协作人IDs（逗号分隔）
     */
    private String collaboratorIds;

    /**
     * 协作人姓名（逗号分隔）
     */
    private String collaboratorNames;

    /**
     * 父任务ID
     */
    private Long parentTaskId;

    /**
     * 进度百分比（0-100）
     */
    private Integer progressPercentage;

    /**
     * 是否需要审核
     */
    private Boolean needReview;

    /**
     * 审核人ID
     */
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    /**
     * 审核意见
     */
    private String reviewOpinion;

    /**
     * 是否需要提醒
     */
    private Boolean needReminder;

    /**
     * 提醒时间
     */
    private transient LocalDateTime reminderTime;

    /**
     * 关联文档IDs（逗号分隔）
     */
    private String documentIds;

    /**
     * 关联事件IDs（逗号分隔）
     */
    private String eventIds;

    /**
     * 标签（逗号分隔）
     */
    private String tags;

    /**
     * 备注
     */
    private String remarks;
} 