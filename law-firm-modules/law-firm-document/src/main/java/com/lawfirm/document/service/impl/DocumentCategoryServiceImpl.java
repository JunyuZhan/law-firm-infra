package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.document.manager.security.SecurityManager;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentCategory;
import com.lawfirm.model.document.mapper.DocumentCategoryMapper;
import com.lawfirm.model.document.service.DocumentCategoryService;
import com.lawfirm.model.document.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.document.exception.DocumentException;

import java.util.List;

/**
 * 文档分类服务实现类
 */
@Slf4j
@Service("documentCategoryServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "true", matchIfMissing = true)
public class DocumentCategoryServiceImpl extends BaseServiceImpl<DocumentCategoryMapper, DocumentCategory> implements DocumentCategoryService {

    private final SecurityManager securityManager;
    
    public DocumentCategoryServiceImpl(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(CategoryCreateDTO createDTO) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("创建分类");
        }

        // TODO: 创建分类记录
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(CategoryUpdateDTO updateDTO) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("更新分类");
        }

        // TODO: 更新分类记录
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("删除分类");
        }

        // TODO: 删除分类记录
    }

    @Override
    public CategoryVO getCategory(Long id) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查看分类");
        }

        // TODO: 获取分类详情
        return null;
    }

    @Override
    public List<CategoryVO> listCategories(CategoryQueryDTO queryDTO) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查询分类列表");
        }

        // TODO: 查询分类列表
        return null;
    }

    @Override
    public Page<CategoryVO> pageCategories(CategoryQueryDTO queryDTO) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查询分类列表");
        }

        // TODO: 分页查询分类
        return null;
    }
}
