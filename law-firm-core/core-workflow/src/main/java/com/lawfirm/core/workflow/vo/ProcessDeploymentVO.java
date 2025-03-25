package com.lawfirm.core.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.workflow.vo.FlowableVO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 流程部署视图对象
 *
 * @author JunyuZhan
 */
@Data
@Accessors(chain = false)
public class ProcessDeploymentVO implements FlowableVO {

    private static final long serialVersionUID = 1L;

    /**
     * 部署ID
     */
    private String id;
    
    /**
     * 部署名称
     */
    private String name;
    
    /**
     * 部署分类
     */
    private String category;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 部署时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deploymentTime;
    
    /**
     * 引擎版本
     */
    private String engineVersion;
    
    /**
     * 部署的资源列表
     */
    private ArrayList<String> resourceNames;
    
    /**
     * 项目版本
     */
    private String projectVersion;
    
    /**
     * 项目发布
     */
    private String projectRelease;
    
    /**
     * 部署说明
     */
    private String description;
    
    /**
     * 是否激活
     */
    private Boolean active;

    /**
     * 父部署ID
     */
    private String parentDeploymentId;

    /**
     * 设置租户ID
     * 
     * @param tenantId 租户ID
     */
    @Override
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
} 
