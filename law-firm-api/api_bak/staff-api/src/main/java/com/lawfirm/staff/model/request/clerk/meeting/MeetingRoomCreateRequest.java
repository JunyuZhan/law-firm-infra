package com.lawfirm.staff.model.request.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "会议室创建请求")
public class MeetingRoomCreateRequest {

    @NotBlank(message = "会议室名称不能为空")
    @Schema(description = "会议室名称")
    private String name;

    @Schema(description = "会议室描述")
    private String description;

    @NotNull(message = "会议室类型不能为空")
    @Schema(description = "会议室类型")
    private Integer type;

    @NotNull(message = "容纳人数不能为空")
    @Schema(description = "容纳人数")
    private Integer capacity;

    @NotNull(message = "楼层不能为空")
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
} 