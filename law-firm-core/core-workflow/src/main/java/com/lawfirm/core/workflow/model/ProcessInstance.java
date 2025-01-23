package com.lawfirm.core.workflow.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 流程实例
 */
@Data
@Accessors(chain = true)
public class ProcessInstance {
    
    /**
     * 流程实例ID
     */
    private String id;
    
    /**
     * 流程定义ID
     */
    private String processDefinitionId;
    
    /**
     * 流程定义键
     */
    private String processDefinitionKey;
    
    /**
     * 流程定义名称
     */
    private String processDefinitionName;
    
    /**
     * 流程定义版本
     */
    private int processDefinitionVersion;
    
    /**
     * 部署ID
     */
    private String deploymentId;
    
    /**
     * 业务键
     */
    private String businessKey;
    
    /**
     * 父流程实例ID
     */
    private String parentId;
    
    /**
     * 父流程实例部署ID
     */
    private String parentDeploymentId;
    
    /**
     * 超级流程实例ID
     */
    private String superProcessInstanceId;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 流程名称
     */
    private String name;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 开始用户ID
     */
    private String startUserId;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 是否结束
     */
    private boolean ended;
    
    /**
     * 是否挂起
     */
    private boolean suspended;
    
    /**
     * 流程变量
     */
    private Map<String, Object> variables;
    
    /**
     * 本地变量
     */
    private Map<String, Object> localVariables;
} 