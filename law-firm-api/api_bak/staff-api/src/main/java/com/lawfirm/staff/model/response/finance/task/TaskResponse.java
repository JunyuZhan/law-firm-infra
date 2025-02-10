package com.lawfirm.staff.model.response.finance.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "财务任务响应")
public class TaskResponse {

    @Schema(description = "任务ID")
    private Long id;

    @Schema(description = "任务标题")
    private String title;

    @Schema(description = "任务描述")
    private String description;

    @Schema(description = "任务类型")
    private Integer type;

    @Schema(description = "任务状态")
    private Integer status;

    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "负责人ID")
    private Long assigneeId;

    @Schema(description = "负责人姓名")
    private String assigneeName;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "创建人姓名")
    private String creatorName;

    @Schema(description = "截止时间")
    private String deadline;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;
} 