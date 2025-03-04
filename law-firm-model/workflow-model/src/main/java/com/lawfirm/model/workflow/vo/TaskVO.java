package com.lawfirm.model.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.workflow.enums.TaskPriorityEnum;
import com.lawfirm.model.workflow.enums.TaskStatusEnum;
import com.lawfirm.model.workflow.enums.TaskTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 * 任务视图对象
 * 用于前端展示和交互的任务数据结构
 *
 * @author JunyuZhan
 */
@Data
@Accessors(chain = true)
public class TaskVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private Long id;
    
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 任务类型
     * @see TaskTypeEnum
     */
    private TaskTypeEnum taskType;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 任务状�?     * @see TaskStatusEnum
     */
    private TaskStatusEnum status;
    
    /**
     * 流程实例ID
     * 关联ProcessVO.id
     */
    private String processInstanceId;
    
    /**
     * 流程定义ID
     * 流程定义的唯一标识
     */
    private String processDefinitionId;
    
    /**
     * 工作流引擎任务ID
     * @deprecated 考虑移除，实现细节不应暴露在VO�?     */
    @Deprecated
    private String flowableTaskId;
    
    /**
     * 处理人ID
     * 关联用户表的ID
     */
    private Long handlerId;
    
    /**
     * 处理人名�?     */
    private String handlerName;
    
    /**
     * 任务优先�?     * @see TaskPriorityEnum
     */
    private TaskPriorityEnum priority;
    
    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;
    
    /**
     * 处理结果
     * 任务完成时的结果说明
     */
    private String result;
    
    /**
     * 处理意见
     * 任务处理过程中的备注或说�?     */
    private String comment;
    
    /**
     * 是否超时
     * true: 已超�?     * false: 未超�?     */
    private Boolean overdue;
    
    /**
     * 剩余处理时间（小时）
     * 负数表示已超时的小时�?     */
    private Integer remainingHours;
    
    /**
     * 可用操作列表
     */
    private ArrayList<String> availableOperations;
    
    /**
     * 关联的业务数据ID
     */
    private Long businessId;
    
    /**
     * 关联的业务类�?     * 用于标识关联的具体业务模�?     */
    private String businessType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 创建�?     */
    private String createBy;

    /**
     * 更新�?     */
    private String updateBy;

    /**
     * 租户ID
     */
    private Long tenantId;
} 
