package com.lawfirm.core.workflow.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 任务视图对象
 *
 * @author claude
 */
@Data
public class TaskVO {
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
     */
    private Integer taskType;
    
    /**
     * 任务状态（0-待处理，1-处理中，2-已完成，3-已取消）
     */
    private Integer status;
    
    /**
     * 处理人ID
     */
    private Long handlerId;
    
    /**
     * 处理人名称
     */
    private String handlerName;
    
    /**
     * 处理结果
     */
    private String result;
    
    /**
     * 处理意见
     */
    private String comment;
    
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    
    /**
     * 流程定义ID
     */
    private String processDefinitionId;
    
    /**
     * 流程任务ID
     */
    private String flowableTaskId;
    
    /**
     * 截止时间
     */
    private Date dueDate;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 创建人ID
     */
    private Long creatorId;
    
    /**
     * 创建人名称
     */
    private String creatorName;
    
    /**
     * 更新人ID
     */
    private Long updaterId;
    
    /**
     * 更新人名称
     */
    private String updaterName;
    
    /**
     * 任务优先级
     */
    private Integer priority;
    
    /**
     * 是否超时
     */
    private Boolean isOverdue;
    
    /**
     * 剩余处理时间（小时）
     */
    private Integer remainingHours;
    
    /**
     * 可执行的操作列表
     */
    private List<String> availableOperations;
    
    /**
     * 关联的业务数据ID
     */
    private Long businessId;
    
    /**
     * 关联的业务类型
     */
    private String businessType;
} 