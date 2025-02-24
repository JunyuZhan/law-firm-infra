package com.lawfirm.model.cases.service.business;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.vo.business.CategoryTreeVO;

import java.util.List;

/**
 * 案件分类服务接口
 */
public interface CaseCategoryService {

    /**
     * 创建案件分类
     *
     * @param name 分类名称
     * @param description 分类描述
     * @param parentId 父分类ID
     * @return 分类ID
     */
    Long createCategory(String name, String description, Long parentId);

    /**
     * 更新案件分类
     *
     * @param categoryId 分类ID
     * @param name 分类名称
     * @param description 分类描述
     * @return 是否成功
     */
    Boolean updateCategory(Long categoryId, String name, String description);

    /**
     * 删除案件分类
     *
     * @param categoryId 分类ID
     * @return 是否成功
     */
    Boolean deleteCategory(Long categoryId);

    /**
     * 获取分类树
     *
     * @return 分类树结构
     */
    List<CategoryTreeVO> getCategoryTree();

    /**
     * 移动分类
     *
     * @param categoryId 分类ID
     * @param targetParentId 目标父分类ID
     * @return 是否成功
     */
    Boolean moveCategory(Long categoryId, Long targetParentId);

    /**
     * 获取分类详情
     *
     * @param categoryId 分类ID
     * @return 分类详情
     */
    CategoryTreeVO getCategoryDetail(Long categoryId);

    /**
     * 获取子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<CategoryTreeVO> getSubCategories(Long parentId);

    /**
     * 检查分类名称是否存在
     *
     * @param name 分类名称
     * @param parentId 父分类ID
     * @return 是否存在
     */
    Boolean checkCategoryNameExists(String name, Long parentId);

    /**
     * 批量移动分类
     *
     * @param categoryIds 分类ID列表
     * @param targetParentId 目标父分类ID
     * @return 是否成功
     */
    Boolean batchMoveCategories(List<Long> categoryIds, Long targetParentId);

    /**
     * 获取分类路径
     *
     * @param categoryId 分类ID
     * @return 分类路径（从根节点到当前节点）
     */
    List<CategoryTreeVO> getCategoryPath(Long categoryId);

    /**
     * 搜索分类
     *
     * @param keyword 关键词
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<CategoryTreeVO> searchCategories(String keyword, Integer pageNum, Integer pageSize);
} 