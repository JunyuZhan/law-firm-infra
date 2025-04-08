package com.lawfirm.model.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务标签实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_task_tag")
public class WorkTaskTag extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @TableField("name")
    private String name;

    /**
     * 标签描述
     */
    @TableField("description")
    private String description;

    /**
     * 标签颜色
     */
    @TableField("color")
    private String color;

    /**
     * 使用次数
     */
    @TableField("usage_count")
    private Integer usageCount;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
} 