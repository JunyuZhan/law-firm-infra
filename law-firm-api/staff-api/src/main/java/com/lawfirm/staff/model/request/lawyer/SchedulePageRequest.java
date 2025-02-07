package com.lawfirm.staff.model.request.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "日程分页查询请求")
public class SchedulePageRequest {

    @Schema(description = "页码")
    private Integer pageNum = 1;

    @Schema(description = "每页条数") 
    private Integer pageSize = 10;

    @Schema(description = "日程标题")
    private String title;

    @Schema(description = "日程类型")
    private Integer type;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;
} 