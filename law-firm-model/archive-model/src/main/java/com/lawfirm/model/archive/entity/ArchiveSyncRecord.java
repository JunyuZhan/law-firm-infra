package com.lawfirm.model.archive.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * 档案同步记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("archive_sync_record")
@Schema(description = "档案同步记录实体类")
public class ArchiveSyncRecord extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 档案ID
     */
    @Schema(description = "档案ID")
    @TableField("archive_id")
    private String archiveId;

    /**
     * 档案编号
     */
    @Schema(description = "档案编号")
    @TableField("archive_no")
    private String archiveNo;

    /**
     * 同步时间
     */
    @Schema(description = "同步时间")
    @TableField("sync_time")
    private LocalDateTime syncTime;

    /**
     * 同步状态（0-失败，1-成功）
     */
    @Schema(description = "同步状态（0-失败，1-成功）")
    @TableField("sync_status")
    private Integer syncStatus;

    /**
     * 同步系统代码
     */
    @Schema(description = "同步系统代码")
    @TableField("sync_system_code")
    private String syncSystemCode;

    /**
     * 同步系统名称
     */
    @Schema(description = "同步系统名称")
    @TableField("sync_system_name")
    private String syncSystemName;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    @TableField("error_message")
    private String errorMessage;

    /**
     * 同步数据快照
     */
    @Schema(description = "同步数据快照")
    @TableField("sync_data_snapshot")
    private String syncDataSnapshot;
} 