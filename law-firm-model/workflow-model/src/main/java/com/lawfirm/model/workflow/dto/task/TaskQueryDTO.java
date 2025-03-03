package com.lawfirm.model.workflow.dto.task;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 任务查询DTO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TaskQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

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
     * 流程ID
     */
    private Long processId;

    /**
     * 流程实例编号
     */
    private String processNo;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 处理人类型 1-用户 2-角色 3-部门
     */
    private Integer handlerType;

    /**
     * 任务状态 0-待处理 1-处理中 2-已完成 3-已取消
     */
    private Integer status;

    /**
     * 优先级 1-低 2-中 3-高
     */
    private Integer priority;

    /**
     * 开始时间-起始
     */
    private String startTimeBegin;

    /**
     * 开始时间-结束
     */
    private String startTimeEnd;

    /**
     * 结束时间-起始
     */
    private String endTimeBegin;

    /**
     * 结束时间-结束
     */
    private String endTimeEnd;

    /**
     * 截止时间-起始
     */
    private String dueTimeBegin;

    /**
     * 截止时间-结束
     */
    private String dueTimeEnd;
} 