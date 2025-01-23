package com.lawfirm.core.workflow.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 历史任务模型
 */
@Data
@Accessors(chain = true)
public class HistoricTask {
    
    /**
     * 任务ID
     */
    private String id;
    
    /**
     * 任务名称
     */
    private String name;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    
    /**
     * 流程定义ID
     */
    private String processDefinitionId;
    
    /**
     * 任务定义Key
     */
    private String taskDefinitionKey;
    
    /**
     * 表单Key
     */
    private String formKey;
    
    /**
     * 优先级
     */
    private int priority;
    
    /**
     * 所有者
     */
    private String owner;
    
    /**
     * 处理人
     */
    private String assignee;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 认领时间
     */
    private LocalDateTime claimTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 持续时间(毫秒)
     */
    private Long durationInMillis;
    
    /**
     * 删除原因
     */
    private String deleteReason;
    
    /**
     * 任务本地变量
     */
    private Map<String, Object> taskLocalVariables;
    
    /**
     * 流程变量
     */
    private Map<String, Object> processVariables;
    
    /**
     * 租户ID
     */
    private String tenantId;
} 