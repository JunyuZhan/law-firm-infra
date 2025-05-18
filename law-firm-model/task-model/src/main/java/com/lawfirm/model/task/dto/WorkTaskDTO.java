package com.lawfirm.model.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 工作任务数据传输对象
 */
@Data
@Schema(description = "工作任务数据传输对象")
public class WorkTaskDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 任务ID
     */
    @Schema(description = "任务ID", example = "1")
    private Long id;
    
    /**
     * 任务标题
     */
    @NotBlank(message = "任务标题不能为空")
    @Size(min = 2, max = 100, message = "任务标题长度必须在2-100个字符之间")
    @Schema(description = "任务标题", requiredMode = io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED, example = "准备合同草稿")
    private String title;
    
    /**
     * 任务描述
     */
    @Size(max = 2000, message = "任务描述不能超过2000个字符")
    @Schema(description = "任务描述", example = "根据客户需求准备初版合同草稿")
    private String description;
    
    /**
     * 任务优先级：0-低，1-中，2-高
     */
    @Min(value = 0, message = "优先级最小为0")
    @Max(value = 2, message = "优先级最大为2")
    @Schema(description = "任务优先级：0-低，1-中，2-高", example = "1")
    private Integer priority;
    
    /**
     * 任务状态：0-待办，1-进行中，2-已完成，3-已取消
     */
    @Min(value = 0, message = "状态值最小为0")
    @Max(value = 3, message = "状态值最大为3")
    @Schema(description = "任务状态：0-待办，1-进行中，2-已完成，3-已取消", example = "0")
    private Integer status;
    
    /**
     * 任务开始时间
     */
    @Schema(description = "任务开始时间", example = "2025-01-01T09:00:00")
    private LocalDateTime startTime;
    
    /**
     * 任务结束时间
     */
    @Schema(description = "任务结束时间", example = "2025-01-10T18:00:00")
    private LocalDateTime endTime;
    
    /**
     * 任务负责人ID
     */
    @Schema(description = "任务负责人ID", example = "1001")
    private Long assigneeId;
    
    /**
     * 任务负责人姓名
     */
    @Schema(description = "任务负责人姓名", example = "张律师")
    private String assigneeName;
    
    /**
     * 任务创建人ID
     */
    @Schema(description = "任务创建人ID", example = "1002")
    private Long creatorId;
    
    /**
     * 任务创建人姓名
     */
    @Schema(description = "任务创建人姓名", example = "王经理")
    private String creatorName;
    
    /**
     * 父任务ID
     */
    @Schema(description = "父任务ID", example = "100")
    private Long parentId;
    
    /**
     * 任务分类ID
     */
    @Schema(description = "任务分类ID", example = "5")
    private Long categoryId;
    
    /**
     * 任务分类名称
     */
    @Schema(description = "任务分类名称", example = "合同审核")
    private String categoryName;
    
    /**
     * 是否提醒：0-否，1-是
     */
    @Min(value = 0, message = "是否提醒值最小为0")
    @Max(value = 1, message = "是否提醒值最大为1")
    @Schema(description = "是否提醒：0-否，1-是", example = "1")
    private Integer isRemind;
    
    /**
     * 提醒时间
     */
    @Schema(description = "提醒时间", example = "2025-01-05T09:00:00")
    private LocalDateTime remindTime;
    
    /**
     * 提醒方式：0-站内消息，1-邮件，2-短信
     */
    @Min(value = 0, message = "提醒方式值最小为0")
    @Max(value = 2, message = "提醒方式值最大为2")
    @Schema(description = "提醒方式：0-站内消息，1-邮件，2-短信", example = "0")
    private Integer remindType;
    
    /**
     * 任务进度（百分比）
     */
    @Min(value = 0, message = "进度最小为0")
    @Max(value = 100, message = "进度最大为100")
    @Schema(description = "任务进度（百分比）", example = "50")
    private Integer progress;
    
    /**
     * 预计工时（小时）
     */
    @DecimalMin(value = "0.0", message = "预计工时不能为负数")
    @Schema(description = "预计工时（小时）", example = "8.5")
    private Double estimatedHours;
    
    /**
     * 实际工时（小时）
     */
    @DecimalMin(value = "0.0", message = "实际工时不能为负数")
    @Schema(description = "实际工时（小时）", example = "10.5")
    private Double actualHours;
    
    /**
     * 关联案件ID
     */
    @Schema(description = "关联案件ID", example = "2001")
    private Long caseId;
    
    /**
     * 关联合同ID
     */
    @Schema(description = "关联合同ID", example = "3001")
    private Long contractId;
    
    /**
     * 关联文档ID
     */
    @Schema(description = "关联文档ID", example = "4001")
    private Long documentId;
    
    /**
     * 租户ID
     */
    @Schema(description = "租户ID", example = "1")
    private Long tenantId;
    
    /**
     * 任务标签列表
     */
    @Schema(description = "任务标签列表")
    private List<WorkTaskTagDTO> tags;
    
    /**
     * 任务评论列表
     */
    @Schema(description = "任务评论列表")
    private List<WorkTaskCommentDTO> comments;
    
    /**
     * 任务附件列表
     */
    @Schema(description = "任务附件列表")
    private List<WorkTaskAttachmentDTO> attachments;
    
    /**
     * 子任务列表
     */
    @Schema(description = "子任务列表")
    private List<WorkTaskDTO> subTasks;
    
    /**
     * 案例名称（只读）
     */
    @Schema(description = "案例名称（只读）", example = "张三诉李四合同纠纷案")
    private String caseName;
    
    /**
     * 客户ID
     */
    @Schema(description = "客户ID", example = "5001")
    private Long clientId;
    
    /**
     * 客户名称（只读）
     */
    @Schema(description = "客户名称（只读）", example = "ABC有限公司")
    private String clientName;
    
    /**
     * 日程ID
     */
    @Schema(description = "日程ID", example = "6001")
    private Long scheduleId;
    
    /**
     * 是否法律专业任务
     */
    @Schema(description = "是否法律专业任务", example = "true")
    private Boolean isLegalTask;
    
    /**
     * 关联文档ID列表
     */
    @Schema(description = "关联文档ID列表")
    private List<Long> documentIds;
    
    /**
     * 所属部门ID
     */
    @Schema(description = "所属部门ID", example = "10")
    private Long departmentId;
    
    /**
     * 所属部门名称（只读）
     */
    @Schema(description = "所属部门名称（只读）", example = "知识产权部")
    private String departmentName;
    
    /**
     * 任务负责人头像
     */
    @Schema(description = "任务负责人头像", example = "https://example.com/avatar/123.jpg")
    private String assigneeAvatar;
}