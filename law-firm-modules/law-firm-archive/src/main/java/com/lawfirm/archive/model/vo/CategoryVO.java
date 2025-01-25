package com.lawfirm.archive.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 档案分类VO
 */
@Data
public class CategoryVO {
    
    /**
     * ID
     */
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
     * 父分类ID
     */
    private Long parentId;
    
    /**
     * 父分类名称
     */
    private String parentName;
    
    /**
     * 分类层级
     */
    private Integer level;
    
    /**
     * 排序号
     */
    private Integer sortOrder;
    
    /**
     * 分类描述
     */
    private String description;
    
    /**
     * 子分类列表
     */
    private List<CategoryVO> children;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 