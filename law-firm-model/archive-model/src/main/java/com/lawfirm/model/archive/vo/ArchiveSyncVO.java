package com.lawfirm.model.archive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 档案同步VO
 */
@Data
@Schema(description = "档案同步VO")
public class ArchiveSyncVO {

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
     * 档案标题
     */
    @Schema(description = "档案标题")
    private String archiveTitle;

    /**
     * 同步时间
     */
    @Schema(description = "同步时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime syncTime;

    /**
     * 同步状态编码
     */
    @Schema(description = "同步状态编码")
    private Integer syncStatusCode;
    
    /**
     * 同步状态名称
     */
    @Schema(description = "同步状态名称")
    private String syncStatusName;

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
     * 同步用户ID
     */
    @Schema(description = "同步用户ID")
    private String syncUserId;
    
    /**
     * 同步用户名称
     */
    @Schema(description = "同步用户名称")
    private String syncUserName;
    
    /**
     * 同步方式（1-手动，2-自动）
     */
    @Schema(description = "同步方式")
    private Integer syncMode;
    
    /**
     * 同步方式描述
     */
    @Schema(description = "同步方式描述")
    private String syncModeDesc;
    
    /**
     * 重试次数
     */
    @Schema(description = "重试次数")
    private Integer retryCount;
} 