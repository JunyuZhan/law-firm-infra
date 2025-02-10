package com.lawfirm.staff.model.response.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "会议室可用时间响应")
public class MeetingRoomAvailableTimeResponse {

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "是否可用")
    private Boolean available;

    @Schema(description = "不可用原因")
    private String unavailableReason;

    @Schema(description = "当前预订人")
    private String bookedBy;

    @Schema(description = "当前预订人ID")
    private Long bookedById;

    @Schema(description = "当前会议主题")
    private String meetingSubject;
}