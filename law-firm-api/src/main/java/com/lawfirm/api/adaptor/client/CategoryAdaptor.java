package com.lawfirm.api.adaptor.client;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.client.service.CategoryService;
import com.lawfirm.model.client.entity.common.ClientCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 客户分类适配器
 */
@Component
@RequiredArgsConstructor
public class CategoryAdaptor extends BaseAdaptor {

    private final CategoryService categoryService;

    /**
     * 获取分类树结构
     */
    public List<ClientCategory> getCategoryTree() {
        return categoryService.getCategoryTree();
    }

    /**
     * 获取分类列表（平铺结构）
     */
    public List<ClientCategory> getCategoryList() {
        return categoryService.list();
    }

    /**
     * 获取分类详情
     */
    public ClientCategory getCategoryDetail(Long id) {
        return categoryService.getCategory(id);
    }

    /**
     * 新增分类
     */
    public Long addCategory(ClientCategory category) {
        return categoryService.createCategory(category);
    }

    /**
     * 修改分类
     */
    public void updateCategory(ClientCategory category) {
        categoryService.updateCategory(category);
    }

    /**
     * 删除分类
     */
    public boolean deleteCategory(Long id) {
        return categoryService.deleteCategory(id);
    }

    /**
     * 获取子分类
     */
    public List<ClientCategory> getCategoryChildren(Long parentId) {
        return categoryService.listByParentId(parentId);
    }
} 