package com.lawfirm.model.knowledge.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 知识分类VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeCategoryVO extends BaseVO {

    private static final long serialVersionUID = 1L;
    
    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类编码
     */
    private String code;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 父分类名称
     */
    private String parentName;

    /**
     * 分类路径（从根到当前节点的路径，用/分隔）
     */
    private String path;

    /**
     * 分类层级（从1开始）
     */
    private Integer level;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 子分类列表
     */
    private transient List<KnowledgeCategoryVO> children;
} 