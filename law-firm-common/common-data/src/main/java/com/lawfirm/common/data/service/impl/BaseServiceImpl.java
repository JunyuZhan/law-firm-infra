package com.lawfirm.common.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.common.data.vo.BaseVO;
import com.lawfirm.common.data.processor.EntityProcessor;
import com.lawfirm.common.data.service.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基础服务实现类
 *
 * @param <M> Mapper类型
 * @param <T> 实体类型
 * @param <V> VO类型
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity, V extends BaseVO> 
    extends ServiceImpl<M, T> implements BaseService<T, V> {

    protected EntityProcessor<T> entityProcessor;

    public void setEntityProcessor(EntityProcessor<T> entityProcessor) {
        this.entityProcessor = entityProcessor;
    }

    @Override
    public PageResult<V> pageVO(Page<T> page, QueryWrapper<T> wrapper) {
        IPage<T> result = page(page, wrapper);
        List<V> voList = entityListToVOList(result.getRecords());
        return new PageResult<>(voList, result.getTotal());
    }

    @Override
    public List<V> listVO(QueryWrapper<T> wrapper) {
        return entityListToVOList(list(wrapper));
    }

    @Override
    public V create(V vo) {
        T entity = voToEntity(vo);
        save(entity);
        return entityToVO(entity);
    }

    @Override
    public V update(V vo) {
        T entity = voToEntity(vo);
        updateById(entity);
        return entityToVO(entity);
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
    public V findById(Long id) {
        return entityToVO(getById(id));
    }

    @Override
    public List<V> listVO() {
        return entityListToVOList(list());
    }

    @Override
    public V getVOById(Long id) {
        return entityToVO(getById(id));
    }

    @Override
    public boolean exists(Long id) {
        return getById(id) != null;
    }

    @Override
    public V entityToVO(T entity) {
        if (entity == null) {
            return null;
        }
        V vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public T voToEntity(V vo) {
        if (vo == null) {
            return null;
        }
        T entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    public List<V> entityListToVOList(List<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        return entityList.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<T> voListToEntityList(List<V> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return Collections.emptyList();
        }
        return voList.stream()
                .map(this::voToEntity)
                .collect(Collectors.toList());
    }

    /**
     * 创建实体对象
     */
    protected abstract T createEntity();

    /**
     * 创建VO对象
     */
    protected abstract V createVO();
} 