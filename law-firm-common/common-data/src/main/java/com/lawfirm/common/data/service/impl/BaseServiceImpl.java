package com.lawfirm.common.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.common.data.service.BaseService;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基础服务实现类
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity, D extends BaseDTO> 
        extends ServiceImpl<M, T> implements BaseService<T, D> {

    @Override
    public D create(D dto) {
        T entity = toEntity(dto);
        save(entity);
        return toDTO(entity);
    }

    @Override
    public D update(D dto) {
        T entity = toEntity(dto);
        updateById(entity);
        return toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        removeByIds(ids);
    }

    @Override
    public D findById(Long id) {
        return toDTO(getById(id));
    }

    @Override
    public PageResult<D> page(Page<T> page, QueryWrapper<T> wrapper) {
        Page<T> result = super.page(page, wrapper);
        List<D> records = toDTOList(result.getRecords());
        return PageResult.of(records, result.getTotal());
    }

    @Override
    public List<D> list(QueryWrapper<T> wrapper) {
        List<T> entityList = super.list(wrapper);
        return toDTOList(entityList);
    }

    @Override
    public D toDTO(T entity) {
        if (entity == null) {
            return null;
        }
        D dto = createDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public T toEntity(D dto) {
        if (dto == null) {
            return null;
        }
        T entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public List<D> toDTOList(List<T> entityList) {
        if (entityList == null) {
            return new ArrayList<>();
        }
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<T> toEntityList(List<D> dtoList) {
        if (dtoList == null) {
            return new ArrayList<>();
        }
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * 创建DTO实例
     */
    protected abstract D createDTO();

    /**
     * 创建实体实例
     */
    protected abstract T createEntity();
} 