package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.dto.category.CategoryCreateDTO;
import com.lawfirm.model.knowledge.dto.category.CategoryQueryDTO;
import com.lawfirm.model.knowledge.entity.Category;
import com.lawfirm.model.knowledge.vo.CategoryVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService extends BaseService<Category> {

    /**
     * 创建分类
     */
    CategoryVO create(CategoryCreateDTO createDTO);

    /**
     * 更新分类
     */
    CategoryVO update(CategoryCreateDTO updateDTO);

    /**
     * 分页查询分类
     */
    Page<CategoryVO> query(CategoryQueryDTO queryDTO);

    /**
     * 获取分类详情
     */
    CategoryVO getDetail(Long id);

    /**
     * 获取分类树
     */
    List<CategoryVO> getTree();

    /**
     * 获取指定分类的子分类
     */
    List<CategoryVO> getChildren(Long id);

    /**
     * 获取指定分类的父分类路径
     */
    List<CategoryVO> getParentPath(Long id);

    /**
     * 检查分类编码是否可用
     */
    boolean checkCodeAvailable(String code);

    /**
     * 移动分类
     */
    void move(Long id, Long targetParentId);

    /**
     * 更新分类排序
     */
    void updateSort(Long id, Integer weight);

    /**
     * 批量更新分类排序
     */
    void batchUpdateSort(List<Long> ids, List<Integer> weights);

    /**
     * 获取导航分类
     */
    List<CategoryVO> getNavCategories();

    /**
     * 获取热门分类
     */
    List<CategoryVO> getHotCategories(int limit);

    /**
     * 统计分类文章数
     */
    void updateArticleCount(Long id);

    /**
     * 批量统计分类文章数
     */
    void batchUpdateArticleCount(List<Long> ids);

    /**
     * 获取分类的最新文章
     */
    List<CategoryVO> getCategoryWithLatestArticles(int categoryLimit, int articleLimit);
} 