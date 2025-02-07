package com.lawfirm.staff.model.request.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "工时分页查询请求")
public class TimesheetPageRequest {

    @Schema(description = "页码")
    private Integer pageNum = 1;

    @Schema(description = "每页条数") 
    private Integer pageSize = 10;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "工作类型")
    private Integer type;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "开始日期")
    private String startDate;

    @Schema(description = "结束日期")
    private String endDate;
} 