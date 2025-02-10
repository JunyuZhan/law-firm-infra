package com.lawfirm.staff.model.request.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "会议日程创建请求")
public class MeetingScheduleCreateRequest {

    @NotNull(message = "会议室ID不能为空")
    @Schema(description = "会议室ID")
    private Long roomId;

    @NotBlank(message = "会议主题不能为空")
    @Schema(description = "会议主题")
    private String subject;

    @Schema(description = "会议内容")
    private String content;

    @NotBlank(message = "开始时间不能为空")
    @Schema(description = "开始时间")
    private String startTime;

    @NotBlank(message = "结束时间不能为空")
    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "参会人员列表")
    private List<MeetingParticipant> participants;

    @Schema(description = "备注")
    private String remark;

    @Data
    @Schema(description = "会议参会人员")
    public static class MeetingParticipant {
        
        @NotNull(message = "参会人ID不能为空")
        @Schema(description = "参会人ID")
        private Long userId;

        @Schema(description = "参会角色")
        private Integer role;

        @Schema(description = "备注")
        private String remark;
    }
} 