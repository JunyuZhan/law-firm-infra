package com.lawfirm.model.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 日历DTO
 */
@Data
@Schema(description = "日历DTO")
public class ScheduleCalendarDTO {

    @Schema(description = "日历名称")
    @NotBlank(message = "日历名称不能为空")
    private String name;

    @Schema(description = "日历描述")
    private String description;

    @Schema(description = "颜色（十六进制颜色码）")
    private String color;

    @Schema(description = "日历类型：1-个人日历，2-共享日历，3-订阅日历")
    @NotNull(message = "日历类型不能为空")
    private Integer type;

    @Schema(description = "可见性：1-私密，2-共享给特定人，3-公开")
    @NotNull(message = "可见性不能为空")
    private Integer visibility;

    @Schema(description = "是否默认日历")
    private Boolean isDefault;
} 