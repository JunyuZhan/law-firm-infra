package com.lawfirm.model.workflow.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 任务视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TaskVO extends BaseVO {

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务类型
     */
    private Integer taskType;

    /**
     * 任务类型名称
     */
    private String taskTypeName;

    /**
     * 流程ID
     */
    private Long processId;

    /**
     * 流程实例编号
     */
    private String processNo;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 处理人名称
     */
    private String handlerName;

    /**
     * 处理人类型 1-用户 2-角色 3-部门
     */
    private Integer handlerType;

    /**
     * 处理人类型名称
     */
    private String handlerTypeName;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 截止时间
     */
    private LocalDateTime dueTime;

    /**
     * 任务状态 0-待处理 1-处理中 2-已完成 3-已取消
     */
    private Integer status;

    /**
     * 任务状态名称
     */
    private String statusName;

    /**
     * 处理结果
     */
    private String result;

    /**
     * 处理意见
     */
    private String comment;

    /**
     * 优先级 1-低 2-中 3-高
     */
    private Integer priority;

    /**
     * 优先级名称
     */
    private String priorityName;

    /**
     * 前置任务ID
     */
    private String prevTaskIds;

    /**
     * 后置任务ID
     */
    private String nextTaskIds;

    /**
     * 任务配置（JSON格式）
     */
    private String taskConfig;
} 