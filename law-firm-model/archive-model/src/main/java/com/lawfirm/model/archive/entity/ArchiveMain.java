package com.lawfirm.model.archive.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 档案主表实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("archive_main")
@Schema(description = "档案主表实体")
public class ArchiveMain extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 档案编号
     */
    @Schema(description = "档案编号")
    @TableField("archive_no")
    private String archiveNo;

    /**
     * 来源类型（1-案件，2-合同，3-文档，4-行政）
     */
    @Schema(description = "来源类型（1-案件，2-合同，3-文档，4-行政）")
    @TableField("source_type")
    private Integer sourceType;

    /**
     * 来源ID
     */
    @Schema(description = "来源ID")
    @TableField("source_id")
    private Long sourceId;

    /**
     * 档案标题
     */
    @Schema(description = "档案标题")
    @TableField("title")
    private String title;

    /**
     * 归档时间
     */
    @Schema(description = "归档时间")
    @TableField("archive_time")
    private String archiveTime;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    @TableField("department_id")
    private Long departmentId;

    /**
     * 经办人ID
     */
    @Schema(description = "经办人ID")
    @TableField("handler_id")
    private Long handlerId;

    /**
     * 经办人姓名
     */
    @Schema(description = "经办人姓名")
    @TableField("handler_name")
    private String handlerName;

    /**
     * 档案状态（1-已归档，2-已同步，3-已销毁）
     */
    @Schema(description = "档案状态")
    @TableField("status")
    private Integer status;

    /**
     * 同步时间
     */
    @Schema(description = "同步时间")
    @TableField("sync_time")
    private String syncTime;

    /**
     * 是否已同步（0-未同步，1-已同步）
     */
    @Schema(description = "是否已同步")
    @TableField("is_synced")
    private Integer isSynced;

    /**
     * 归档数据（JSON格式）
     */
    @Schema(description = "归档数据（JSON格式）")
    @TableField("archive_data")
    private String archiveData;

    /**
     * 关键词
     */
    @Schema(description = "关键词")
    @TableField("keywords")
    private String keywords;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
} 