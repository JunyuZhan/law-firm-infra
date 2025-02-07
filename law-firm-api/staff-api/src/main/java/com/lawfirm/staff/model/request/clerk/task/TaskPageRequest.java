package com.lawfirm.staff.model.request.clerk.task;

import com.lawfirm.staff.model.base.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "任务分页查询请求")
public class TaskPageRequest extends PageQuery {

    @Schema(description = "任务标题")
    private String title;

    @Schema(description = "任务类型")
    private Integer type;

    @Schema(description = "任务状态")
    private Integer status;

    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "负责人ID")
    private Long assigneeId;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "部门ID")
    private Long departmentId;
} 