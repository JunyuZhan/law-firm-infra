package com.lawfirm.core.workflow.entity;

import com.lawfirm.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 流程权限实体
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "wf_process_permission")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProcessPermissionEntity extends BaseEntity<ProcessPermissionEntity> {
    
    /**
     * 流程定义Key
     */
    @Column(name = "process_key", nullable = false)
    private String processKey;
    
    /**
     * 流程名称
     */
    @Column(name = "process_name")
    private String processName;
    
    /**
     * 流程分类
     */
    @Column(name = "category")
    private String category;
    
    /**
     * 可发起的角色列表（JSON）
     */
    @Column(name = "start_roles", columnDefinition = "text")
    private String startRoles;
    
    /**
     * 可发起的部门列表（JSON）
     */
    @Column(name = "start_departments", columnDefinition = "text")
    private String startDepartments;
    
    /**
     * 任务节点权限列表（JSON）
     */
    @Column(name = "task_node_permissions", columnDefinition = "text")
    private String taskNodePermissions;
    
    /**
     * 流程管理员角色列表（JSON）
     */
    @Column(name = "admin_roles", columnDefinition = "text")
    private String adminRoles;
    
    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    
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
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;
    
    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;
} 