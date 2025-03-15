package com.lawfirm.model.document.dto.category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档分类更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CategoryUpdateDTO extends CategoryCreateDTO {
    
    private static final long serialVersionUID = 1L;
} 