package com.lawfirm.archive.converter;

import com.lawfirm.archive.model.dto.ArchiveCreateDTO;
import com.lawfirm.archive.model.dto.ArchiveUpdateDTO;
import com.lawfirm.archive.model.entity.Archive;
import com.lawfirm.archive.model.enums.ArchiveStatusEnum;
import com.lawfirm.archive.model.enums.RetentionPeriodEnum;
import com.lawfirm.archive.model.enums.SecurityLevelEnum;
import com.lawfirm.archive.model.vo.ArchiveVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * 档案转换器
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ArchiveConverter {

    /**
     * CreateDTO转Entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "IN_STORAGE")
    @Mapping(target = "filingTime", expression = "java(java.time.LocalDateTime.now())")
    Archive toEntity(ArchiveCreateDTO dto);

    /**
     * UpdateDTO更新Entity
     */
    void updateEntity(@MappingTarget Archive entity, ArchiveUpdateDTO dto);

    /**
     * Entity转VO
     */
    @Mapping(target = "statusDesc", expression = "java(getStatusDesc(entity.getStatus()))")
    @Mapping(target = "retentionPeriodDesc", expression = "java(getRetentionPeriodDesc(entity.getRetentionPeriod()))")
    @Mapping(target = "securityLevelDesc", expression = "java(getSecurityLevelDesc(entity.getSecurityLevel()))")
    ArchiveVO toVO(Archive entity);

    /**
     * 获取状态描述
     */
    default String getStatusDesc(String status) {
        try {
            return ArchiveStatusEnum.valueOf(status).getDesc();
        } catch (IllegalArgumentException e) {
            return status;
        }
    }

    /**
     * 获取保管期限描述
     */
    default String getRetentionPeriodDesc(String retentionPeriod) {
        try {
            return RetentionPeriodEnum.valueOf(retentionPeriod).getDesc();
        } catch (IllegalArgumentException e) {
            return retentionPeriod;
        }
    }

    /**
     * 获取密级描述
     */
    default String getSecurityLevelDesc(String securityLevel) {
        try {
            return SecurityLevelEnum.valueOf(securityLevel).getDesc();
        } catch (IllegalArgumentException e) {
            return securityLevel;
        }
    }
} 