package com.lawfirm.model.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务标签关联关系实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_task_tag_relation")
public class WorkTaskTagRelation extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableField("task_id")
    private Long taskId;
    
    /**
     * 标签ID
     */
    @TableField("tag_id")
    private Long tagId;
    
    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
}