package com.lawfirm.model.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 任务标签实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_task_tag")
@Schema(description = "任务标签实体类")
public class WorkTaskTag extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    @TableField("name")
    private String name;

    /**
     * 标签描述
     */
    @Schema(description = "标签描述")
    @TableField("description")
    private String description;

    /**
     * 标签颜色
     */
    @Schema(description = "标签颜色")
    @TableField("color")
    private String color;

    /**
     * 使用次数
     */
    @Schema(description = "使用次数")
    @TableField("usage_count")
    private Integer usageCount;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @TableField("tenant_id")
    private Long tenantId;
} 