package com.lawfirm.model.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 树形实体基类
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class TreeEntity extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父级ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 层级
     */
    @TableField("level")
    private Integer level;

    /**
     * 路径
     */
    @TableField("path")
    private String path;

    /**
     * 是否叶子节点
     */
    @TableField("is_leaf")
    private Boolean leaf;
} 