package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.entity.KnowledgeCategory;

import java.util.List;

/**
 * 知识分类Service接口
 */
public interface KnowledgeCategoryService extends BaseService<KnowledgeCategory> {

    /**
     * 获取完整的分类树
     */
    List<KnowledgeCategory> getCategoryTree();

    /**
     * 获取指定分类下的子分类
     */
    List<KnowledgeCategory> getSubCategories(Long parentId);

    /**
     * 获取分类路径
     */
    List<KnowledgeCategory> getCategoryPath(Long categoryId);
} 