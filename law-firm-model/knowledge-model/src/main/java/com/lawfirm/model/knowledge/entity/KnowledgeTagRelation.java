package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 知识标签关联实体
 */
@Getter
@Setter
@TableName("knowledge_tag_relation")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeTagRelation extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 知识ID
     */
    @TableField("knowledge_id")
    private Long knowledgeId;

    /**
     * 标签ID
     */
    @TableField("tag_id")
    private Long tagId;
    
    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
    
    /**
     * 租户编码
     */
    @TableField("tenant_code")
    private String tenantCode;
    
    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;
    
    /**
     * 是否删除（0否 1是）
     */
    @TableField("deleted")
    private Integer deleted;
} 