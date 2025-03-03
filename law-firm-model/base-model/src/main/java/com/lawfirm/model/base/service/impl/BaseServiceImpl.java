package com.lawfirm.model.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 基础服务实现类
 * 提供通用的CRUD操作实现
 *
 * @param <M> Mapper类型
 * @param <T> 实体类型
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends ModelBaseEntity> implements BaseService<T> {

    /**
     * MyBatis Plus的基础Mapper
     */
    @Autowired
    protected final M baseMapper;

    /**
     * 保存实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T entity) {
        log.debug("保存实体: {}", entity);
        return baseMapper.insert(entity) > 0;
    }

    /**
     * 批量保存实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(List<T> entities) {
        log.debug("批量保存实体, 数量: {}", entities.size());
        for (T entity : entities) {
            baseMapper.insert(entity);
        }
        return true;
    }

    /**
     * 更新实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(T entity) {
        log.debug("更新实体: {}", entity);
        return baseMapper.updateById(entity) > 0;
    }

    /**
     * 批量更新实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatch(List<T> entities) {
        log.debug("批量更新实体, 数量: {}", entities.size());
        for (T entity : entities) {
            baseMapper.updateById(entity);
        }
        return true;
    }

    /**
     * 删除实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
        log.debug("删除实体, ID: {}", id);
        return baseMapper.deleteById(id) > 0;
    }

    /**
     * 批量删除实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeBatch(List<Long> ids) {
        log.debug("批量删除实体, ID列表: {}", ids);
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 根据ID查询
     */
    @Override
    public T getById(Long id) {
        log.debug("根据ID查询实体: {}", id);
        return baseMapper.selectById(id);
    }

    /**
     * 查询列表
     */
    @Override
    public List<T> list(QueryWrapper<T> wrapper) {
        log.debug("查询实体列表");
        return baseMapper.selectList(wrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<T> page(Page<T> page, QueryWrapper<T> wrapper) {
        log.debug("分页查询实体列表, 页码: {}, 每页大小: {}", page.getCurrent(), page.getSize());
        return baseMapper.selectPage(page, wrapper);
    }

    /**
     * 查询总数
     */
    @Override
    public long count(QueryWrapper<T> wrapper) {
        log.debug("查询实体总数");
        return baseMapper.selectCount(wrapper);
    }

    /**
     * 检查是否存在
     */
    @Override
    public boolean exists(QueryWrapper<T> wrapper) {
        log.debug("检查实体是否存在");
        return baseMapper.selectCount(wrapper) > 0;
    }
} 