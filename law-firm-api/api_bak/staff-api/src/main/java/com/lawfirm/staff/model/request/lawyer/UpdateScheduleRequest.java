package com.lawfirm.staff.model.request.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "更新日程请求")
public class UpdateScheduleRequest {

    @Schema(description = "日程ID")
    @NotNull(message = "日程ID不能为空")
    private Long id;

    @Schema(description = "日程标题")
    @NotBlank(message = "日程标题不能为空")
    private String title;

    @Schema(description = "日程内容")
    private String content;

    @Schema(description = "日程类型")
    @NotNull(message = "日程类型不能为空")
    private Integer type;

    @Schema(description = "开始时间")
    @NotBlank(message = "开始时间不能为空")
    private String startTime;

    @Schema(description = "结束时间")
    @NotBlank(message = "结束时间不能为空")
    private String endTime;

    @Schema(description = "提醒时间")
    private String remindTime;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "参与人ID列表")
    private List<Long> participantIds;
} 