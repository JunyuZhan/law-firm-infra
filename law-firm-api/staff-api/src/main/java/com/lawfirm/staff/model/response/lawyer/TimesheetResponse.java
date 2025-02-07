package com.lawfirm.staff.model.response.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "工时响应")
public class TimesheetResponse {

    @Schema(description = "工时ID")
    private Long id;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "关联案件名称")
    private String matterName;

    @Schema(description = "工作内容")
    private String content;

    @Schema(description = "工作时长(小时)")
    private BigDecimal hours;

    @Schema(description = "工作日期")
    private String workDate;

    @Schema(description = "工作类型")
    private Integer type;

    @Schema(description = "工作类型名称")
    private String typeName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "创建人姓名")
    private String creatorName;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;
} 