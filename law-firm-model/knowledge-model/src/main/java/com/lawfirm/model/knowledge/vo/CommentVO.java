package com.lawfirm.model.knowledge.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.knowledge.enums.CommentStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CommentVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 父评论ID
     */
    private Long parentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人ID
     */
    private Long userId;

    /**
     * 评论人名称
     */
    private String userName;

    /**
     * 评论人头像
     */
    private String userAvatar;

    /**
     * 评论状态
     */
    private CommentStatusEnum commentStatus;

    /**
     * 点赞数
     */
    private Long likeCount;

    /**
     * 回复数
     */
    private Long replyCount;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 设备信息
     */
    private String deviceInfo;

    /**
     * 审核人ID
     */
    private Long auditorId;

    /**
     * 审核人名称
     */
    private String auditorName;

    /**
     * 审核时间
     */
    private Long auditTime;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 回复列表
     */
    private transient List<CommentVO> replies = new ArrayList<>();

    /**
     * 子评论列表
     */
    private transient List<CommentVO> children;
} 