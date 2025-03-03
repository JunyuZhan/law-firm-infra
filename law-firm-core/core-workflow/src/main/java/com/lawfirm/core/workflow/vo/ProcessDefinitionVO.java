package com.lawfirm.core.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.workflow.vo.FlowableVO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 流程定义视图对象
 *
 * @author claude
 */
@Data
@Accessors(chain = false)
public class ProcessDefinitionVO implements FlowableVO {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    private String id;
    
    /**
     * 流程定义Key
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
    private Integer version;
    
    /**
     * 部署ID
     */
    private String deploymentId;
    
    /**
     * 资源名称
     */
    private String resourceName;
    
    /**
     * 部署时间
     */
    private LocalDateTime deploymentTime;
    
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
    private Boolean suspended;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 引擎版本
     */
    private String engineVersion;
    
    /**
     * 是否有启动表单
     */
    private Boolean hasStartForm;
    
    /**
     * 是否图形化流程
     */
    private Boolean graphicalNotation;
} 