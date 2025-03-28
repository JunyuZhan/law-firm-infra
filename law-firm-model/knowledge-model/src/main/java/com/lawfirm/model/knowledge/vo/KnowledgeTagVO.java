package com.lawfirm.model.knowledge.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 知识标签VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeTagVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签编码
     */
    private String code;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 使用次数
     */
    private Integer useCount;
} 