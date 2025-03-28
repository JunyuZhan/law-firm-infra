package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 知识标签关联实体
 */
@Data
@TableName("knowledge_tag_relation")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeTagRelation extends ModelBaseEntity {

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
} 