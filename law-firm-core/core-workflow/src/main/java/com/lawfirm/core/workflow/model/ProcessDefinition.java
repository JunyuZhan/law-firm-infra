package com.lawfirm.core.workflow.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 流程定义
 */
@Data
@Accessors(chain = true)
public class ProcessDefinition {
    
    /**
     * 流程定义ID
     */
    private String id;
    
    /**
     * 流程定义键
     */
    private String key;
    
    /**
     * 流程定义名称
     */
    private String name;
    
    /**
     * 流程定义分类
     */
    private String category;
    
    /**
     * 流程定义版本
     */
    private int version;
    
    /**
     * 部署ID
     */
    private String deploymentId;
    
    /**
     * 资源名称
     */
    private String resourceName;
    
    /**
     * 流程图资源名称
     */
    private String diagramResourceName;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 是否挂起
     */
    private boolean suspended;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 部署时间
     */
    private LocalDateTime deploymentTime;
    
    /**
     * 引擎版本
     */
    private String engineVersion;
    
    /**
     * 是否有启动表单
     */
    private boolean hasStartForm;
    
    /**
     * 是否有图形化信息
     */
    private boolean hasGraphicalNotation;
} 