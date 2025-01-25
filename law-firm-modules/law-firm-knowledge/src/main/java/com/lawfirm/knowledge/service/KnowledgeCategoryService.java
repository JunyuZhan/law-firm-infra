package com.lawfirm.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.knowledge.entity.KnowledgeCategory;

import java.util.List;

/**
 * 知识分类服务接口
 */
public interface KnowledgeCategoryService extends IService<KnowledgeCategory> {

    /**
     * 创建分类
     *
     * @param category 分类信息
     * @return 分类ID
     */
    Long createCategory(KnowledgeCategory category);

    /**
     * 更新分类
     *
     * @param category 分类信息
     */
    void updateCategory(KnowledgeCategory category);

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    void deleteCategory(Long id);

    /**
     * 查询子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<KnowledgeCategory> listChildren(Long parentId);

    /**
     * 查询分类路径
     *
     * @param categoryId 分类ID
     * @return 分类路径
     */
    List<KnowledgeCategory> getPath(Long categoryId);

    /**
     * 查询分类树
     *
     * @return 分类树
     */
    List<KnowledgeCategory> getTree();

    /**
     * 移动分类
     *
     * @param id       分类ID
     * @param parentId 父分类ID
     */
    void moveCategory(Long id, Long parentId);

    /**
     * 调整分类顺序
     *
     * @param id       分类ID
     * @param orderNum 排序号
     */
    void reorderCategory(Long id, Integer orderNum);
} 