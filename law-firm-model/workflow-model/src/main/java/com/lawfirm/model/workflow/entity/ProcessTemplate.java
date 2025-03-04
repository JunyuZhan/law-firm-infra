package com.lawfirm.model.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程模板实体�? * 
 * @author JunyuZhan
 */
@Data
@TableName(value = "wf_process_template")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProcessTemplate extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程模板标识
     */
    @TableField(value = "template_key")
    private String key;

    /**
     * 流程模板名称
     */
    @TableField(value = "template_name")
    private String name;

    /**
     * 流程模板分类
     */
    @TableField(value = "template_category")
    private String category;

    /**
     * 业务类型
     */
    @TableField(value = "business_type")
    private String businessType;

    /**
     * 模板版本�?     */
    @TableField(value = "template_version")
    private String templateVersion;

    /**
     * 流程定义ID
     */
    @TableField(value = "process_definition_id")
    private String processDefinitionId;

    /**
     * 流程部署ID
     */
    @TableField(value = "deployment_id")
    private String deploymentId;

    /**
     * 流程模板描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否挂起
     */
    @TableField(value = "suspended")
    private Boolean suspended;

    /**
     * 创建人名�?     */
    @TableField(value = "creator_name")
    private String creatorName;

    /**
     * 更新人名�?     */
    @TableField(value = "updater_name")
    private String updaterName;
} 
