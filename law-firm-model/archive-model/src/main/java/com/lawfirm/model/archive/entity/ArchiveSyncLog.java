package com.lawfirm.model.archive.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 档案同步日志实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("archive_sync_log")
@Schema(description = "档案同步日志实体")
public class ArchiveSyncLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 同步批次号
     */
    @Schema(description = "同步批次号")
    @TableField("batch_no")
    private String batchNo;

    /**
     * 同步时间
     */
    @Schema(description = "同步时间")
    @TableField("sync_time")
    private LocalDateTime syncTime;

    /**
     * 同步用户
     */
    @Schema(description = "同步用户")
    @TableField("sync_user")
    private String syncUser;

    /**
     * 目标系统
     */
    @Schema(description = "目标系统")
    @TableField("target_system")
    private String targetSystem;

    /**
     * 同步档案数量
     */
    @Schema(description = "同步档案数量")
    @TableField("archive_count")
    private Integer archiveCount;

    /**
     * 状态(1:成功 0:失败)
     */
    @Schema(description = "状态(1:成功 0:失败)")
    @TableField("status")
    private Integer status;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField("created_time")
    private LocalDateTime createdTime;
} 