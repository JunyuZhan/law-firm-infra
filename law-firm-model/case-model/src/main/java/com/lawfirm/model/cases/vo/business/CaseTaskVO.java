package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.task.TaskPriorityEnum;
import com.lawfirm.model.cases.enums.task.TaskStatusEnum;
import com.lawfirm.model.cases.enums.task.TaskTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * 案件任务视图对象
 * 
 * 包含任务的基本信息，如任务标题、类型、状态、时间等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseTaskVO extends BaseVO {

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
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

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
     * 审核时间
     */
    private transient LocalDateTime reviewTime;

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
     * 是否已提醒
     */
    private Boolean isReminded;

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

    /**
     * 判断任务是否已完成
     */
    public boolean isCompleted() {
        return this.taskStatus != null && 
               this.taskStatus == TaskStatusEnum.COMPLETED;
    }

    /**
     * 判断任务是否已取消
     */
    public boolean isCancelled() {
        return this.taskStatus != null && 
               this.taskStatus == TaskStatusEnum.CANCELLED;
    }

    /**
     * 判断任务是否进行中
     */
    public boolean isInProgress() {
        return this.taskStatus != null && 
               this.taskStatus == TaskStatusEnum.IN_PROGRESS;
    }

    /**
     * 判断任务是否待处理
     */
    public boolean isPending() {
        return this.taskStatus != null && 
               this.taskStatus == TaskStatusEnum.PENDING_ASSIGNMENT;
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
    public CaseTaskVO addCollaborator(Long id, String name) {
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
    public CaseTaskVO removeCollaborator(Long id) {
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
    public CaseTaskVO addTag(String tag) {
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
    public CaseTaskVO removeTag(String tag) {
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