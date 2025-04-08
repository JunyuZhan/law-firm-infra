package com.lawfirm.model.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务评论实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_task_comment")
public class WorkTaskComment extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableField("task_id")
    private Long taskId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 父评论ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 评论人ID
     */
    @TableField("commenter_id")
    private Long commenterId;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
} 