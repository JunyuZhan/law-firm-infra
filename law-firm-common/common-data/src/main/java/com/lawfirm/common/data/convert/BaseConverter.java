package com.lawfirm.common.data.convert;

import com.lawfirm.common.core.entity.BaseEntity;
import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.common.data.vo.BaseVO;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 基础转换器接口
 *
 * @param <T> 实体类型
 * @param <V> VO类型
 */
public interface BaseConverter<T extends BaseEntity<T>, V extends BaseVO> {

    /**
     * 实体转VO
     */
    V toVO(T entity);

    /**
     * VO转实体
     */
    T toEntity(V vo);

    /**
     * 更新实体
     */
    void updateEntity(V vo, @MappingTarget T entity);

    /**
     * 实体列表转VO列表
     */
    List<V> toVOList(List<T> entityList);

    /**
     * VO列表转实体列表
     */
    List<T> toEntityList(List<V> voList);
} 