package com.lawfirm.model.task.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.task.dto.WorkTaskTagDTO;
import com.lawfirm.model.task.enums.WorkTaskPriorityEnum;
import com.lawfirm.model.task.enums.WorkTaskStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 工作任务展示对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkTaskVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 任务标题
     */
    private String title;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 任务状态枚举
     */
    private transient WorkTaskStatusEnum statusEnum;
    
    /**
     * 任务优先级枚举
     */
    private transient WorkTaskPriorityEnum priority;
    
    /**
     * 任务优先级码
     */
    private Integer priorityCode;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 负责人ID
     */
    private Long assigneeId;
    
    /**
     * 负责人姓名
     */
    private String assigneeName;
    
    /**
     * 创建人姓名
     */
    private String creatorName;
    
    /**
     * 父任务ID
     */
    private Long parentId;
    
    /**
     * 父任务标题
     */
    private String parentTitle;
    
    /**
     * 标签列表
     */
    private transient List<WorkTaskTagDTO> tags;
    
    /**
     * 评论数量
     */
    private Integer commentCount;
    
    /**
     * 附件数量
     */
    private Integer attachmentCount;
    
    /**
     * 子任务数量
     */
    private Integer subTaskCount;
    
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    
    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;
    
    /**
     * 取消原因
     */
    private String cancelReason;
    
    /**
     * 获取状态枚举
     */
    @JsonIgnore
    public WorkTaskStatusEnum getStatusEnum() {
        if (statusEnum == null && getStatus() != null) {
            statusEnum = WorkTaskStatusEnum.getByCode(getStatus());
        }
        return statusEnum;
    }
    
    /**
     * 设置状态枚举
     */
    public void setStatusEnum(WorkTaskStatusEnum statusEnum) {
        this.statusEnum = statusEnum;
        if (statusEnum != null) {
            setStatus(statusEnum.getCode());
        }
    }
    
    /**
     * 获取优先级枚举
     */
    @JsonIgnore
    public WorkTaskPriorityEnum getPriority() {
        if (priority == null && priorityCode != null) {
            priority = WorkTaskPriorityEnum.getByCode(priorityCode);
        }
        return priority;
    }
    
    /**
     * 设置优先级枚举
     */
    public void setPriority(WorkTaskPriorityEnum priority) {
        this.priority = priority;
        if (priority != null) {
            this.priorityCode = priority.getCode();
        }
    }
    
    /**
     * 获取标签列表
     */
    public List<WorkTaskTagDTO> getTags() {
        return tags;
    }
    
    /**
     * 设置标签列表
     */
    public void setTags(List<WorkTaskTagDTO> tags) {
        this.tags = tags;
    }
} 