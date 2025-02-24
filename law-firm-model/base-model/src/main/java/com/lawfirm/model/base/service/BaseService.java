package com.lawfirm.model.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.entity.ModelBaseEntity;

import java.util.List;

/**
 * 基础服务接口
 */
public interface BaseService<T extends ModelBaseEntity> {

    /**
     * 保存
     */
    boolean save(T entity);

    /**
     * 批量保存
     */
    boolean saveBatch(List<T> entities);

    /**
     * 更新
     */
    boolean update(T entity);

    /**
     * 批量更新
     */
    boolean updateBatch(List<T> entities);

    /**
     * 删除
     */
    boolean remove(Long id);

    /**
     * 批量删除
     */
    boolean removeBatch(List<Long> ids);

    /**
     * 根据ID查询
     */
    T getById(Long id);

    /**
     * 查询列表
     */
    List<T> list(QueryWrapper<T> wrapper);

    /**
     * 分页查询
     */
    Page<T> page(Page<T> page, QueryWrapper<T> wrapper);

    /**
     * 查询总数
     */
    long count(QueryWrapper<T> wrapper);

    /**
     * 检查是否存在
     */
    boolean exists(QueryWrapper<T> wrapper);
} 