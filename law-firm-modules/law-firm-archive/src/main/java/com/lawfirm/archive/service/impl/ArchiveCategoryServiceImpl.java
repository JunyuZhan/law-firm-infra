package com.lawfirm.archive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.archive.mapper.ArchiveCategoryMapper;
import com.lawfirm.archive.model.entity.ArchiveCategory;
import com.lawfirm.archive.service.ArchiveCategoryService;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 档案分类服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArchiveCategoryServiceImpl implements ArchiveCategoryService {

    private final ArchiveCategoryMapper categoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArchiveCategory createCategory(ArchiveCategory category) {
        // 检查编码是否存在
        if (checkCategoryCodeExists(category.getCategoryCode())) {
            throw new BusinessException("分类编码已存在");
        }
        
        // 检查名称是否存在
        if (checkCategoryNameExists(category.getCategoryName())) {
            throw new BusinessException("分类名称已存在");
        }
        
        // 设置层级
        if (category.getParentId() == null || category.getParentId() == 0) {
            category.setLevel(1);
            category.setParentId(0L);
        } else {
            ArchiveCategory parent = getCategoryById(category.getParentId());
            if (parent == null) {
                throw new BusinessException("父分类不存在");
            }
            category.setLevel(parent.getLevel() + 1);
        }
        
        categoryMapper.insert(category);
        return category;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArchiveCategory updateCategory(ArchiveCategory category) {
        // 检查分类是否存在
        ArchiveCategory existingCategory = getCategoryById(category.getId());
        if (existingCategory == null) {
            throw new BusinessException("分类不存在");
        }
        
        // 检查编码是否重复
        if (!existingCategory.getCategoryCode().equals(category.getCategoryCode()) 
                && checkCategoryCodeExists(category.getCategoryCode())) {
            throw new BusinessException("分类编码已存在");
        }
        
        // 检查名称是否重复
        if (!existingCategory.getCategoryName().equals(category.getCategoryName()) 
                && checkCategoryNameExists(category.getCategoryName())) {
            throw new BusinessException("分类名称已存在");
        }
        
        categoryMapper.updateById(category);
        return category;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        // 检查是否存在子分类
        if (hasChildCategories(id)) {
            throw new BusinessException("存在子分类，无法删除");
        }
        
        categoryMapper.deleteById(id);
    }

    @Override
    public ArchiveCategory getCategoryById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<ArchiveCategory> getAllCategories() {
        return categoryMapper.selectList(null);
    }

    @Override
    public List<ArchiveCategory> getChildCategories(Long parentId) {
        LambdaQueryWrapper<ArchiveCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveCategory::getParentId, parentId)
                .orderByAsc(ArchiveCategory::getSortOrder);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public List<ArchiveCategory> getCategoryTree() {
        // 获取所有分类
        List<ArchiveCategory> allCategories = getAllCategories();
        
        // 构建树形结构
        Map<Long, List<ArchiveCategory>> parentIdMap = allCategories.stream()
                .collect(Collectors.groupingBy(ArchiveCategory::getParentId));
        
        // 获取顶级分类
        List<ArchiveCategory> rootCategories = parentIdMap.getOrDefault(0L, new ArrayList<>());
        
        // 递归设置子分类
        rootCategories.forEach(root -> setChildren(root, parentIdMap));
        
        return rootCategories;
    }

    @Override
    public boolean checkCategoryCodeExists(String categoryCode) {
        LambdaQueryWrapper<ArchiveCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveCategory::getCategoryCode, categoryCode);
        return categoryMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean checkCategoryNameExists(String categoryName) {
        LambdaQueryWrapper<ArchiveCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveCategory::getCategoryName, categoryName);
        return categoryMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<ArchiveCategory> getCategoryPath(Long categoryId) {
        List<ArchiveCategory> path = new ArrayList<>();
        ArchiveCategory current = getCategoryById(categoryId);
        
        while (current != null) {
            path.add(0, current);
            if (current.getParentId() == 0) {
                break;
            }
            current = getCategoryById(current.getParentId());
        }
        
        return path;
    }

    /**
     * 检查是否存在子分类
     */
    private boolean hasChildCategories(Long parentId) {
        LambdaQueryWrapper<ArchiveCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArchiveCategory::getParentId, parentId);
        return categoryMapper.selectCount(wrapper) > 0;
    }

    /**
     * 递归设置子分类
     */
    private void setChildren(ArchiveCategory parent, Map<Long, List<ArchiveCategory>> parentIdMap) {
        List<ArchiveCategory> children = parentIdMap.get(parent.getId());
        if (children != null) {
            children.forEach(child -> setChildren(child, parentIdMap));
        }
    }
} 