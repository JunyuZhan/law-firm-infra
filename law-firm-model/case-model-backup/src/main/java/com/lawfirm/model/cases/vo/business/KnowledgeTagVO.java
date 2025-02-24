package com.lawfirm.model.cases.vo.business;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 知识标签VO
 */
@Data
public class KnowledgeTagVO {
    
    private Long id;
    
    private String name;
    
    private String code;
    
    private String category;
    
    private String description;
    
    private Integer useCount;
    
    private Integer sort;
    
    private Boolean enabled;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 