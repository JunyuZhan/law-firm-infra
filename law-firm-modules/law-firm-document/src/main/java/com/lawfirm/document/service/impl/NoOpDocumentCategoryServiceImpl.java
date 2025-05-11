package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentCategory;
import com.lawfirm.model.document.service.DocumentCategoryService;
import com.lawfirm.model.document.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 文档分类服务空实现类，当存储功能禁用时使用
 */
@Slf4j
@Service("documentCategoryServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "false", matchIfMissing = false)
public class NoOpDocumentCategoryServiceImpl extends BaseServiceImpl<BaseMapper<DocumentCategory>, DocumentCategory> implements DocumentCategoryService {

    @Override
    public Long createCategory(CategoryCreateDTO createDTO) {
        log.warn("存储功能已禁用，忽略创建文档分类操作");
        return null;
    }

    @Override
    public void updateCategory(CategoryUpdateDTO updateDTO) {
        log.warn("存储功能已禁用，忽略更新文档分类操作");
    }

    @Override
    public void deleteCategory(Long id) {
        log.warn("存储功能已禁用，忽略删除文档分类操作");
    }

    @Override
    public CategoryVO getCategory(Long id) {
        log.warn("存储功能已禁用，忽略获取文档分类操作");
        return null;
    }

    @Override
    public List<CategoryVO> listCategories(CategoryQueryDTO queryDTO) {
        log.warn("存储功能已禁用，忽略查询文档分类列表操作");
        return Collections.emptyList();
    }

    @Override
    public Page<CategoryVO> pageCategories(CategoryQueryDTO queryDTO) {
        log.warn("存储功能已禁用，忽略分页查询文档分类操作");
        return new Page<>();
    }
} 