package com.lawfirm.model.document.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档分类VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 父分类ID
     */
    private Long parentId;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类编码
     */
    private String code;
    
    /**
     * 分类图标
     */
    private String icon;
    
    /**
     * 分类排序
     */
    private Integer sort;
    
    /**
     * 分类描述
     */
    private String description;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 子分类列表
     */
    private ArrayList<CategoryVO> children = new ArrayList<>();
} 