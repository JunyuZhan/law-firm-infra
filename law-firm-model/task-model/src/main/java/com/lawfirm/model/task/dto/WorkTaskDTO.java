package com.lawfirm.model.task.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 工作任务数据传输对象
 */
@Data
public class WorkTaskDTO {
    
    /**
     * 任务ID
     */
    private Long id;
    
    /**
     * 任务标题
     */
    private String title;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 任务优先级：0-低，1-中，2-高
     */
    private Integer priority;
    
    /**
     * 任务状态：0-待办，1-进行中，2-已完成，3-已取消
     */
    private Integer status;
    
    /**
     * 任务开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 任务结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 任务负责人ID
     */
    private Long assigneeId;
    
    /**
     * 任务负责人姓名
     */
    private String assigneeName;
    
    /**
     * 任务创建人ID
     */
    private Long creatorId;
    
    /**
     * 任务创建人姓名
     */
    private String creatorName;
    
    /**
     * 父任务ID
     */
    private Long parentId;
    
    /**
     * 任务分类ID
     */
    private Long categoryId;
    
    /**
     * 任务分类名称
     */
    private String categoryName;
    
    /**
     * 是否提醒：0-否，1-是
     */
    private Integer isRemind;
    
    /**
     * 提醒时间
     */
    private LocalDateTime remindTime;
    
    /**
     * 提醒方式：0-站内消息，1-邮件，2-短信
     */
    private Integer remindType;
    
    /**
     * 任务进度（百分比）
     */
    private Integer progress;
    
    /**
     * 预计工时（小时）
     */
    private Double estimatedHours;
    
    /**
     * 实际工时（小时）
     */
    private Double actualHours;
    
    /**
     * 关联案件ID
     */
    private Long caseId;
    
    /**
     * 关联合同ID
     */
    private Long contractId;
    
    /**
     * 关联文档ID
     */
    private Long documentId;
    
    /**
     * 租户ID
     */
    private Long tenantId;
    
    /**
     * 任务标签列表
     */
    private List<WorkTaskTagDTO> tags;
    
    /**
     * 任务评论列表
     */
    private List<WorkTaskCommentDTO> comments;
    
    /**
     * 任务附件列表
     */
    private List<WorkTaskAttachmentDTO> attachments;
    
    /**
     * 子任务列表
     */
    private List<WorkTaskDTO> subTasks;
    
    /**
     * 案例名称（只读）
     */
    private String caseName;
    
    /**
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 客户名称（只读）
     */
    private String clientName;
    
    /**
     * 日程ID
     */
    private Long scheduleId;
    
    /**
     * 是否法律专业任务
     */
    private Boolean isLegalTask;
    
    /**
     * 关联文档ID列表
     */
    private List<Long> documentIds;
    
    /**
     * 所属部门ID
     */
    private Long departmentId;
    
    /**
     * 所属部门名称（只读）
     */
    private String departmentName;
}