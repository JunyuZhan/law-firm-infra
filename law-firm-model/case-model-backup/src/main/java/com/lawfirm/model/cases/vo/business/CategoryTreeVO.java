package com.lawfirm.model.cases.vo.business;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件分类树VO
 */
@Data
public class CategoryTreeVO {
    
    private Long id;
    
    private String name;
    
    private String code;
    
    private Long parentId;
    
    private Integer level;
    
    private Integer sort;
    
    private String description;
    
    private Boolean isLeaf;
    
    private List<CategoryTreeVO> children;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 