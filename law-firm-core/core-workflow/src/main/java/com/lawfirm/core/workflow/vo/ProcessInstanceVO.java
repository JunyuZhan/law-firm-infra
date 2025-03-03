package com.lawfirm.core.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.workflow.vo.FlowableVO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 流程实例视图对象
 *
 * @author claude
 */
@Data
@Accessors(chain = false)
public class ProcessInstanceVO implements FlowableVO {

    private static final long serialVersionUID = 1L;

    /**
     * 流程实例ID
     */
    private String id;
    
    /**
     * 流程定义ID
     */
    private String processDefinitionId;
    
    /**
     * 流程定义名称
     */
    private String processDefinitionName;
    
    /**
     * 流程定义Key
     */
    private String processDefinitionKey;
    
    /**
     * 流程定义版本
     */
    private Integer processDefinitionVersion;
    
    /**
     * 部署ID
     */
    private String deploymentId;
    
    /**
     * 业务Key
     */
    private String businessKey;
    
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    /**
     * 持续时间（毫秒）
     */
    private Long durationInMillis;
    
    /**
     * 启动用户ID
     */
    private String startUserId;
    
    /**
     * 启动活动ID
     */
    private String startActivityId;
    
    /**
     * 父流程实例ID
     */
    private String superProcessInstanceId;
    
    /**
     * 是否挂起
     */
    private Boolean suspended;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 流程名称
     */
    private String name;
    
    /**
     * 流程描述
     */
    private String description;
    
    /**
     * 流程状态
     */
    private String processStatus;
    
    /**
     * 当前任务名称
     */
    private String currentTaskName;
    
    /**
     * 当前处理人
     */
    private String currentHandler;
} 