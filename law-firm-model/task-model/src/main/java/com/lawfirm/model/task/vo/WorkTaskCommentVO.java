package com.lawfirm.model.task.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 工作任务评论展示对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkTaskCommentVO extends BaseVO {

    private static final long serialVersionUID = 1L;
    
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
     * 创建人姓名
     */
    private String creatorName;
    
    /**
     * 创建人头像
     */
    private String creatorAvatar;
    
    /**
     * 父评论ID
     */
    private Long parentId;
    
    /**
     * 父评论内容
     */
    private String parentContent;
    
    /**
     * 父评论创建人姓名
     */
    private String parentCreatorName;
    
    /**
     * 回复列表
     */
    private transient List<WorkTaskCommentVO> replies;
    
    /**
     * 回复数量
     */
    private Integer replyCount;
    
    /**
     * 点赞数量
     */
    private Integer likeCount;
    
    /**
     * 是否已点赞
     */
    private Boolean liked;
} 