package com.lawfirm.model.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.workflow.entity.base.BaseProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程实例实体类，扩展BaseProcess
 * 
 * @author JunyuZhan
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("workflow_process")
public class ProcessInstance extends BaseProcess {

    private static final long serialVersionUID = 1L;

    /**
     * Flowable流程实例ID
     */
    @TableField("process_instance_id")
    private String processInstanceId;
    
    /**
     * 流程定义ID
     */
    @TableField("process_definition_id")
    private String processDefinitionId;
    
    /**
     * 流程定义Key
     */
    @TableField("process_definition_key")
    private String processDefinitionKey;
    
    /**
     * 流程定义名称
     */
    @TableField("process_definition_name")
    private String processDefinitionName;
    
    /**
     * 流程定义版本
     */
    @TableField("process_definition_version")
    private Integer processDefinitionVersion;
    
    /**
     * 部署ID
     */
    @TableField("deployment_id")
    private String deploymentId;
    
    /**
     * 业务�?     */
    @TableField("business_key")
    private String businessKey;
} 
