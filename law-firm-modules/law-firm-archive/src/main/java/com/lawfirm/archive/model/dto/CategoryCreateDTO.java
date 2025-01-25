package com.lawfirm.archive.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 档案分类创建DTO
 */
@Data
public class CategoryCreateDTO {
    
    /**
     * 分类编码
     */
    @NotBlank(message = "分类编码不能为空")
    private String categoryCode;
    
    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;
    
    /**
     * 父分类ID
     */
    private Long parentId;
    
    /**
     * 排序号
     */
    @NotNull(message = "排序号不能为空")
    private Integer sortOrder;
    
    /**
     * 分类描述
     */
    private String description;
} 