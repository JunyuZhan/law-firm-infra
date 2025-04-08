package com.lawfirm.model.task.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 工作任务评论数据传输对象
 */
@Data
public class WorkTaskCommentDTO {
    /**
     * 评论ID
     */
    private Long id;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人ID
     */
    private Long creatorId;

    /**
     * 评论人姓名
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 父评论ID
     */
    private Long parentId;

    /**
     * 回复列表
     */
    private List<WorkTaskCommentDTO> replies;
} 