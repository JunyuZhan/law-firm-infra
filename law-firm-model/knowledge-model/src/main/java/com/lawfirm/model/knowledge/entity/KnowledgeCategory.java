package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识分类实体
 */
@Data
@TableName("knowledge_category")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeCategory extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;

    /**
     * 分类编码
     */
    @TableField("code")
    private String code;

    /**
     * 父分类ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 分类路径（从根到当前节点的路径，用/分隔）
     */
    @TableField("path")
    private String path;

    /**
     * 分类层级（从1开始）
     */
    @TableField("level")
    private Integer level;

    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 子分类列表
     */
    @TableField(exist = false)
    private transient List<KnowledgeCategory> children = new ArrayList<>();

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;
} 