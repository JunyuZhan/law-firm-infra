package com.lawfirm.staff.model.request.clerk.meeting;

import com.lawfirm.common.data.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "会议日程分页查询请求")
public class MeetingSchedulePageRequest extends PageQuery {

    @Schema(description = "会议室ID")
    private Long roomId;

    @Schema(description = "会议主题")
    private String subject;

    @Schema(description = "会议状态")
    private Integer status;

    @Schema(description = "预订人ID")
    private Long bookedById;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "创建开始时间")
    private String createTimeStart;

    @Schema(description = "创建结束时间")
    private String createTimeEnd;
} 