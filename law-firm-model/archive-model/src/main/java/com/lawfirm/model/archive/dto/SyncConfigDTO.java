package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 同步配置DTO
 */
@Data
@Schema(description = "同步配置DTO")
public class SyncConfigDTO {

    /**
     * 外部系统编码
     */
    @Schema(description = "外部系统编码")
    private String systemCode;

    /**
     * 外部系统名称
     */
    @Schema(description = "外部系统名称")
    private String systemName;

    /**
     * 同步接口URL
     */
    @Schema(description = "同步接口URL")
    private String syncUrl;

    /**
     * 认证令牌
     */
    @Schema(description = "认证令牌")
    private String authToken;

    /**
     * 是否启用（0-禁用，1-启用）
     */
    @Schema(description = "是否启用（0-禁用，1-启用）")
    private Integer enabled;

    /**
     * 同步频率（分钟）
     */
    @Schema(description = "同步频率（分钟）")
    private Integer syncFrequency;

    /**
     * 超时时间（秒）
     */
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