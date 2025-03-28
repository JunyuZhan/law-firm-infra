package com.lawfirm.model.knowledge.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 知识分类DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeCategoryDTO extends BaseDTO {

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
} 