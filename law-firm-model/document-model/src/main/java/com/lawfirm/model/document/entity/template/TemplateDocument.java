package com.lawfirm.model.document.entity.template;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.document.entity.base.BaseDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档模板实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_template")
public class TemplateDocument extends BaseDocument {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 模板编码
     */
    @TableField("template_code")
    private String templateCode;
    
    /**
     * 模板分类ID
     */
    @TableField("category_id")
    private Long categoryId;
    
    /**
     * 模板类型（WORD/PDF/HTML等）
     */
    @TableField("template_type")
    private String templateType;
    
    /**
     * 变量字段（JSON格式）
     */
    @TableField("variable_fields")
    private String variableFields;
    
    /**
     * 适用范围
     */
    @TableField("apply_scope")
    private String applyScope;
    
    /**
     * 作者
     */
    @TableField("author")
    private String author;
    
    /**
     * 使用次数
     */
    @TableField("use_count")
    private Long useCount;
    
    /**
     * 是否公开
     */
    @TableField("is_public")
    private Boolean isPublic;
} 