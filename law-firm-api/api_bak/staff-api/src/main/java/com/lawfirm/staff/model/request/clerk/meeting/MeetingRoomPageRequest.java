package com.lawfirm.staff.model.request.clerk.meeting;

import com.lawfirm.common.data.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "会议室分页查询请求")
public class MeetingRoomPageRequest extends PageQuery {

    @Schema(description = "会议室名称")
    private String name;

    @Schema(description = "会议室类型")
    private Integer type;

    @Schema(description = "会议室状态")
    private Integer status;

    @Schema(description = "容纳人数")
    private Integer capacity;

    @Schema(description = "楼层")
    private Integer floor;

    @Schema(description = "是否有投影设备")
    private Boolean hasProjector;

    @Schema(description = "是否有视频会议设备")
    private Boolean hasVideoConference;

    @Schema(description = "关键字")
    private String keyword;
} 