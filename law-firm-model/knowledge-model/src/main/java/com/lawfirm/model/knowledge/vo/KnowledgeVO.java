package com.lawfirm.model.knowledge.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.knowledge.enums.KnowledgeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 知识VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 知识标题
     */
    private String title;

    /**
     * 知识类型
     */
    private KnowledgeTypeEnum knowledgeType;

    /**
     * 知识内容
     */
    private String content;

    /**
     * 知识摘要
     */
    private String summary;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 标签列表
     */
    private transient List<KnowledgeTagVO> tags;

    /**
     * 关键词（逗号分隔）
     */
    private String keywords;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 附件列表
     */
    private transient List<KnowledgeAttachmentVO> attachments;
} 