package com.lawfirm.model.knowledge.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.knowledge.enums.KnowledgeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 知识DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeDTO extends BaseDTO {

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
     * 标签ID列表
     */
    private transient List<Long> tagIds;

    /**
     * 关键词（逗号分隔）
     */
    private String keywords;

    /**
     * 备注
     */
    private String remarks;
} 