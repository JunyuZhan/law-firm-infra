package com.lawfirm.api.adaptor.document;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentCategory;
import com.lawfirm.model.document.service.DocumentCategoryService;
import com.lawfirm.model.document.vo.category.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档分类管理适配器
 */
@Component
public class DocumentCategoryAdaptor extends BaseAdaptor {

    @Autowired
    private DocumentCategoryService documentCategoryService;

    /**
     * 创建文档分类
     */
    public CategoryVO createCategory(CategoryCreateDTO dto) {
        Long id = documentCategoryService.createCategory(dto);
        return getCategory(id);
    }

    /**
     * 更新文档分类
     */
    public CategoryVO updateCategory(CategoryUpdateDTO dto) {
        documentCategoryService.updateCategory(dto);
        return getCategory(dto.getId());
    }

    /**
     * 删除文档分类
     */
    public void deleteCategory(Long id) {
        documentCategoryService.deleteCategory(id);
    }

    /**
     * 获取文档分类详情
     */
    public CategoryVO getCategory(Long id) {
        return documentCategoryService.getCategory(id);
    }

    /**
     * 获取所有文档分类
     */
    public List<CategoryVO> listCategories(CategoryQueryDTO queryDTO) {
        return documentCategoryService.listCategories(queryDTO);
    }

    /**
     * 分页查询文档分类
     */
    public List<CategoryVO> pageCategories(CategoryQueryDTO queryDTO) {
        return documentCategoryService.pageCategories(queryDTO);
    }
} 