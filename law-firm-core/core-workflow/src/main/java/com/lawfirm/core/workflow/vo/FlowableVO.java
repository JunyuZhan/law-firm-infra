package com.lawfirm.core.workflow.vo;

import java.io.Serializable;

/**
 * Flowable引擎相关的VO基础接口
 *
 * @author claude
 */
public interface FlowableVO extends Serializable {
    
    /**
     * 获取ID
     */
    String getId();
    
    /**
     * 设置ID
     */
    void setId(String id);
    
    /**
     * 获取租户ID
     */
    String getTenantId();
    
    /**
     * 设置租户ID
     */
    void setTenantId(String tenantId);
} 