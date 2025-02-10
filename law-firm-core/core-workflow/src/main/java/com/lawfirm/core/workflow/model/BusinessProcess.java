package com.lawfirm.core.workflow.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 业务流程关联
 */
@Data
@Accessors(chain = true)
public class BusinessProcess {
    
    /**
     * 业务类型（case/contract/finance/admin）
     */
    private String businessType;
    
    /**
     * 业务ID
     */
    private String businessId;
    
    /**
     * 业务标题
     */
    private String businessTitle;
    
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    
    /**
     * 流程定义ID
     */
    private String processDefinitionId;
    
    /**
     * 流程定义Key
     */
    private String processDefinitionKey;
    
    /**
     * 流程发起人ID
     */
    private String startUserId;
    
    /**
     * 流程发起时间
     */
    private LocalDateTime startTime;
    
    /**
     * 流程结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 流程状态（running/suspended/completed/terminated）
     */
    private String processStatus;
    
    /**
     * 当前任务ID
     */
    private String currentTaskId;
    
    /**
     * 当前任务名称
     */
    private String currentTaskName;
    
    /**
     * 当前处理人
     */
    private String currentAssignee;
    
    /**
     * 业务表单数据
     */
    private Map<String, Object> formData;
    
    /**
     * 流程变量
     */
    private Map<String, Object> processVariables;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 备注
     */
    private String remark;
} 