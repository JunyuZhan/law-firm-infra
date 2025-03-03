package com.lawfirm.model.cases.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.enums.task.TaskPriorityEnum;
import com.lawfirm.model.cases.enums.task.TaskStatusEnum;
import com.lawfirm.model.cases.enums.task.TaskTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * 案件任务实体类
 * 
 * 案件任务记录了与案件相关的各类任务信息，包括任务基本信息、
 * 时间信息、执行人信息、进度信息、审核信息等。
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case_task")
public class CaseTask extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 案件编号
     */
    @TableField("case_number")
    private String caseNumber;

    /**
     * 任务标题
     */
    @TableField("task_title")
    private String taskTitle;

    /**
     * 任务类型
     */
    @TableField("task_type")
    private Integer taskType;

    /**
     * 任务状态
     */
    @TableField("task_status")
    private Integer taskStatus;

    /**
     * 任务优先级
     */
    @TableField("task_priority")
    private Integer taskPriority;

    /**
     * 任务描述
     */
    @TableField("task_description")
    private String taskDescription;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private transient LocalDateTime startTime;

    /**
     * 截止时间
     */
    @TableField("deadline")
    private transient LocalDateTime deadline;

    /**
     * 完成时间
     */
    @TableField("completion_time")
    private transient LocalDateTime completionTime;

    /**
     * 预计工时（小时）
     */
    @TableField("estimated_hours")
    private Double estimatedHours;

    /**
     * 实际工时（小时）
     */
    @TableField("actual_hours")
    private Double actualHours;

    /**
     * 创建人ID
     */
    @TableField("creator_id")
    private Long creatorId;

    /**
     * 创建人姓名
     */
    @TableField("creator_name")
    private String creatorName;

    /**
     * 负责人ID
     */
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 负责人姓名
     */
    @TableField("assignee_name")
    private String assigneeName;

    /**
     * 协作人IDs（逗号分隔）
     */
    @TableField("collaborator_ids")
    private String collaboratorIds;

    /**
     * 协作人姓名（逗号分隔）
     */
    @TableField("collaborator_names")
    private String collaboratorNames;

    /**
     * 父任务ID
     */
    @TableField("parent_task_id")
    private Long parentTaskId;

    /**
     * 进度百分比（0-100）
     */
    @TableField("progress_percentage")
    private Integer progressPercentage;

    /**
     * 是否需要审核
     */
    @TableField("need_review")
    private Boolean needReview;

    /**
     * 审核人ID
     */
    @TableField("reviewer_id")
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    @TableField("reviewer_name")
    private String reviewerName;

    /**
     * 审核时间
     */
    @TableField("review_time")
    private transient LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    @TableField("review_opinion")
    private String reviewOpinion;

    /**
     * 是否需要提醒
     */
    @TableField("need_reminder")
    private Boolean needReminder;

    /**
     * 提醒时间
     */
    @TableField("reminder_time")
    private transient LocalDateTime reminderTime;

    /**
     * 是否已提醒
     */
    @TableField("is_reminded")
    private Boolean isReminded;

    /**
     * 关联文档IDs（逗号分隔）
     */
    @TableField("document_ids")
    private String documentIds;

    /**
     * 关联事件IDs（逗号分隔）
     */
    @TableField("event_ids")
    private String eventIds;

    /**
     * 标签（逗号分隔）
     */
    @TableField("tags")
    private String tags;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 获取任务类型枚举
     */
    public TaskTypeEnum getTaskTypeEnum() {
        return TaskTypeEnum.valueOf(this.taskType);
    }

    /**
     * 设置任务类型
     */
    public CaseTask setTaskTypeEnum(TaskTypeEnum taskTypeEnum) {
        this.taskType = taskTypeEnum != null ? taskTypeEnum.getValue() : null;
        return this;
    }

    /**
     * 获取任务状态枚举
     */
    public TaskStatusEnum getTaskStatusEnum() {
        return TaskStatusEnum.valueOf(this.taskStatus);
    }

    /**
     * 设置任务状态
     */
    public CaseTask setTaskStatusEnum(TaskStatusEnum taskStatusEnum) {
        this.taskStatus = taskStatusEnum != null ? taskStatusEnum.getValue() : null;
        return this;
    }

    /**
     * 获取任务优先级枚举
     */
    public TaskPriorityEnum getTaskPriorityEnum() {
        return TaskPriorityEnum.valueOf(this.taskPriority);
    }

    /**
     * 设置任务优先级
     */
    public CaseTask setTaskPriorityEnum(TaskPriorityEnum taskPriorityEnum) {
        this.taskPriority = taskPriorityEnum != null ? taskPriorityEnum.getValue() : null;
        return this;
    }

    /**
     * 判断任务是否已完成
     */
    public boolean isCompleted() {
        return this.taskStatus != null && 
               this.getTaskStatusEnum() == TaskStatusEnum.COMPLETED;
    }

    /**
     * 判断任务是否已取消
     */
    public boolean isCancelled() {
        return this.taskStatus != null && 
               this.getTaskStatusEnum() == TaskStatusEnum.CANCELLED;
    }

    /**
     * 判断任务是否进行中
     */
    public boolean isInProgress() {
        return this.taskStatus != null && 
               this.getTaskStatusEnum() == TaskStatusEnum.IN_PROGRESS;
    }

    /**
     * 判断任务是否待处理
     */
    public boolean isPending() {
        return this.taskStatus != null && 
               this.getTaskStatusEnum() == TaskStatusEnum.PENDING_ASSIGNMENT;
    }

    /**
     * 判断任务是否已逾期
     */
    public boolean isOverdue() {
        return !isCompleted() && !isCancelled() && 
               deadline != null && LocalDateTime.now().isAfter(deadline);
    }

    /**
     * 获取任务剩余时间（小时）
     */
    public Double getRemainingHours() {
        if (isCompleted() || isCancelled() || deadline == null) {
            return 0.0;
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(deadline)) {
            return 0.0;
        }
        
        return Duration.between(now, deadline).toHours() / 24.0;
    }

    /**
     * 获取任务剩余天数
     */
    public Long getRemainingDays() {
        if (isCompleted() || isCancelled() || deadline == null) {
            return 0L;
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(deadline)) {
            return 0L;
        }
        
        return Duration.between(now, deadline).toDays();
    }

    /**
     * 获取任务进度百分比
     */
    public Integer getProgressPercentage() {
        if (this.progressPercentage != null) {
            return this.progressPercentage;
        }
        
        if (isCompleted()) {
            return 100;
        } else if (isCancelled()) {
            return 0;
        } else if (isPending()) {
            return 0;
        } else if (isInProgress()) {
            return 50; // 默认进行中为50%
        }
        
        return 0;
    }

    /**
     * 更新任务进度
     */
    public CaseTask updateProgress(Integer progressPercentage) {
        if (progressPercentage == null) {
            return this;
        }
        
        // 确保进度在0-100之间
        this.progressPercentage = Math.max(0, Math.min(100, progressPercentage));
        
        // 如果进度为100%，自动将状态设为已完成
        if (this.progressPercentage == 100 && !isCompleted()) {
            setTaskStatusEnum(TaskStatusEnum.COMPLETED);
            this.completionTime = LocalDateTime.now();
        }
        
        return this;
    }

    /**
     * 获取协作人ID数组
     */
    public Long[] getCollaboratorIdArray() {
        if (collaboratorIds == null || collaboratorIds.isEmpty()) {
            return new Long[0];
        }
        
        String[] idStrings = collaboratorIds.split(",");
        Long[] ids = new Long[idStrings.length];
        
        for (int i = 0; i < idStrings.length; i++) {
            try {
                ids[i] = Long.parseLong(idStrings[i].trim());
            } catch (NumberFormatException e) {
                ids[i] = null;
            }
        }
        
        return ids;
    }

    /**
     * 获取协作人姓名数组
     */
    public String[] getCollaboratorNameArray() {
        if (collaboratorNames == null || collaboratorNames.isEmpty()) {
            return new String[0];
        }
        
        return collaboratorNames.split(",");
    }

    /**
     * 添加协作人
     */
    public CaseTask addCollaborator(Long id, String name) {
        if (id == null) {
            return this;
        }
        
        // 处理协作人ID
        if (collaboratorIds == null || collaboratorIds.isEmpty()) {
            collaboratorIds = id.toString();
        } else if (!collaboratorIds.contains(id.toString())) {
            collaboratorIds += "," + id;
        }
        
        // 处理协作人姓名
        if (name != null && !name.isEmpty()) {
            if (collaboratorNames == null || collaboratorNames.isEmpty()) {
                collaboratorNames = name;
            } else if (!collaboratorNames.contains(name)) {
                collaboratorNames += "," + name;
            }
        }
        
        return this;
    }

    /**
     * 移除协作人
     */
    public CaseTask removeCollaborator(Long id) {
        if (id == null || collaboratorIds == null || collaboratorIds.isEmpty()) {
            return this;
        }
        
        Long[] ids = getCollaboratorIdArray();
        String[] names = getCollaboratorNameArray();
        
        StringBuilder newIds = new StringBuilder();
        StringBuilder newNames = new StringBuilder();
        
        for (int i = 0; i < ids.length; i++) {
            if (!id.equals(ids[i])) {
                if (newIds.length() > 0) {
                    newIds.append(",");
                }
                newIds.append(ids[i]);
                
                if (i < names.length) {
                    if (newNames.length() > 0) {
                        newNames.append(",");
                    }
                    newNames.append(names[i]);
                }
            }
        }
        
        collaboratorIds = newIds.toString();
        collaboratorNames = newNames.toString();
        
        return this;
    }

    /**
     * 获取标签数组
     */
    public String[] getTagArray() {
        if (tags == null || tags.isEmpty()) {
            return new String[0];
        }
        
        return tags.split(",");
    }

    /**
     * 添加标签
     */
    public CaseTask addTag(String tag) {
        if (tag == null || tag.isEmpty()) {
            return this;
        }
        
        if (tags == null || tags.isEmpty()) {
            tags = tag;
        } else if (!tags.contains(tag)) {
            tags += "," + tag;
        }
        
        return this;
    }

    /**
     * 移除标签
     */
    public CaseTask removeTag(String tag) {
        if (tag == null || tag.isEmpty() || tags == null || tags.isEmpty()) {
            return this;
        }
        
        String[] tagArray = getTagArray();
        StringBuilder newTags = new StringBuilder();
        
        for (String t : tagArray) {
            if (!tag.equals(t)) {
                if (newTags.length() > 0) {
                    newTags.append(",");
                }
                newTags.append(t);
            }
        }
        
        tags = newTags.toString();
        
        return this;
    }
} 