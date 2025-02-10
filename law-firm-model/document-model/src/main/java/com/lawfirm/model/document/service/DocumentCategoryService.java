package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.DocumentCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 文档分类服务接口
 */
public interface DocumentCategoryService {
    
    /**
     * 创建分类
     */
    DocumentCategory createCategory(DocumentCategory category);
    
    /**
     * 更新分类
     */
    DocumentCategory updateCategory(DocumentCategory category);
    
    /**
     * 删除分类
     */
    void deleteCategory(Long id);
    
    /**
     * 获取分类详情
     */
    DocumentCategory getCategory(Long id);
    
    /**
     * 获取分类树
     */
    List<DocumentCategory> getCategoryTree();
    
    /**
     * 获取子分类
     */
    List<DocumentCategory> getChildren(Long parentId);
    
    /**
     * 移动分类
     */
    void moveCategory(Long categoryId, Long newParentId);
    
    /**
     * 分页查询分类
     */
    Page<DocumentCategory> listCategories(Pageable pageable);
    
    /**
     * 根据编码获取分类
     */
    DocumentCategory getCategoryByCode(String code);
    
    /**
     * 获取分类路径
     */
    List<DocumentCategory> getCategoryPath(Long categoryId);
    
    /**
     * 检查分类编码是否存在
     */
    boolean existsByCode(String code);
    
    /**
     * 获取分类下的文档数量
     */
    long countDocuments(Long categoryId);
} 