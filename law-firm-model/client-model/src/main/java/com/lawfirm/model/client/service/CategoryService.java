package com.lawfirm.model.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.client.entity.common.ClientCategory;

import java.util.List;

/**
 * 客户分类服务接口
 */
public interface CategoryService extends IService<ClientCategory> {
    
    /**
     * 创建分类
     * @param category 分类信息
     * @return 分类ID
     */
    Long createCategory(ClientCategory category);
    
    /**
     * 更新分类
     * @param category 分类信息
     */
    void updateCategory(ClientCategory category);
    
    /**
     * 删除分类
     * @param id 分类ID
     * @return 是否成功
     */
    boolean deleteCategory(Long id);
    
    /**
     * 获取分类详情
     * @param id 分类ID
     * @return 分类信息
     */
    ClientCategory getCategory(Long id);
    
    /**
     * 根据父级ID获取子分类列表
     * @param parentId 父级ID
     * @return 子分类列表
     */
    List<ClientCategory> listByParentId(Long parentId);
    
    /**
     * 获取分类树形结构
     * @return 树形分类列表
     */
    List<ClientCategory> getCategoryTree();
    
    /**
     * 获取所有子分类ID
     * @param id 分类ID
     * @return 子分类ID列表
     */
    List<Long> getAllChildrenIds(Long id);
} 