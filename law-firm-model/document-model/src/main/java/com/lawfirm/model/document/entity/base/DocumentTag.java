package com.lawfirm.model.document.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档标签实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_tag")
public class DocumentTag extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @TableField("tag_name")
    private String tagName;

    /**
     * 标签编码
     */
    @TableField("tag_code")
    private String tagCode;

    /**
     * 标签类型
     */
    @TableField("tag_type")
    private String tagType;

    /**
     * 标签颜色
     */
    @TableField("color")
    private String color;

    /**
     * 标签描述
     */
    @TableField("description")
    private String description;

    /**
     * 是否系统标签
     */
    @TableField("is_system")
    private Boolean isSystem;

    /**
     * 是否启用
     */
    @TableField("is_enabled")
    private Boolean isEnabled;
} 