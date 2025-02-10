package com.lawfirm.common.data.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.core.entity.BaseEntity;
import com.lawfirm.common.data.vo.BaseVO;

import java.util.List;

/**
 * 基础服务接口
 *
 * @param <T> 实体类型
 * @param <V> VO类型
 */
public interface BaseService<T extends BaseEntity<T>, V> extends IService<T> {

    /**
     * 分页查询
     */
    PageResult<V> pageVO(Page<T> page, QueryWrapper<T> wrapper);

    /**
     * 列表查询
     */
    List<V> listVO(QueryWrapper<T> wrapper);

    /**
     * 创建
     */
    V create(V vo);

    /**
     * 更新
     */
    V update(V vo);

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
    V findById(Long id);

    /**
     * 根据ID获取VO对象
     */
    V getVOById(Long id);

    /**
     * 获取所有VO对象列表
     */
    List<V> listVO();

    /**
     * 检查ID是否存在
     */
    boolean exists(Long id);

    /**
     * 实体对象转VO
     */
    V entityToVO(T entity);

    /**
     * VO转实体对象
     */
    T voToEntity(V vo);

    /**
     * 实体列表转VO列表
     */
    List<V> entityListToVOList(List<T> entityList);

    /**
     * VO列表转实体列表
     */
    List<T> voListToEntityList(List<V> voList);

    /**
     * 将实体转换为DTO
     */
    V toDTO(T entity);

    /**
     * 将DTO转换为实体
     */
    T toEntity(V dto);

    /**
     * 重写removeById方法，避免冲突
     */
    @Override
    default boolean removeById(T entity) {
        if (entity == null) {
            return false;
        }
        return removeById(entity.getId());
    }
} 