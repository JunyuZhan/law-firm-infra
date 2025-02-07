package com.lawfirm.staff.model.request.clerk.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "任务创建请求")
public class TaskCreateRequest {

    /**
     * 任务标题
     */
    @NotBlank(message = "任务标题不能为空")
    @Schema(description = "任务标题")
    private String title;

    /**
     * 任务内容
     */
    @NotBlank(message = "任务内容不能为空")
    @Schema(description = "任务内容")
    private String content;

    /**
     * 优先级（0：普通，1：重要，2：紧急）
     */
    @NotNull(message = "优先级不能为空")
    @Schema(description = "优先级（0普通 1重要 2紧急）")
    private Integer priority;

    /**
     * 截止时间
     */
    @Schema(description = "截止时间")
    private LocalDateTime deadline;

    /**
     * 负责人ID
     */
    @NotNull(message = "负责人不能为空")
    @Schema(description = "负责人ID")
    private Long assigneeId;

    /**
     * 部门ID
     */
    @NotNull(message = "部门不能为空")
    @Schema(description = "部门ID")
    private Long departmentId;
} 