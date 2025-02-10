package com.lawfirm.staff.model.request.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "会议室可用时间请求")
public class MeetingRoomAvailableTimeRequest {

    @NotNull(message = "日期不能为空")
    @Schema(description = "日期")
    private String date;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "时间段（分钟）")
    private Integer duration;
}