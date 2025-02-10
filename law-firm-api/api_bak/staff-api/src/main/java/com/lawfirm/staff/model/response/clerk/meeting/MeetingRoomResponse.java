package com.lawfirm.staff.model.response.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "会议室响应")
public class MeetingRoomResponse {

    @Schema(description = "会议室ID")
    private Long id;

    @Schema(description = "会议室名称")
    private String name;

    @Schema(description = "会议室描述")
    private String description;

    @Schema(description = "会议室类型")
    private Integer type;

    @Schema(description = "会议室类型名称")
    private String typeName;

    @Schema(description = "会议室状态")
    private Integer status;

    @Schema(description = "会议室状态名称")
    private String statusName;

    @Schema(description = "容纳人数")
    private Integer capacity;

    @Schema(description = "楼层")
    private Integer floor;

    @Schema(description = "位置说明")
    private String location;

    @Schema(description = "是否有投影设备")
    private Boolean hasProjector;

    @Schema(description = "是否有视频会议设备")
    private Boolean hasVideoConference;

    @Schema(description = "设备说明")
    private String equipment;

    @Schema(description = "使用说明")
    private String instructions;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;
} 