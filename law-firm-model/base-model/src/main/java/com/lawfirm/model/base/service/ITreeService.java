package com.lawfirm.model.base.service;

import com.lawfirm.model.base.entity.TreeEntity;
import com.lawfirm.model.base.query.BaseQuery;

import java.util.List;

/**
 * 树形结构服务接口
 */
public interface ITreeService<T extends TreeEntity> extends BaseService<T> {

    /**
     * 获取子节点
     */
    List<T> getChildren(Long parentId);

    /**
     * 获取所有子节点
     */
    List<T> getAllChildren(Long parentId);

    /**
     * 获取父节点路径
     */
    List<T> getPath(Long id);

    /**
     * 获取树形结构
     */
    List<T> getTree();

    /**
     * 移动节点
     */
    void move(Long id, Long parentId);

    /**
     * 排序
     */
    void sort(Long id, Integer sort);

    /**
     * 查询树形结构
     */
    List<T> findTree(BaseQuery query);

    /**
     * 查询父节点
     */
    List<T> findParents(Long id);
} 