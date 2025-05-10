package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 档案同步记录DTO
 */
@Data
@Schema(description = "档案同步记录DTO")
public class ArchiveSyncRecordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @Schema(description = "记录ID")
    private String id;

    /**
     * 档案ID
     */
    @Schema(description = "档案ID")
    private String archiveId;

    /**
     * 档案编号
     */
    @Schema(description = "档案编号")
    private String archiveNo;

    /**
     * 同步时间
     */
    @Schema(description = "同步时间")
    private LocalDateTime syncTime;

    /**
     * 同步状态（0-失败，1-成功）
     */
    @Schema(description = "同步状态（0-失败，1-成功）")
    private Integer syncStatus;

    /**
     * 同步系统代码
     */
    @Schema(description = "同步系统代码")
    private String syncSystemCode;

    /**
     * 同步系统名称
     */
    @Schema(description = "同步系统名称")
    private String syncSystemName;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    private String errorMessage;

    /**
     * 同步数据快照
     */
    @Schema(description = "同步数据快照")
    private String syncDataSnapshot;
} 