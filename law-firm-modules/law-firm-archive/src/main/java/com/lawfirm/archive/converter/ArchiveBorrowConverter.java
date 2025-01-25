package com.lawfirm.archive.converter;

import com.lawfirm.archive.model.dto.ArchiveBorrowDTO;
import com.lawfirm.archive.model.entity.ArchiveBorrow;
import com.lawfirm.archive.model.enums.BorrowStatusEnum;
import com.lawfirm.archive.model.vo.ArchiveBorrowVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * 档案借阅转换器
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ArchiveBorrowConverter {

    /**
     * DTO转Entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "BORROWED")
    @Mapping(target = "borrowTime", expression = "java(java.time.LocalDateTime.now())")
    ArchiveBorrow toEntity(ArchiveBorrowDTO dto);

    /**
     * Entity转VO
     */
    @Mapping(target = "statusDesc", expression = "java(getStatusDesc(entity.getStatus()))")
    ArchiveBorrowVO toVO(ArchiveBorrow entity);

    /**
     * 获取状态描述
     */
    default String getStatusDesc(String status) {
        try {
            return BorrowStatusEnum.valueOf(status).getDesc();
        } catch (IllegalArgumentException e) {
            return status;
        }
    }
} 