package com.lawfirm.common.data.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.common.data.dto.BaseDTO;

import java.util.List;

/**
 * 基础服务接口
 */
public interface BaseService<T extends BaseEntity, D extends BaseDTO> extends IService<T> {

    /**
     * 创建
     */
    D create(D dto);

    /**
     * 更新
     */
    D update(D dto);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 批量删除
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据ID查询
     */
    D findById(Long id);

    /**
     * 分页查询
     */
    PageResult<D> page(Page<T> page, QueryWrapper<T> wrapper);

    /**
     * 列表查询
     */
    List<D> list(QueryWrapper<T> wrapper);

    /**
     * 实体转DTO
     */
    D toDTO(T entity);

    /**
     * DTO转实体
     */
    T toEntity(D dto);

    /**
     * 实体列表转DTO列表
     */
    List<D> toDTOList(List<T> entityList);

    /**
     * DTO列表转实体列表
     */
    List<T> toEntityList(List<D> dtoList);
} 