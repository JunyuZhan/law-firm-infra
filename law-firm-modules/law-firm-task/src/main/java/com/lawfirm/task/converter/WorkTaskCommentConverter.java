package com.lawfirm.task.converter;

import com.lawfirm.model.task.dto.WorkTaskCommentDTO;
import com.lawfirm.model.task.entity.WorkTaskComment;
import com.lawfirm.model.task.vo.WorkTaskCommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 工作任务评论转换器
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WorkTaskCommentConverter {
    
    /**
     * 实体转DTO
     *
     * @param entity 实体
     * @return DTO
     */
    @Mapping(target = "replies", ignore = true)
    WorkTaskCommentDTO entityToDto(WorkTaskComment entity);
    
    /**
     * DTO转实体
     *
     * @param dto DTO
     * @return 实体
     */
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "sort", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "status", ignore = true)
    WorkTaskComment dtoToEntity(WorkTaskCommentDTO dto);
    
    /**
     * 实体转VO
     *
     * @param entity 实体
     * @return VO
     */
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "replyCount", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    WorkTaskCommentVO entityToVo(WorkTaskComment entity);
    
    /**
     * DTO转VO
     *
     * @param dto DTO
     * @return VO
     */
    @Mapping(target = "replyCount", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "remark", ignore = true)
    WorkTaskCommentVO dtoToVo(WorkTaskCommentDTO dto);
} 