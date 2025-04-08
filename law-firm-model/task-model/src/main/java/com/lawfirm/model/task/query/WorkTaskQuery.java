package com.lawfirm.model.task.query;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 工作任务查询条件
 */
@Data
public class WorkTaskQuery {
    
    /**
     * 任务标题（模糊匹配）
     */
    private String title;
    
    /**
     * 任务状态
     */
    private Integer status;
    
    /**
     * 任务优先级
     */
    private Integer priority;
    
    /**
     * 任务类型
     */
    private Integer type;
    
    /**
     * 创建人ID
     */
    private Long creatorId;
    
    /**
     * 负责人ID
     */
    private Long assigneeId;
    
    /**
     * 开始日期（开始）
     */
    private LocalDateTime startTimeBegin;
    
    /**
     * 开始日期（结束）
     */
    private LocalDateTime startTimeEnd;
    
    /**
     * 截止日期（开始）
     */
    private LocalDateTime endTimeBegin;
    
    /**
     * 截止日期（结束）
     */
    private LocalDateTime endTimeEnd;
    
    /**
     * 标签ID
     */
    private Long tagId;
    
    /**
     * 父任务ID
     */
    private Long parentId;
    
    /**
     * 是否包含子任务
     */
    private Boolean includeSubTasks;
    
    /**
     * 案例ID
     */
    private Long caseId;
    
    /**
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 是否法律专业任务
     */
    private Boolean isLegalTask;
    
    /**
     * 部门ID
     */
    private Long departmentId;
    
    /**
     * 页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页条数
     */
    private Integer pageSize = 10;
    
    /**
     * 排序方式
     */
    private String orderBy;
} 