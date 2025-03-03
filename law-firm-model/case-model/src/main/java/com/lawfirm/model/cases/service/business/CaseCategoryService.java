package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseCategoryDTO;
import com.lawfirm.model.cases.vo.business.CaseCategoryVO;

import java.util.List;

/**
 * 案件分类服务接口
 */
public interface CaseCategoryService {

    /**
     * 创建分类
     *
     * @param categoryDTO 分类信息
     * @return 分类ID
     */
    Long createCategory(CaseCategoryDTO categoryDTO);

    /**
     * 更新分类
     *
     * @param categoryDTO 分类信息
     * @return 是否成功
     */
    boolean updateCategory(CaseCategoryDTO categoryDTO);

    /**
     * 删除分类
     *
     * @param categoryId 分类ID
     * @return 是否成功
     */
    boolean deleteCategory(Long categoryId);

    /**
     * 获取分类详情
     *
     * @param categoryId 分类ID
     * @return 分类详情
     */
    CaseCategoryVO getCategoryDetail(Long categoryId);

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    List<CaseCategoryVO> listAllCategories();

    /**
     * 获取分类树
     *
     * @return 分类树
     */
    List<CaseCategoryVO> getCategoryTree();

    /**
     * 分页查询分类
     *
     * @param keyword 关键词
     * @param status 状态
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseCategoryVO> pageCategories(String keyword, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 获取子分类
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<CaseCategoryVO> listSubCategories(Long parentId);

    /**
     * 移动分类
     *
     * @param categoryId 分类ID
     * @param targetParentId 目标父分类ID
     * @return 是否成功
     */
    boolean moveCategory(Long categoryId, Long targetParentId);

    /**
     * 设置分类状态
     *
     * @param categoryId 分类ID
     * @param status 状态
     * @return 是否成功
     */
    boolean setCategoryStatus(Long categoryId, Integer status);

    /**
     * 设置分类排序
     *
     * @param categoryId 分类ID
     * @param sort 排序值
     * @return 是否成功
     */
    boolean setCategorySort(Long categoryId, Integer sort);

    /**
     * 批量设置分类排序
     *
     * @param categorySorts 分类ID和排序值的映射
     * @return 是否成功
     */
    boolean batchSetCategorySort(List<CaseCategoryDTO> categorySorts);

    /**
     * 检查分类编码是否存在
     *
     * @param categoryCode 分类编码
     * @return 是否存在
     */
    boolean checkCategoryCodeExists(String categoryCode);

    /**
     * 检查分类名称是否存在
     *
     * @param categoryName 分类名称
     * @param excludeId 排除的分类ID
     * @return 是否存在
     */
    boolean checkCategoryNameExists(String categoryName, Long excludeId);

    /**
     * 获取分类的完整路径
     *
     * @param categoryId 分类ID
     * @return 完整路径
     */
    String getCategoryFullPath(Long categoryId);

    /**
     * 统计子分类数量
     *
     * @param categoryId 分类ID
     * @return 数量
     */
    int countSubCategories(Long categoryId);

    /**
     * 统计分类下的案件数量
     *
     * @param categoryId 分类ID
     * @return 数量
     */
    int countCasesByCategory(Long categoryId);
} 