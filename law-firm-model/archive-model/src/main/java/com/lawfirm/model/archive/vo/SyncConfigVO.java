package com.lawfirm.model.archive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 同步配置VO
 */
@Data
@Schema(description = "同步配置VO")
public class SyncConfigVO {

    /**
     * 配置ID
     */
    @Schema(description = "配置ID")
    private String id;
    
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
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Boolean enabled;
    
    /**
     * 状态描述
     */
    @Schema(description = "状态描述")
    private String statusDesc;

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
     * 最近同步时间
     */
    @Schema(description = "最近同步时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSyncTime;
    
    /**
     * 同步状态（1-正常，0-异常）
     */
    @Schema(description = "同步状态")
    private Integer syncStatus;
    
    /**
     * 同步状态描述
     */
    @Schema(description = "同步状态描述")
    private String syncStatusDesc;
    
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createBy;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updateBy;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
} 