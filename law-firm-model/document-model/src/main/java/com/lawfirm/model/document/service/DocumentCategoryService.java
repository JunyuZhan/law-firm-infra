package com.lawfirm.model.document.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentCategory;
import com.lawfirm.model.document.vo.CategoryVO;

import java.util.List;

/**
 * 文档分类服务接口
 */
public interface DocumentCategoryService extends IService<DocumentCategory> {
    
    /**
     * 创建分类
     * 
     * @param createDTO 创建参数
     * @return 分类ID
     */
    Long createCategory(CategoryCreateDTO createDTO);
    
    /**
     * 更新分类
     * 
     * @param updateDTO 更新参数
     */
    void updateCategory(CategoryUpdateDTO updateDTO);
    
    /**
     * 删除分类
     * 
     * @param id 分类ID
     */
    void deleteCategory(Long id);
    
    /**
     * 获取分类
     * 
     * @param id 分类ID
     * @return 分类详情
     */
    CategoryVO getCategory(Long id);
    
    /**
     * 列出分类
     * 
     * @param queryDTO 查询参数
     * @return 分类列表
     */
    List<CategoryVO> listCategories(CategoryQueryDTO queryDTO);
    
    /**
     * 分页查询分类
     * 
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<CategoryVO> pageCategories(CategoryQueryDTO queryDTO);
} 