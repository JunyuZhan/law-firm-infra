package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.knowledge.service.AuditIntegrationService;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.knowledge.entity.KnowledgeCategory;
import com.lawfirm.model.knowledge.mapper.KnowledgeCategoryMapper;
import com.lawfirm.model.knowledge.mapper.KnowledgeMapper;
import com.lawfirm.model.knowledge.service.KnowledgeCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lawfirm.knowledge.exception.KnowledgeException;

/**
 * 知识分类服务实现类
 */
@Slf4j
@Service("knowledgeCategoryServiceImpl")
public class KnowledgeCategoryServiceImpl extends BaseServiceImpl<KnowledgeCategoryMapper, KnowledgeCategory> implements KnowledgeCategoryService {
    
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    
    @Autowired
    @Qualifier("knowledgeAuditService")
    private AuditIntegrationService auditService;

    /**
     * 获取完整的分类树
     */
    @Override
    public List<KnowledgeCategory> getCategoryTree() {
        // 查询所有分类
        List<KnowledgeCategory> allCategories = list();
        
        // 构建分类树
        List<KnowledgeCategory> rootCategories = allCategories.stream()
                .filter(category -> category.getParentId() == 0 || category.getParentId() == null)
                .collect(Collectors.toList());
                
        rootCategories.forEach(root -> {
            buildChildrenTree(root, allCategories);
        });
        
        return rootCategories;
    }

    /**
     * 获取指定分类下的子分类
     */
    @Override
    public List<KnowledgeCategory> getSubCategories(Long parentId) {
        if (parentId == null) {
            return new ArrayList<>();
        }
        
        LambdaQueryWrapper<KnowledgeCategory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeCategory::getParentId, parentId);
        wrapper.orderByAsc(KnowledgeCategory::getSort);
        
        return list(wrapper);
    }

    /**
     * 获取分类路径
     */
    @Override
    public List<KnowledgeCategory> getCategoryPath(Long categoryId) {
        if (categoryId == null) {
            return new ArrayList<>();
        }
        
        List<KnowledgeCategory> path = new ArrayList<>();
        KnowledgeCategory category = getById(categoryId);
        
        if (category == null) {
            return path;
        }
        
        path.add(category);
        
        // 递归获取父分类
        Long parentId = category.getParentId();
        while (parentId != null && parentId != 0) {
            KnowledgeCategory parent = getById(parentId);
            if (parent == null) {
                break;
            }
            
            path.add(0, parent); // 将父分类添加到路径开头
            parentId = parent.getParentId();
        }
        
        return path;
    }

    /**
     * 保存分类
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(KnowledgeCategory entity) {
        // 设置创建时间等基本属性由BaseServiceImpl处理
        
        // 对于新增分类，设置排序值
        if (entity.getId() == null) {
            // 设置排序值为同级分类中的最大值+1
            Integer maxSort = getMaxSortByParentId(entity.getParentId());
            entity.setSort(maxSort + 1);
        }
        
        boolean success = super.save(entity);
        
        if (success) {
            // 记录审计日志
            auditService.recordCategoryCreation(
                String.format("创建知识分类：[%s]，上级分类ID：[%s]", entity.getName(), entity.getParentId()),
                entity.getId()
            );
        }
        
        return success;
    }

    /**
     * 删除分类
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
        // 获取分类信息
        KnowledgeCategory category = getById(id);
        if (category == null) {
            return false;
        }
        
        // 检查是否有子分类
        LambdaQueryWrapper<KnowledgeCategory> childrenWrapper = Wrappers.lambdaQuery();
        childrenWrapper.eq(KnowledgeCategory::getParentId, id);
        long childrenCount = count(childrenWrapper);
        
        if (childrenCount > 0) {
            throw KnowledgeException.failed("该分类下存在子分类", new RuntimeException("该分类下存在子分类，不能删除"));
        }
        
        // 检查是否有关联的知识文档
        Integer knowledgeCount = knowledgeMapper.countByCategoryId(id);
        if (knowledgeCount > 0) {
            throw KnowledgeException.failed("该分类下存在知识文档", new RuntimeException("该分类下存在知识文档，不能删除"));
        }
        
        // 删除分类
        boolean success = super.removeById(id);
        
        if (success) {
            // 记录审计日志
            auditService.recordCategoryDeletion(
                String.format("删除知识分类：[%s]，ID：[%s]", category.getName(), category.getId()),
                id
            );
        }
        
        return success;
    }

    /**
     * 递归构建子分类树
     */
    private void buildChildrenTree(KnowledgeCategory parent, List<KnowledgeCategory> allCategories) {
        List<KnowledgeCategory> children = allCategories.stream()
                .filter(category -> Objects.equals(category.getParentId(), parent.getId()))
                .collect(Collectors.toList());
                
        parent.setChildren(children);
        
        children.forEach(child -> {
            buildChildrenTree(child, allCategories);
        });
    }

    /**
     * 获取同级分类中的最大排序值
     */
    private Integer getMaxSortByParentId(Long parentId) {
        LambdaQueryWrapper<KnowledgeCategory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(KnowledgeCategory::getParentId, parentId);
        wrapper.orderByDesc(KnowledgeCategory::getSort);
        wrapper.last("LIMIT 1");
        
        KnowledgeCategory category = getOne(wrapper);
        return category != null ? category.getSort() : 0;
    }
} 