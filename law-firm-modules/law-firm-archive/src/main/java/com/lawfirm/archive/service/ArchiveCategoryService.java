package com.lawfirm.archive.service;

import com.lawfirm.archive.model.entity.ArchiveCategory;
import java.util.List;

/**
 * 档案分类服务接口
 */
public interface ArchiveCategoryService {

    /**
     * 创建分类
     */
    ArchiveCategory createCategory(ArchiveCategory category);

    /**
     * 更新分类
     */
    ArchiveCategory updateCategory(ArchiveCategory category);

    /**
     * 删除分类
     */
    void deleteCategory(Long id);

    /**
     * 获取分类详情
     */
    ArchiveCategory getCategoryById(Long id);

    /**
     * 获取所有分类
     */
    List<ArchiveCategory> getAllCategories();

    /**
     * 获取子分类
     */
    List<ArchiveCategory> getChildCategories(Long parentId);

    /**
     * 获取分类树
     */
    List<ArchiveCategory> getCategoryTree();

    /**
     * 检查分类编码是否存在
     */
    boolean checkCategoryCodeExists(String categoryCode);

    /**
     * 检查分类名称是否存在
     */
    boolean checkCategoryNameExists(String categoryName);

    /**
     * 获取分类路径
     */
    List<ArchiveCategory> getCategoryPath(Long categoryId);
} 