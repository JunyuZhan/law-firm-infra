package com.lawfirm.archive.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 档案分类更新DTO
 */
@Data
public class CategoryUpdateDTO {
    
    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long id;
    
    /**
     * 分类编码
     */
    private String categoryCode;
    
    /**
     * 分类名称
     */
    private String categoryName;
    
    /**
     * 排序号
     */
    private Integer sortOrder;
    
    /**
     * 分类描述
     */
    private String description;
} 