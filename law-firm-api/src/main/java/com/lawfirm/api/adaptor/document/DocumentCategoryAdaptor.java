package com.lawfirm.api.adaptor.document;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentCategory;
import com.lawfirm.model.document.service.DocumentCategoryService;
import com.lawfirm.model.document.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文档分类管理适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentCategoryAdaptor extends BaseAdaptor {

    private final DocumentCategoryService documentCategoryService;

    /**
     * 创建文档分类
     */
    public CategoryVO createCategory(CategoryCreateDTO dto) {
        log.info("创建文档分类: {}", dto);
        Long id = documentCategoryService.createCategory(dto);
        return getCategory(id);
    }

    /**
     * 更新文档分类
     */
    public CategoryVO updateCategory(CategoryUpdateDTO dto) {
        log.info("更新文档分类: {}", dto);
        documentCategoryService.updateCategory(dto);
        return getCategory(dto.getId());
    }

    /**
     * 删除文档分类
     */
    public void deleteCategory(Long id) {
        log.info("删除文档分类: {}", id);
        documentCategoryService.deleteCategory(id);
    }

    /**
     * 获取文档分类详情
     */
    public CategoryVO getCategory(Long id) {
        log.info("获取文档分类详情: {}", id);
        return documentCategoryService.getCategory(id);
    }

    /**
     * 获取所有文档分类
     */
    public List<CategoryVO> listCategories(CategoryQueryDTO queryDTO) {
        log.info("获取所有文档分类: {}", queryDTO);
        return documentCategoryService.listCategories(queryDTO);
    }

    /**
     * 分页查询文档分类
     */
    public Page<CategoryVO> pageCategories(CategoryQueryDTO queryDTO) {
        log.info("分页查询文档分类: {}", queryDTO);
        return documentCategoryService.pageCategories(queryDTO);
    }
} 