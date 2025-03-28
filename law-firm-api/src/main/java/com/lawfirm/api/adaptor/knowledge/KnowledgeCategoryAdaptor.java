package com.lawfirm.api.adaptor.knowledge;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.knowledge.entity.KnowledgeCategory;
import com.lawfirm.model.knowledge.service.KnowledgeCategoryService;
import com.lawfirm.model.knowledge.service.convert.KnowledgeConvert;
import com.lawfirm.model.knowledge.vo.KnowledgeCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 知识分类适配器
 * <p>
 * 该适配器负责处理与知识分类相关的所有操作，包括分类的创建、更新、删除、查询等功能。
 * 作为API层与服务层之间的桥梁，转换请求参数并调用KnowledgeCategoryService提供的服务。
 * </p>
 */
@Slf4j
@Component("knowledgeCategoryAdaptor")
public class KnowledgeCategoryAdaptor extends BaseAdaptor {

    private final KnowledgeCategoryService categoryService;
    private final KnowledgeConvert knowledgeConvert;

    @Autowired
    public KnowledgeCategoryAdaptor(@Qualifier("knowledgeCategoryServiceImpl") KnowledgeCategoryService categoryService,
                                    KnowledgeConvert knowledgeConvert) {
        this.categoryService = categoryService;
        this.knowledgeConvert = knowledgeConvert;
    }

    /**
     * 获取分类树
     *
     * @return 分类树
     */
    public List<KnowledgeCategoryVO> getCategoryTree() {
        log.info("获取知识分类树");
        List<KnowledgeCategory> categoryTree = categoryService.getCategoryTree();
        return categoryTree.stream()
                .map(knowledgeConvert::toCategoryVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取子分类
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    public List<KnowledgeCategoryVO> getSubCategories(Long parentId) {
        log.info("获取子分类: parentId={}", parentId);
        List<KnowledgeCategory> subCategories = categoryService.getSubCategories(parentId);
        return subCategories.stream()
                .map(knowledgeConvert::toCategoryVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取分类路径
     *
     * @param categoryId 分类ID
     * @return 分类路径
     */
    public List<KnowledgeCategoryVO> getCategoryPath(Long categoryId) {
        log.info("获取分类路径: categoryId={}", categoryId);
        List<KnowledgeCategory> categoryPath = categoryService.getCategoryPath(categoryId);
        return categoryPath.stream()
                .map(knowledgeConvert::toCategoryVO)
                .collect(Collectors.toList());
    }

    /**
     * 创建分类
     *
     * @param category 分类
     * @return 是否成功
     */
    public boolean createCategory(KnowledgeCategory category) {
        log.info("创建分类: name={}, parentId={}", category.getName(), category.getParentId());
        return categoryService.save(category);
    }

    /**
     * 更新分类
     *
     * @param category 分类
     * @return 是否成功
     */
    public boolean updateCategory(KnowledgeCategory category) {
        log.info("更新分类: id={}, name={}", category.getId(), category.getName());
        return categoryService.updateById(category);
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 是否成功
     */
    public boolean deleteCategory(Long id) {
        log.info("删除分类: id={}", id);
        return categoryService.remove(id);
    }

    /**
     * 获取分类详情
     *
     * @param id 分类ID
     * @return 分类详情
     */
    public KnowledgeCategoryVO getCategory(Long id) {
        log.info("获取分类详情: id={}", id);
        KnowledgeCategory category = categoryService.getById(id);
        return knowledgeConvert.toCategoryVO(category);
    }
} 