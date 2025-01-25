package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.knowledge.entity.KnowledgeCategory;
import com.lawfirm.knowledge.mapper.KnowledgeCategoryMapper;
import com.lawfirm.knowledge.service.KnowledgeCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识分类服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeCategoryServiceImpl extends ServiceImpl<KnowledgeCategoryMapper, KnowledgeCategory> implements KnowledgeCategoryService {

    private final KnowledgeCategoryMapper categoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(KnowledgeCategory category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        save(category);
        return category.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(KnowledgeCategory category) {
        category.setUpdateTime(LocalDateTime.now());
        updateById(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        // 递归删除子分类
        List<KnowledgeCategory> children = categoryMapper.selectChildren(id);
        if (!children.isEmpty()) {
            children.forEach(child -> deleteCategory(child.getId()));
        }
        removeById(id);
    }

    @Override
    public List<KnowledgeCategory> listChildren(Long parentId) {
        return categoryMapper.selectChildren(parentId);
    }

    @Override
    public List<KnowledgeCategory> getPath(Long categoryId) {
        return categoryMapper.selectPath(categoryId);
    }

    @Override
    public List<KnowledgeCategory> getTree() {
        return categoryMapper.selectTree();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveCategory(Long id, Long parentId) {
        KnowledgeCategory category = getById(id);
        if (category != null) {
            category.setParentId(parentId);
            category.setUpdateTime(LocalDateTime.now());
            updateById(category);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reorderCategory(Long id, Integer orderNum) {
        KnowledgeCategory category = getById(id);
        if (category != null) {
            category.setOrderNum(orderNum);
            category.setUpdateTime(LocalDateTime.now());
            updateById(category);
        }
    }
} 