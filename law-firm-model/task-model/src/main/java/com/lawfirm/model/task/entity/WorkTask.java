package com.lawfirm.model.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 工作任务实体类
 */
@Getter
@Accessors(chain = true)
@TableName("work_task")
public class WorkTask extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务标题
     */
    @TableField("title")
    private String title;

    /**
     * 任务描述
     */
    @TableField("description")
    private String description;

    /**
     * 任务状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 任务优先级
     */
    @TableField("priority")
    private Integer priority;

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
     * 负责人ID
     */
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 父任务ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    /**
     * 案例ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 客户ID
     */
    @TableField("client_id")
    private Long clientId;

    /**
     * 日程ID
     */
    @TableField("schedule_id")
    private Long scheduleId;

    /**
     * 是否法律专业任务
     */
    @TableField("is_legal_task")
    private Boolean isLegalTask;

    /**
     * 关联文档ID列表（JSON数组）
     */
    @TableField("document_ids")
    private String documentIds;

    /**
     * 所属部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    public WorkTask setTitle(String title) {
        this.title = title;
        return this;
    }

    public WorkTask setDescription(String description) {
        this.description = description;
        return this;
    }

    public WorkTask setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public WorkTask setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public WorkTask setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public WorkTask setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
        return this;
    }

    public WorkTask setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public WorkTask setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public WorkTask setCaseId(Long caseId) {
        this.caseId = caseId;
        return this;
    }

    public WorkTask setClientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public WorkTask setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
        return this;
    }

    public WorkTask setIsLegalTask(Boolean isLegalTask) {
        this.isLegalTask = isLegalTask;
        return this;
    }

    public WorkTask setDocumentIds(String documentIds) {
        this.documentIds = documentIds;
        return this;
    }

    public WorkTask setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }
} 