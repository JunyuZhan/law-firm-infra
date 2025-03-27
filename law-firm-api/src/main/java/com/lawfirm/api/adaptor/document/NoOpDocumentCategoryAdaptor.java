package com.lawfirm.api.adaptor.document;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 文档分类管理适配器空实现，当存储功能禁用时使用
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = false)
public class NoOpDocumentCategoryAdaptor extends BaseAdaptor {

    /**
     * 创建文档分类
     */
    public CategoryVO createCategory(CategoryCreateDTO dto) {
        log.warn("存储功能已禁用，忽略创建文档分类操作: {}", dto);
        return null;
    }

    /**
     * 更新文档分类
     */
    public CategoryVO updateCategory(CategoryUpdateDTO dto) {
        log.warn("存储功能已禁用，忽略更新文档分类操作: {}", dto);
        return null;
    }

    /**
     * 删除文档分类
     */
    public void deleteCategory(Long id) {
        log.warn("存储功能已禁用，忽略删除文档分类操作: {}", id);
    }

    /**
     * 获取文档分类详情
     */
    public CategoryVO getCategory(Long id) {
        log.warn("存储功能已禁用，忽略获取文档分类详情操作: {}", id);
        return null;
    }

    /**
     * 获取所有文档分类
     */
    public List<CategoryVO> listCategories(CategoryQueryDTO queryDTO) {
        log.warn("存储功能已禁用，忽略获取所有文档分类操作: {}", queryDTO);
        return Collections.emptyList();
    }

    /**
     * 分页查询文档分类
     */
    public Page<CategoryVO> pageCategories(CategoryQueryDTO queryDTO) {
        log.warn("存储功能已禁用，忽略分页查询文档分类操作: {}", queryDTO);
        return new Page<>();
    }
} 