package com.lawfirm.model.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 外部日历账号DTO
 */
@Data
@Schema(description = "外部日历账号DTO")
public class ExternalCalendarAccountDTO {

    @Schema(description = "账号类型")
    @NotBlank(message = "账号类型不能为空")
    private String type;

    @Schema(description = "访问令牌")
    @NotBlank(message = "访问令牌不能为空")
    private String accessToken;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "令牌过期时间")
    private LocalDateTime expireTime;
} 