package com.lawfirm.model.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.util.json.JsonUtils;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作任务实体类
 */
@Getter
@Accessors(chain = true)
@TableName("work_task")
@Schema(description = "工作任务实体类")
public class WorkTask extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务标题
     */
    @Schema(description = "任务标题")
    @TableField("title")
    private String title;

    /**
     * 任务描述
     */
    @Schema(description = "任务描述")
    @TableField("description")
    private String description;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态")
    @TableField("status")
    private Integer status;

    /**
     * 任务优先级
     */
    @Schema(description = "任务优先级")
    @TableField("priority")
    private Integer priority;

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
     * 负责人ID
     */
    @Schema(description = "负责人ID")
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 父任务ID
     */
    @Schema(description = "父任务ID")
    @TableField("parent_id")
    private Long parentId;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @TableField("tenant_id")
    private Long tenantId;

    /**
     * 案例ID
     */
    @Schema(description = "案例ID")
    @TableField("case_id")
    private Long caseId;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
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

    /**
     * 取消原因
     */
    @Schema(description = "取消原因")
    @TableField("cancel_reason")
    private String cancelReason;

    public WorkTask setTitle(String title) {
        this.title = title;
        return this;
    }

    public WorkTask setDescription(String description) {
        this.description = description;
        return this;
    }
    
    /**
     * 设置任务状态
     * 
     * @param status 任务状态值
     * @return 当前任务对象
     */
    public WorkTask setStatus(Integer status) {
        this.status = status;
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
    
    /**
     * 设置文档ID列表
     * 
     * @param documentIdList 文档ID列表
     * @return 当前任务对象
     */
    public WorkTask setDocumentIdList(List<Long> documentIdList) {
        if (documentIdList == null || documentIdList.isEmpty()) {
            this.documentIds = "[]";
        } else {
            this.documentIds = JsonUtils.toJsonString(documentIdList);
        }
        return this;
    }
    
    /**
     * 获取文档ID列表
     * 
     * @return 文档ID列表
     */
    public List<Long> getDocumentIdList() {
        if (documentIds == null || documentIds.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return JsonUtils.parseArray(documentIds, Long.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * 添加关联文档ID
     * 
     * @param documentId 文档ID
     * @return 当前任务对象
     */
    public WorkTask addDocumentId(Long documentId) {
        if (documentId == null) {
            return this;
        }
        
        List<Long> documentIdList = getDocumentIdList();
        if (!documentIdList.contains(documentId)) {
            documentIdList.add(documentId);
            setDocumentIdList(documentIdList);
        }
        return this;
    }
    
    /**
     * 移除关联文档ID
     * 
     * @param documentId 文档ID
     * @return 当前任务对象
     */
    public WorkTask removeDocumentId(Long documentId) {
        if (documentId == null) {
            return this;
        }
        
        List<Long> documentIdList = getDocumentIdList();
        documentIdList.remove(documentId);
        setDocumentIdList(documentIdList);
        return this;
    }

    public WorkTask setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public WorkTask setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
        return this;
    }
} 