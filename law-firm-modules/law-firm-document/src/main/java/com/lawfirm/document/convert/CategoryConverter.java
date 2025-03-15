package com.lawfirm.document.convert;

import com.lawfirm.model.document.entity.base.DocumentCategory;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.vo.CategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文档分类对象转换器
 */
@Mapper
public interface CategoryConverter {
    
    CategoryConverter INSTANCE = Mappers.getMapper(CategoryConverter.class);
    
    /**
     * CategoryCreateDTO 转 DocumentCategory
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "ancestors", ignore = true)
    @Mapping(target = "children", ignore = true)
    DocumentCategory toCategory(CategoryCreateDTO dto);
    
    /**
     * CategoryUpdateDTO 转 DocumentCategory
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "ancestors", ignore = true)
    @Mapping(target = "children", ignore = true)
    DocumentCategory toCategory(CategoryUpdateDTO dto);
    
    /**
     * CategoryQueryDTO 转 DocumentCategory
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "ancestors", ignore = true)
    @Mapping(target = "children", ignore = true)
    DocumentCategory toCategory(CategoryQueryDTO dto);
    
    /**
     * DocumentCategory 转 CategoryVO
     */
    @Mapping(target = "parentName", source = "parent.name")
    @Mapping(target = "children", ignore = true)
    CategoryVO toVO(DocumentCategory category);
    
    /**
     * List<DocumentCategory> 转 List<CategoryVO>
     */
    List<CategoryVO> toVOList(List<DocumentCategory> categories);
}
