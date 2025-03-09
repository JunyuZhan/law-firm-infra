package com.lawfirm.model.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.base.entity.ModelBaseEntity;

import java.util.List;

/**
 * 基础服务接口
 * 继承MyBatis-Plus的IService接口，并添加一些扩展方法
 */
public interface BaseService<T extends ModelBaseEntity> extends IService<T> {

    /**
     * 保存
     */
    boolean save(T entity);

    /**
     * 批量保存
     */
    boolean saveBatch(List<T> entities);

    /**
     * 更新实体
     * 与IService中的updateById保持功能上一致
     */
    boolean update(T entity);

    /**
     * 批量更新实体
     * 与IService中的updateBatchById保持功能上一致
     */
    boolean updateBatch(List<T> entities);

    /**
     * 删除
     * 与IService中的removeById保持功能上一致
     */
    boolean remove(Long id);

    /**
     * 批量删除
     * 与IService中的removeByIds保持功能上一致
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
     * 这是对IService的扩展方法
     */
    boolean exists(QueryWrapper<T> wrapper);
} 