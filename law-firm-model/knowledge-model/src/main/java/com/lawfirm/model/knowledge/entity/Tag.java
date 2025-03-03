package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 标签实体
 */
@Data
@TableName("knowledge_tag")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Tag extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @TableField("name")
    private String name;

    /**
     * 标签编码（用于URL）
     */
    @TableField("code")
    private String code;

    /**
     * 标签描述
     */
    @TableField("description")
    private String description;

    /**
     * 使用次数
     */
    @TableField("use_count")
    private Long useCount = 0L;

    /**
     * 排序权重
     */
    @TableField("weight")
    private Integer weight = 0;

    /**
     * 是否推荐
     */
    @TableField("recommended")
    private Boolean recommended = false;
} 