package com.lawfirm.staff.model.response.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.ApiModel;
import io.swagger.v3.oas.annotations.media.ApiModelProperty;

import java.util.List;

@Data
@Schema(description = "会议日程响应")
public class MeetingScheduleResponse {

    @Schema(description = "会议日程ID")
    private Long id;

    @Schema(description = "会议室ID")
    private Long roomId;

    @Schema(description = "会议室名称")
    private String roomName;

    @Schema(description = "会议主题")
    private String subject;

    @Schema(description = "会议内容")
    private String content;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "会议状态")
    private Integer status;

    @Schema(description = "会议状态名称")
    private String statusName;

    @Schema(description = "预订人ID")
    private Long bookedById;

    @Schema(description = "预订人姓名")
    private String bookedByName;

    @Schema(description = "参会人员列表")
    private List<MeetingParticipantResponse> participants;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;

    @Schema(description = "备注")
    private String remark;
} 