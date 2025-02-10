package com.lawfirm.core.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 业务流程关联实体
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "wf_business_process")
@Accessors(chain = true)
public class BusinessProcessEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 业务类型
     */
    @Column(name = "business_type", nullable = false)
    private String businessType;
    
    /**
     * 业务ID
     */
    @Column(name = "business_id", nullable = false)
    private String businessId;
    
    /**
     * 业务标题
     */
    @Column(name = "business_title")
    private String businessTitle;
    
    /**
     * 流程实例ID
     */
    @Column(name = "process_instance_id", nullable = false)
    private String processInstanceId;
    
    /**
     * 流程定义ID
     */
    @Column(name = "process_definition_id", nullable = false)
    private String processDefinitionId;
    
    /**
     * 流程定义Key
     */
    @Column(name = "process_definition_key", nullable = false)
    private String processDefinitionKey;
    
    /**
     * 流程发起人ID
     */
    @Column(name = "start_user_id")
    private String startUserId;
    
    /**
     * 流程发起时间
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    /**
     * 流程结束时间
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    /**
     * 流程状态
     */
    @Column(name = "process_status", nullable = false)
    private String processStatus;
    
    /**
     * 当前任务ID
     */
    @Column(name = "current_task_id")
    private String currentTaskId;
    
    /**
     * 当前任务名称
     */
    @Column(name = "current_task_name")
    private String currentTaskName;
    
    /**
     * 当前处理人
     */
    @Column(name = "current_assignee")
    private String currentAssignee;
    
    /**
     * 业务表单数据（JSON）
     */
    @Column(name = "form_data", columnDefinition = "text")
    private String formData;
    
    /**
     * 流程变量（JSON）
     */
    @Column(name = "process_variables", columnDefinition = "text")
    private String processVariables;
    
    /**
     * 租户ID
     */
    @Column(name = "tenant_id")
    private String tenantId;
    
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
} 