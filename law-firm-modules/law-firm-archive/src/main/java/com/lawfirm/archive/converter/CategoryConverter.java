package com.lawfirm.archive.converter;

import com.lawfirm.archive.model.dto.CategoryCreateDTO;
import com.lawfirm.archive.model.dto.CategoryUpdateDTO;
import com.lawfirm.archive.model.entity.ArchiveCategory;
import com.lawfirm.archive.model.vo.CategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * 档案分类转换器
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryConverter {

    /**
     * CreateDTO转Entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "level", ignore = true)
    ArchiveCategory toEntity(CategoryCreateDTO dto);

    /**
     * UpdateDTO更新Entity
     */
    void updateEntity(@MappingTarget ArchiveCategory entity, CategoryUpdateDTO dto);

    /**
     * Entity转VO
     */
    CategoryVO toVO(ArchiveCategory entity);
} 