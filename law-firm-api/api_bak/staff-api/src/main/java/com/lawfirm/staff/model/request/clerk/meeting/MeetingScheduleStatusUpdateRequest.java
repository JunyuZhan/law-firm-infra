package com.lawfirm.staff.model.request.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "会议日程状态更新请求")
public class MeetingScheduleStatusUpdateRequest {

    @NotNull(message = "会议日程状态不能为空")
    @Schema(description = "会议日程状态")
    private Integer status;

    @Schema(description = "状态说明")
    private String statusDescription;

    @Schema(description = "备注")
    private String remark;
}