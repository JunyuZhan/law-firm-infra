package com.lawfirm.core.workflow.model;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 流程权限模型
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProcessPermission extends BaseVO {
    
    /**
     * 流程定义Key
     */
    private String processKey;
    
    /**
     * 流程名称
     */
    private String processName;
    
    /**
     * 流程分类
     */
    private String category;
    
    /**
     * 可发起的角色列表
     */
    private List<String> startRoles;
    
    /**
     * 可发起的部门列表
     */
    private List<String> startDepartments;
    
    /**
     * 任务节点权限列表
     */
    private List<TaskNodePermission> taskNodePermissions;
    
    /**
     * 流程管理员角色列表
     */
    private List<String> adminRoles;
    
    /**
     * 是否启用
     */
    private boolean enabled;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 备注
     */
    private String remark;
} 