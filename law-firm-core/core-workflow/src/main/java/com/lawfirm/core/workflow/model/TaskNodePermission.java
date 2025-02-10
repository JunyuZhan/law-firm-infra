package com.lawfirm.core.workflow.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 任务节点权限
 */
@Data
@Accessors(chain = true)
public class TaskNodePermission {
    
    /**
     * 任务节点Key
     */
    private String taskKey;
    
    /**
     * 任务节点名称
     */
    private String taskName;
    
    /**
     * 可处理的角色列表
     */
    private List<String> assigneeRoles;
    
    /**
     * 可处理的部门列表
     */
    private List<String> assigneeDepartments;
    
    /**
     * 可处理的用户列表
     */
    private List<String> assigneeUsers;
    
    /**
     * 是否需要会签
     */
    private boolean multiInstance;
    
    /**
     * 会签类型（AND/OR）
     */
    private String multiInstanceType;
    
    /**
     * 是否可转办
     */
    private boolean transferable;
    
    /**
     * 是否可委托
     */
    private boolean delegatable;
    
    /**
     * 是否可退回
     */
    private boolean rejectable;
    
    /**
     * 备注
     */
    private String remark;
} 