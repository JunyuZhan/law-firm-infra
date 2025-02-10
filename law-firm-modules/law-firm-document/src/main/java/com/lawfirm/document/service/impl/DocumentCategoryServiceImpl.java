package com.lawfirm.modules.document.service.impl;

import com.lawfirm.modules.document.entity.DocumentCategory;
import com.lawfirm.model.document.repository.DocumentCategoryRepository;
import com.lawfirm.model.document.repository.DocumentRepository;
import com.lawfirm.modules.document.service.DocumentCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档分类服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentCategoryServiceImpl implements DocumentCategoryService {

    private final DocumentCategoryRepository documentCategoryRepository;
    private final DocumentRepository documentRepository;

    @Override
    @Transactional
    public DocumentCategory createCategory(DocumentCategory category) {
        // 检查编码是否存在
        if (existsByCode(category.getCode())) {
            throw new IllegalArgumentException("分类编码已存在");
        }

        // 设置初始状态
        category.setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setIsDeleted(false);

        // 如果有父分类，设置层级和路径
        if (category.getParentId() != null) {
            DocumentCategory parent = getCategory(category.getParentId());
            if (parent == null) {
                throw new IllegalArgumentException("父分类不存在");
            }
            category.setLevel(parent.getLevel() + 1)
                    .setPath(parent.getPath() + "/" + parent.getId());
        } else {
            category.setLevel(1)
                    .setPath("");
        }

        return documentCategoryRepository.save(category);
    }

    @Override
    @Transactional
    public DocumentCategory updateCategory(DocumentCategory category) {
        DocumentCategory existingCategory = getCategory(category.getId());
        if (existingCategory == null) {
            throw new IllegalArgumentException("分类不存在");
        }

        // 检查编码是否重复
        if (!existingCategory.getCode().equals(category.getCode()) && existsByCode(category.getCode())) {
            throw new IllegalArgumentException("分类编码已存在");
        }

        // 更新基本信息
        category.setUpdateTime(LocalDateTime.now());
        return documentCategoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        DocumentCategory category = getCategory(id);
        if (category == null) {
            return;
        }

        // 检查是否有子分类
        if (documentCategoryRepository.countByParentId(id) > 0) {
            throw new IllegalStateException("存在子分类，无法删除");
        }

        // 检查是否有关联文档
        if (documentRepository.countByCategoryId(id) > 0) {
            throw new IllegalStateException("分类下存在文档，无法删除");
        }

        // 逻辑删除
        category.setIsDeleted(true)
                .setDeleteTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        documentCategoryRepository.save(category);
    }

    @Override
    public DocumentCategory getCategory(Long id) {
        return documentCategoryRepository.findById(id)
                .filter(cat -> !Boolean.TRUE.equals(cat.getIsDeleted()))
                .orElse(null);
    }

    @Override
    public List<DocumentCategory> getCategoryTree() {
        List<DocumentCategory> allCategories = documentCategoryRepository.findAll();
        return buildCategoryTree(allCategories, null);
    }

    @Override
    public List<DocumentCategory> getChildren(Long parentId) {
        return documentCategoryRepository.findByParentId(parentId);
    }

    @Override
    @Transactional
    public void moveCategory(Long categoryId, Long newParentId) {
        DocumentCategory category = getCategory(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("分类不存在");
        }

        // 检查新父分类
        DocumentCategory newParent = newParentId != null ? getCategory(newParentId) : null;
        if (newParentId != null && newParent == null) {
            throw new IllegalArgumentException("新父分类不存在");
        }

        // 检查是否形成循环
        if (newParentId != null && isDescendant(category.getId(), newParentId)) {
            throw new IllegalArgumentException("不能将分类移动到其子分类下");
        }

        // 更新分类
        category.setParentId(newParentId)
                .setLevel(newParent != null ? newParent.getLevel() + 1 : 1)
                .setPath(newParent != null ? newParent.getPath() + "/" + newParent.getId() : "")
                .setUpdateTime(LocalDateTime.now());
        documentCategoryRepository.save(category);

        // 更新子分类的层级和路径
        updateChildrenLevelAndPath(category);
    }

    @Override
    public Page<DocumentCategory> listCategories(Pageable pageable) {
        return documentCategoryRepository.findAll(pageable);
    }

    @Override
    public DocumentCategory getCategoryByCode(String code) {
        return documentCategoryRepository.findByCode(code);
    }

    @Override
    public List<DocumentCategory> getCategoryPath(Long categoryId) {
        return documentCategoryRepository.findCategoryPath(categoryId);
    }

    @Override
    public boolean existsByCode(String code) {
        return documentCategoryRepository.existsByCode(code);
    }

    @Override
    public long countDocuments(Long categoryId) {
        return documentRepository.countByCategoryId(categoryId);
    }

    private List<DocumentCategory> buildCategoryTree(List<DocumentCategory> allCategories, Long parentId) {
        return allCategories.stream()
                .filter(cat -> !Boolean.TRUE.equals(cat.getIsDeleted()))
                .filter(cat -> parentId == null ? cat.getParentId() == null : parentId.equals(cat.getParentId()))
                .map(cat -> {
                    cat.setChildren(buildCategoryTree(allCategories, cat.getId()));
                    return cat;
                })
                .collect(Collectors.toList());
    }

    private boolean isDescendant(Long ancestorId, Long descendantId) {
        if (ancestorId.equals(descendantId)) {
            return true;
        }
        DocumentCategory descendant = getCategory(descendantId);
        if (descendant == null || descendant.getParentId() == null) {
            return false;
        }
        return isDescendant(ancestorId, descendant.getParentId());
    }

    private void updateChildrenLevelAndPath(DocumentCategory parent) {
        List<DocumentCategory> children = getChildren(parent.getId());
        for (DocumentCategory child : children) {
            child.setLevel(parent.getLevel() + 1)
                    .setPath(parent.getPath() + "/" + parent.getId())
                    .setUpdateTime(LocalDateTime.now());
            documentCategoryRepository.save(child);
            updateChildrenLevelAndPath(child);
        }
    }
} 