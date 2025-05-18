package com.lawfirm.model.task.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * 工作任务查询条件
 */
@Data
@Schema(description = "工作任务查询条件")
public class WorkTaskQuery {
    
    /**
     * 任务标题（模糊匹配）
     */
    @Size(max = 100, message = "任务标题不能超过100个字符")
    @Schema(description = "任务标题（模糊匹配）", example = "合同")
    private String title;
    
    /**
     * 任务状态
     */
    @Min(value = 0, message = "状态值最小为0")
    @Max(value = 3, message = "状态值最大为3")
    @Schema(description = "任务状态：0-待办，1-进行中，2-已完成，3-已取消", example = "1")
    private Integer status;
    
    /**
     * 任务优先级
     */
    @Min(value = 0, message = "优先级最小为0")
    @Max(value = 2, message = "优先级最大为2")
    @Schema(description = "任务优先级：0-低，1-中，2-高", example = "2")
    private Integer priority;
    
    /**
     * 任务类型
     */
    @Schema(description = "任务类型", example = "1")
    private Integer type;
    
    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID", example = "1001")
    private Long creatorId;
    
    /**
     * 负责人ID
     */
    @Schema(description = "负责人ID", example = "1002")
    private Long assigneeId;
    
    /**
     * 开始日期（开始）
     */
    @Schema(description = "开始日期（开始）", example = "2025-01-01T00:00:00")
    private LocalDateTime startTimeBegin;
    
    /**
     * 开始日期（结束）
     */
    @Schema(description = "开始日期（结束）", example = "2025-01-31T23:59:59")
    private LocalDateTime startTimeEnd;
    
    /**
     * 截止日期（开始）
     */
    @Schema(description = "截止日期（开始）", example = "2025-02-01T00:00:00")
    private LocalDateTime endTimeBegin;
    
    /**
     * 截止日期（结束）
     */
    @Schema(description = "截止日期（结束）", example = "2025-02-28T23:59:59")
    private LocalDateTime endTimeEnd;
    
    /**
     * 标签ID
     */
    @Schema(description = "标签ID", example = "5")
    private Long tagId;
    
    /**
     * 父任务ID
     */
    @Schema(description = "父任务ID", example = "100")
    private Long parentId;
    
    /**
     * 是否包含子任务
     */
    @Schema(description = "是否包含子任务", example = "true")
    private Boolean includeSubTasks;
    
    /**
     * 案例ID
     */
    @Schema(description = "案例ID", example = "2001")
    private Long caseId;
    
    /**
     * 客户ID
     */
    @Schema(description = "客户ID", example = "3001")
    private Long clientId;
    
    /**
     * 是否法律专业任务
     */
    @Schema(description = "是否法律专业任务", example = "true")
    private Boolean isLegalTask;
    
    /**
     * 部门ID
     */
    @Schema(description = "部门ID", example = "10")
    private Long departmentId;
    
    /**
     * 页码
     */
    @Min(value = 1, message = "页码最小为1")
    @Schema(description = "页码", defaultValue = "1", example = "1")
    private Integer pageNum = 1;
    
    /**
     * 每页条数
     */
    @Min(value = 1, message = "每页条数最小为1")
    @Max(value = 100, message = "每页条数最大为100")
    @Schema(description = "每页条数", defaultValue = "10", example = "10")
    private Integer pageSize = 10;
    
    /**
     * 排序方式
     */
    @Pattern(regexp = "^[a-zA-Z0-9_]+(\\s+(ASC|DESC))?$", message = "排序方式格式不正确")
    @Schema(description = "排序方式，例如：createTime DESC", example = "createTime DESC")
    private String orderBy;
} 