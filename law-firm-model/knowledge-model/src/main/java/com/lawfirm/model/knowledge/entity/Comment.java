package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.knowledge.enums.CommentStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 评论实体
 */
@Data
@TableName("knowledge_comment")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Comment extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @TableField("article_id")
    private Long articleId;

    /**
     * 所属文章（延迟加载）
     */
    @TableField(exist = false)
    private Article article;

    /**
     * 父评论ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 评论人ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 评论人名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 评论人头像
     */
    @TableField("user_avatar")
    private String userAvatar;

    /**
     * 评论状态
     */
    @TableField("comment_status")
    private CommentStatusEnum commentStatus = CommentStatusEnum.PENDING;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Long likeCount = 0L;

    /**
     * 回复数
     */
    @TableField("reply_count")
    private Long replyCount = 0L;

    /**
     * IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 设备信息
     */
    @TableField("device_info")
    private String deviceInfo;

    /**
     * 审核人ID
     */
    @TableField("auditor_id")
    private Long auditorId;

    /**
     * 审核人名称
     */
    @TableField("auditor_name")
    private String auditorName;

    /**
     * 审核时间
     */
    @TableField("audit_time")
    private Long auditTime;

    /**
     * 审核备注
     */
    @TableField("audit_remark")
    private String auditRemark;
} 