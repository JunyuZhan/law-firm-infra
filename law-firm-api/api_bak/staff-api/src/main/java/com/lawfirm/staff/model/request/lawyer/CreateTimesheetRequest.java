package com.lawfirm.staff.model.request.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "创建工时请求")
public class CreateTimesheetRequest {

    @Schema(description = "关联案件ID")
    @NotNull(message = "案件ID不能为空")
    private Long matterId;

    @Schema(description = "工作内容")
    @NotBlank(message = "工作内容不能为空")
    private String content;

    @Schema(description = "工作时长(小时)")
    @NotNull(message = "工作时长不能为空")
    private BigDecimal hours;

    @Schema(description = "工作日期")
    @NotBlank(message = "工作日期不能为空")
    private String workDate;

    @Schema(description = "工作类型")
    @NotNull(message = "工作类型不能为空")
    private Integer type;

    @Schema(description = "备注")
    private String remark;
} 