package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import com.lawfirm.model.knowledge.enums.KnowledgeTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识库实体
 */
@Getter
@Setter
@TableName("knowledge_document")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Knowledge extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 知识标题
     */
    @TableField("title")
    private String title;

    /**
     * 知识类型
     */
    @TableField("knowledge_type")
    private KnowledgeTypeEnum knowledgeType;

    /**
     * 知识内容
     */
    @TableField("content")
    private String content;

    /**
     * 知识摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 所属分类（延迟加载）
     */
    @TableField(exist = false)
    private KnowledgeCategory category;

    /**
     * 标签列表
     */
    @TableField(exist = false)
    private transient List<KnowledgeTag> tags = new ArrayList<>();

    /**
     * 关键词（逗号分隔）
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 作者ID
     */
    @TableField("author_id")
    private Long authorId;

    /**
     * 作者名称
     */
    @TableField("author_name")
    private String authorName;

    /**
     * 状态（0-草稿 1-已发布 2-已归档）
     */
    @TableField("status")
    private Integer status;

    /**
     * 点赞次数
     */
    @TableField("like_count")
    private Integer likeCount = 0;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 浏览次数
     */
    @TableField("view_count")
    private Integer viewCount = 0;

    /**
     * 附件列表
     */
    @TableField(exist = false)
    private transient List<KnowledgeAttachment> attachments = new ArrayList<>();
} 