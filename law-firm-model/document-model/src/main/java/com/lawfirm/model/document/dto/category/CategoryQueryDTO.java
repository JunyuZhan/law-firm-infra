package com.lawfirm.model.document.dto.category;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.document.vo.CategoryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档分类查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CategoryQueryDTO extends PageDTO<CategoryVO> {
    
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
     * 是否启用
     */
    private Boolean enabled;
} 