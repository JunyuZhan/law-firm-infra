package com.lawfirm.model.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 任务实体类
 * 
 * @author JunyuZhan
 */
@Data
@TableName(value = "wf_task")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Task extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    @TableField(value = "task_name")
    private String taskName;

    /**
     * 任务类型
     */
    @TableField(value = "task_type")
    private Integer taskType;

    /**
     * 任务描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 流程实例ID
     */
    @TableField(value = "process_instance_id")
    private String processInstanceId;

    /**
     * 处理人ID
     */
    @TableField(value = "handler_id")
    private Long handlerId;

    /**
     * 处理人名称
     */
    @TableField(value = "handler_name")
    private String handlerName;

    /**
     * 优先级
     */
    @TableField(value = "priority")
    private Integer priority;

    /**
     * 截止时间
     */
    @TableField(value = "due_date")
    private LocalDateTime dueDate;

    /**
     * 任务状态
     * 0-待处理
     * 1-处理中
     * 2-已完成
     * 3-已取消
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 处理结果
     */
    @TableField(value = "result")
    private String result;

    /**
     * 处理意见
     */
    @TableField(value = "comment")
    private String comment;
} 
