package com.lawfirm.staff.model.request.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "会议日程冲突检查请求")
public class MeetingScheduleCheckConflictRequest {

    @NotNull(message = "会议室ID不能为空")
    @Schema(description = "会议室ID")
    private Long roomId;

    @NotNull(message = "开始时间不能为空")
    @Schema(description = "开始时间")
    private String startTime;

    @NotNull(message = "结束时间不能为空")
    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "当前会议日程ID（更新时使用）")
    private Long currentScheduleId;
} 