package com.lawfirm.common.data.mapper;

import org.mapstruct.MappingTarget;

/**
 * 基础Mapper接口
 */
public interface BaseMapper<D, E> {
    /**
     * DTO转Entity
     */
    E toEntity(D dto);

    /**
     * Entity转DTO
     */
    D toDto(E entity);

    /**
     * 更新Entity
     */
    void updateEntity(@MappingTarget E entity, D dto);
} 