package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 知识标签实体
 */
@Getter
@Setter
@TableName("knowledge_tag")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeTag extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @TableField("name")
    private String name;

    /**
     * 标签编码
     */
    @TableField("code")
    private String code;

    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;
} 