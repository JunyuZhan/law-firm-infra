package com.lawfirm.task.converter;

import com.lawfirm.model.task.dto.WorkTaskTagDTO;
import com.lawfirm.model.task.entity.WorkTaskTag;
import com.lawfirm.model.task.vo.WorkTaskTagVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 工作任务标签转换器
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WorkTaskTagConverter {
    
    /**
     * 实体转DTO
     *
     * @param entity 实体
     * @return DTO
     */
    WorkTaskTagDTO entityToDto(WorkTaskTag entity);
    
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
    @Mapping(target = "usageCount", ignore = true)
    WorkTaskTag dtoToEntity(WorkTaskTagDTO dto);
    
    /**
     * 实体转VO
     *
     * @param entity 实体
     * @return VO
     */
    WorkTaskTagVO entityToVo(WorkTaskTag entity);
    
    /**
     * DTO转VO
     *
     * @param dto DTO
     * @return VO
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "remark", ignore = true)
    WorkTaskTagVO dtoToVo(WorkTaskTagDTO dto);
} 