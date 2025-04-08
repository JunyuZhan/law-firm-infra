package com.lawfirm.model.task.query;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 工作任务评论查询对象
 */
@Data
public class WorkTaskCommentQuery {
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 创建人ID
     */
    private Long creatorId;
    
    /**
     * 评论时间范围-开始
     */
    private LocalDateTime startTime;
    
    /**
     * 评论时间范围-结束
     */
    private LocalDateTime endTime;
    
    /**
     * 租户ID
     */
    private Long tenantId;
}