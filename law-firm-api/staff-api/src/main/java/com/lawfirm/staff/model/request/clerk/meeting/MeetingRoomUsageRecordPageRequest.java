package com.lawfirm.staff.model.request.clerk.meeting;

import com.lawfirm.model.base.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "会议室使用记录分页查询请求")
public class MeetingRoomUsageRecordPageRequest extends PageQuery {

    @Schema(description = "会议室ID")
    private Long roomId;

    @Schema(description = "使用人ID")
    private Long userId;

    @Schema(description = "使用状态")
    private Integer status;

    @Schema(description = "使用开始时间")
    private String startTime;

    @Schema(description = "使用结束时间")
    private String endTime;

    @Schema(description = "创建开始时间")
    private String createTimeStart;

    @Schema(description = "创建结束时间")
    private String createTimeEnd;
} 