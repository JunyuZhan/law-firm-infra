package com.lawfirm.task.converter;

import com.lawfirm.model.task.dto.WorkTaskAttachmentDTO;
import com.lawfirm.model.task.entity.WorkTaskAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 工作任务附件转换器
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkTaskAttachmentConverter {

    WorkTaskAttachmentConverter INSTANCE = Mappers.getMapper(WorkTaskAttachmentConverter.class);
    
    /**
     * 实体转DTO
     */
    @Mapping(target = "createTime", expression = "java(entity.getCreateTime())")
    WorkTaskAttachmentDTO entityToDto(WorkTaskAttachment entity);
    
    /**
     * DTO转实体
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "sort", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "uploaderAvatar", ignore = true)
    @Mapping(target = "fileSuffix", ignore = true)
    @Mapping(target = "previewUrl", ignore = true)
    @Mapping(target = "downloadUrl", ignore = true)
    @Mapping(target = "downloadCount", ignore = true)
    @Mapping(target = "previewable", ignore = true)
    @Mapping(target = "fileIcon", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    WorkTaskAttachment dtoToEntity(WorkTaskAttachmentDTO dto);
} 