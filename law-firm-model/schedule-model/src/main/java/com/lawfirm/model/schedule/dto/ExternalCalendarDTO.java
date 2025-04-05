package com.lawfirm.model.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 外部日历DTO
 */
@Data
@Schema(description = "外部日历DTO")
public class ExternalCalendarDTO {

    @Schema(description = "日历名称")
    @NotBlank(message = "日历名称不能为空")
    private String name;

    @Schema(description = "日历描述")
    private String description;

    @Schema(description = "颜色（十六进制颜色码）")
    private String color;

    @Schema(description = "提供商类型：1-Google, 2-Microsoft, 3-Apple, 4-其他")
    @NotNull(message = "提供商类型不能为空")
    private Integer providerType;
    
    @Schema(description = "外部日历ID（提供商平台的ID）")
    private String externalId;
    
    @Schema(description = "访问令牌")
    private String accessToken;
    
    @Schema(description = "刷新令牌")
    private String refreshToken;
    
    @Schema(description = "状态：1-正常, 2-同步失败, 3-授权过期, 4-已暂停")
    private Integer status;
} 