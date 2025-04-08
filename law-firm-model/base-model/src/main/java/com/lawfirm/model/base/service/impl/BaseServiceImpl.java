package com.lawfirm.model.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 基础服务实现类
 * 继承MyBatis-Plus的ServiceImpl，并实现BaseService接口
 * 提供通用的CRUD操作实现
 *
 * @param <M> Mapper类型
 * @param <T> 实体类型
 */
@Slf4j
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends ModelBaseEntity> 
        extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 根据ID查询
     */
    @Override
    public T getById(Long id) {
        log.debug("根据ID查询: {}", id);
        return super.getById(id);
    }

    /**
     * 查询列表
     */
    @Override
    public List<T> list(QueryWrapper<T> wrapper) {
        log.debug("查询列表");
        return super.list(wrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<T> page(Page<T> page, QueryWrapper<T> wrapper) {
        log.debug("分页查询: 页码={}, 页大小={}", page.getCurrent(), page.getSize());
        return super.page(page, wrapper);
    }

    /**
     * 查询总数
     */
    @Override
    public long count(QueryWrapper<T> wrapper) {
        log.debug("查询总数");
        return super.count(wrapper);
    }

    /**
     * 保存实体
     */
    @Override
    public boolean save(T entity) {
        log.debug("保存实体: {}", entity);
        return super.save(entity);
    }

    /**
     * 批量保存实体
     */
    @Override
    public boolean saveBatch(List<T> entities) {
        log.debug("批量保存实体, 数量: {}", entities.size());
        return super.saveBatch(entities);
    }

    /**
     * 检查是否存在
     */
    @Override
    public boolean exists(QueryWrapper<T> wrapper) {
        log.debug("检查实体是否存在");
        return count(wrapper) > 0;
    }

    /**
     * 更新实体
     * 转发到updateById方法
     */
    @Override
    public boolean update(T entity) {
        log.debug("更新实体: {}", entity);
        return updateById(entity);
    }

    /**
     * 批量更新实体
     * 转发到updateBatchById方法
     */
    @Override
    public boolean updateBatch(List<T> entities) {
        log.debug("批量更新实体, 数量: {}", entities.size());
        return updateBatchById(entities);
    }

    /**
     * 删除实体
     * 转发到removeById方法
     */
    @Override
    public boolean remove(Long id) {
        log.debug("删除实体, ID: {}", id);
        return removeById(id);
    }

    /**
     * 批量删除实体
     * 转发到removeByIds方法
     */
    @Override
    public boolean removeBatch(List<Long> ids) {
        log.debug("批量删除实体, ID列表: {}", ids);
        return removeByIds(ids);
    }

    @Override
    public String getCurrentUsername() {
        return SecurityUtils.getUsername();
    }

    @Override
    public Long getCurrentUserId() {
        return SecurityUtils.getUserId();
    }

    @Override
    public Long getCurrentTenantId() {
        // TODO: 从SecurityContext中获取租户ID
        return null;
    }
} 