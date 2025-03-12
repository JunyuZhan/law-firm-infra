package com.lawfirm.model.document.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档标签关联实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_tag_rel")
public class DocumentTagRel extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    @TableField("doc_id")
    private Long docId;

    /**
     * 标签ID
     */
    @TableField("tag_id")
    private Long tagId;
} 