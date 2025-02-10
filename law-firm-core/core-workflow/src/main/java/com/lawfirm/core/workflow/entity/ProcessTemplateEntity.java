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
 * 流程模板实体
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "wf_process_template")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProcessTemplateEntity extends BaseEntity<ProcessTemplateEntity> {
    
    /**
     * 流程定义Key
     */
    @Column(name = "process_key", nullable = false)
    private String processKey;
    
    /**
     * 流程名称
     */
    @Column(name = "process_name", nullable = false)
    private String processName;
    
    /**
     * 流程分类
     */
    @Column(name = "category")
    private String category;
    
    /**
     * 部署ID
     */
    @Column(name = "deployment_id")
    private String deploymentId;
    
    /**
     * 流程定义ID
     */
    @Column(name = "process_definition_id")
    private String processDefinitionId;
    
    /**
     * 流程定义文件名
     */
    @Column(name = "file_name")
    private String fileName;
    
    /**
     * 流程定义XML
     */
    @Column(name = "xml_content", columnDefinition = "text")
    private String xmlContent;
    
    /**
     * 表单定义（JSON）
     */
    @Column(name = "form_definition", columnDefinition = "text")
    private String formDefinition;
    
    /**
     * 节点定义（JSON）
     */
    @Column(name = "node_definition", columnDefinition = "text")
    private String nodeDefinition;
    
    /**
     * 版本号
     */
    @Column(name = "version")
    private Integer version;
    
    /**
     * 是否最新版本
     */
    @Column(name = "latest")
    private Boolean latest;
    
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