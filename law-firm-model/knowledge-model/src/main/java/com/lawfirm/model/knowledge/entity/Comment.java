package com.lawfirm.model.knowledge.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.knowledge.enums.CommentStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 评论实体
 */
@Data
@Entity
@Table(name = "knowledge_comment")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Comment extends ModelBaseEntity {

    /**
     * 文章ID
     */
    @Column(name = "article_id", nullable = false)
    private Long articleId;

    /**
     * 所属文章（延迟加载）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;

    /**
     * 父评论ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 评论内容
     */
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * 评论人ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 评论人名称
     */
    @Column(name = "user_name", nullable = false)
    private String userName;

    /**
     * 评论人头像
     */
    @Column(name = "user_avatar")
    private String userAvatar;

    /**
     * 评论状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "comment_status", nullable = false)
    private CommentStatusEnum commentStatus = CommentStatusEnum.PENDING;

    /**
     * 点赞数
     */
    @Column(name = "like_count")
    private Long likeCount = 0L;

    /**
     * 回复数
     */
    @Column(name = "reply_count")
    private Long replyCount = 0L;

    /**
     * IP地址
     */
    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * 设备信息
     */
    @Column(name = "device_info")
    private String deviceInfo;

    /**
     * 审核人ID
     */
    @Column(name = "auditor_id")
    private Long auditorId;

    /**
     * 审核人名称
     */
    @Column(name = "auditor_name")
    private String auditorName;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    private Long auditTime;

    /**
     * 审核备注
     */
    @Column(name = "audit_remark")
    private String auditRemark;
} 