package com.lawfirm.model.cases.vo.business;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识文章VO
 */
@Data
public class KnowledgeArticleVO {
    
    private Long id;
    
    private Long caseId;
    
    private String title;
    
    private String content;
    
    private String category;
    
    private List<String> tags;
    
    private String author;
    
    private String authorId;
    
    private Integer viewCount;
    
    private Integer likeCount;
    
    private Integer commentCount;
    
    private Boolean isPublic;
    
    private String status;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 