package com.lawfirm.common.data.convert;

import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.common.data.dto.BaseDTO;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 基础对象转换器
 */
public interface BaseConverter<E extends BaseEntity, D extends BaseDTO> {

    /**
     * 实体转DTO
     */
    D toDTO(E entity);

    /**
     * DTO转实体
     */
    E toEntity(D dto);

    /**
     * 更新实体
     */
    void updateEntity(D dto, @MappingTarget E entity);

    /**
     * 实体列表转DTO列表
     */
    List<D> toDTOList(List<E> entityList);

    /**
     * DTO列表转实体列表
     */
    List<E> toEntityList(List<D> dtoList);
} 