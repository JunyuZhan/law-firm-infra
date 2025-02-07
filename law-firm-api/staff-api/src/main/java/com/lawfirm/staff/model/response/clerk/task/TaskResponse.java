package com.lawfirm.staff.model.response.clerk.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "任务响应")
public class TaskResponse {

    @Schema(description = "任务ID")
    private Long id;

    @Schema(description = "任务标题")
    private String title;

    @Schema(description = "任务内容")
    private String content;

    @Schema(description = "任务状态（0：待处理，1：处理中，2：已完成，3：已取消）")
    private Integer status;

    @Schema(description = "优先级（0：普通，1：重要，2：紧急）")
    private Integer priority;

    @Schema(description = "截止时间")
    private LocalDateTime deadline;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "创建人姓名")
    private String creatorName;

    @Schema(description = "负责人ID")
    private Long assigneeId;

    @Schema(description = "负责人姓名")
    private String assigneeName;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "部门名称")
    private String departmentName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 