package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 同步配置创建DTO
 */
@Data
@Schema(description = "同步配置创建DTO")
public class SyncConfigCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 外部系统编码
     */
    @NotBlank(message = "外部系统编码不能为空")
    @Schema(description = "外部系统编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String systemCode;

    /**
     * 外部系统名称
     */
    @NotBlank(message = "外部系统名称不能为空")
    @Schema(description = "外部系统名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String systemName;

    /**
     * 同步接口URL
     */
    @NotBlank(message = "同步接口URL不能为空")
    @Schema(description = "同步接口URL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String syncUrl;

    /**
     * 认证令牌
     */
    @Schema(description = "认证令牌")
    private String authToken;

    /**
     * 是否启用（0-禁用，1-启用）
     */
    @NotNull(message = "启用状态不能为空")
    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer enabled;

    /**
     * 同步频率（分钟）
     */
    @Positive(message = "同步频率必须大于0")
    @Schema(description = "同步频率（分钟）")
    private Integer syncFrequency;

    /**
     * 超时时间（秒）
     */
    @Positive(message = "超时时间必须大于0")
    @Schema(description = "超时时间（秒）")
    private Integer timeout;

    /**
     * 重试次数
     */
    @Schema(description = "重试次数")
    private Integer retryCount;

    /**
     * 最大批量数量
     */
    @Schema(description = "最大批量数量")
    private Integer maxBatchSize;
} 