package com.lawfirm.document.convert;

import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.entity.base.DocumentInfo;
import com.lawfirm.model.document.entity.base.DocumentCategory;
import com.lawfirm.model.document.entity.base.DocumentTag;
import com.lawfirm.model.document.entity.base.DocumentTagRel;
import com.lawfirm.model.document.entity.base.DocumentPermission;
import com.lawfirm.model.document.entity.template.TemplateDocument;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentEditDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.document.vo.TemplateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档对象转换器
 */
@Mapper
public interface DocumentConverter {
    
    DocumentConverter INSTANCE = Mappers.getMapper(DocumentConverter.class);
    
    /**
     * DocumentCreateDTO 转 BaseDocument
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    BaseDocument toDocument(DocumentCreateDTO dto);
    
    /**
     * DocumentEditDTO 转 BaseDocument
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    BaseDocument toDocument(DocumentEditDTO dto);
    
    /**
     * DocumentUpdateDTO 转 BaseDocument
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    BaseDocument toDocument(DocumentUpdateDTO dto);
    
    /**
     * BaseDocument 转 DocumentVO
     */
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "tags", expression = "java(document.getTags().stream().map(DocumentTag::getName).collect(Collectors.toList()))")
    DocumentVO toVO(BaseDocument document);
    
    /**
     * List<BaseDocument> 转 List<DocumentVO>
     */
    List<DocumentVO> toVOList(List<BaseDocument> documents);
    
    /**
     * DocumentInfo 转 DocumentVO
     */
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "tags", expression = "java(documentInfo.getTags().stream().map(DocumentTag::getName).collect(Collectors.toList()))")
    DocumentVO toVO(DocumentInfo documentInfo);
    
    /**
     * List<DocumentInfo> 转 List<DocumentVO>
     */
    List<DocumentVO> toVOListFromInfo(List<DocumentInfo> documentInfos);
    
    /**
     * DocumentQueryDTO 转 DocumentInfo
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
    DocumentInfo toDocumentInfo(DocumentQueryDTO dto);
    
    /**
     * DocumentPermission 转 DocumentVO
     */
    @Mapping(target = "id", source = "documentId")
    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "tags", ignore = true)
    DocumentVO toVO(DocumentPermission permission);
    
    /**
     * List<DocumentPermission> 转 List<DocumentVO>
     */
    List<DocumentVO> toVOListFromPermission(List<DocumentPermission> permissions);
    
    /**
     * TemplateCreateDTO 转 TemplateDocument
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "title", source = "templateName")
    @Mapping(target = "docStatus", constant = "VALID")
    @Mapping(target = "useCount", constant = "0L")
    @Mapping(target = "downloadCount", constant = "0L")
    @Mapping(target = "viewCount", constant = "0L")
    @Mapping(target = "applyScope", source = "scope")
    @Mapping(target = "templateType", source = "templateType")
    @Mapping(target = "businessType", source = "businessType")
    @Mapping(target = "variableFields", source = "parameters")
    @Mapping(target = "isPublic", expression = "java(true)")
    TemplateDocument toTemplateDocument(TemplateCreateDTO dto);
    
    /**
     * TemplateDocument 转 TemplateVO
     */
    TemplateVO toTemplateVO(TemplateDocument template);
    
    /**
     * List<TemplateDocument> 转 List<TemplateVO>
     */
    List<TemplateVO> toTemplateVOList(List<TemplateDocument> templates);
}
