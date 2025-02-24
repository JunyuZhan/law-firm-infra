package com.lawfirm.model.cases.vo.business;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 知识评论VO
 */
@Data
public class KnowledgeCommentVO {
    
    private Long id;
    
    private Long articleId;
    
    private Long parentId;
    
    private String content;
    
    private String commentator;
    
    private String commentatorId;
    
    private Integer likeCount;
    
    private String status;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 