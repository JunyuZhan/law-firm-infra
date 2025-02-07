package com.lawfirm.staff.model.request.finance.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建财务任务请求")
public class TaskCreateRequest {

    @NotBlank(message = "任务标题不能为空")
    @Schema(description = "任务标题")
    private String title;

    @Schema(description = "任务描述")
    private String description;

    @NotNull(message = "任务类型不能为空")
    @Schema(description = "任务类型")
    private Integer type;

    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "负责人ID")
    private Long assigneeId;

    @Schema(description = "截止时间")
    private String deadline;

    @Schema(description = "备注")
    private String remark;
} 