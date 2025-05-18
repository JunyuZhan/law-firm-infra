package com.lawfirm.model.document.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档分类实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_category")
public class DocumentCategory extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 分类编码
     */
    @TableField("category_code")
    private String categoryCode;

    /**
     * 父级分类ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 分类层级
     */
    @TableField("level")
    private Integer level;

    /**
     * 排序号
     */
    @TableField("sort_no")
    private Integer sortNo;

    /**
     * 分类描述
     */
    @TableField("description")
    private String description;

    /**
     * 是否启用
     */
    @TableField("is_enabled")
    private Boolean isEnabled;
    
    /**
     * 获取分类名称（兼容旧代码）
     * 
     * @return 分类名称
     */
    public String getName() {
        return this.categoryName;
    }
    
    /**
     * 设置分类名称（兼容旧代码）
     * 
     * @param name 分类名称
     */
    public DocumentCategory setName(String name) {
        this.categoryName = name;
        return this;
    }
} 