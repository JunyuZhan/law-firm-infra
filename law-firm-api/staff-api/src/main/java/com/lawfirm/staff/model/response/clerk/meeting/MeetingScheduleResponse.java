package com.lawfirm.staff.model.response.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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

@Data
@Schema(description = "会议参会人员响应")
class MeetingParticipantResponse {

    @Schema(description = "参会人ID")
    private Long userId;

    @Schema(description = "参会人姓名")
    private String userName;

    @Schema(description = "参会角色")
    private Integer role;

    @Schema(description = "参会角色名称")
    private String roleName;

    @Schema(description = "备注")
    private String remark;
} 