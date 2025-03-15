package com.lawfirm.document.convert;

import com.lawfirm.model.document.entity.template.TemplateDocument;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.dto.template.TemplateUpdateDTO;
import com.lawfirm.model.document.vo.TemplateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文档模板对象转换器
 */
@Mapper
public interface TemplateConverter {
    
    TemplateConverter INSTANCE = Mappers.getMapper(TemplateConverter.class);
    
    /**
     * TemplateCreateDTO 转 TemplateDocument
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    TemplateDocument toTemplate(TemplateCreateDTO dto);
    
    /**
     * TemplateUpdateDTO 转 TemplateDocument
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    TemplateDocument toTemplate(TemplateUpdateDTO dto);
    
    /**
     * TemplateDocument 转 TemplateVO
     */
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "tags", expression = "java(template.getTags().stream().map(tag -> tag.getName()).collect(java.util.stream.Collectors.toList()))")
    TemplateVO toVO(TemplateDocument template);
    
    /**
     * List<TemplateDocument> 转 List<TemplateVO>
     */
    List<TemplateVO> toVOList(List<TemplateDocument> templates);
}
