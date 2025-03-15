package com.lawfirm.model.document.dto.category;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档分类创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CategoryCreateDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 父分类ID
     */
    private Long parentId;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类编码
     */
    private String code;
    
    /**
     * 分类图标
     */
    private String icon;
    
    /**
     * 分类排序
     */
    private Integer sort;
    
    /**
     * 分类描述
     */
    private String description;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
} 