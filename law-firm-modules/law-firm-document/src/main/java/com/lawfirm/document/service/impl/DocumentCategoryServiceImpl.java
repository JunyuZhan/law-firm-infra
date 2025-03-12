package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.utils.BeanUtils;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.entity.DocumentCategory;
import com.lawfirm.model.document.mapper.DocumentCategoryMapper;
import com.lawfirm.model.document.service.DocumentCategoryService;
import com.lawfirm.model.document.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文档分类服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentCategoryServiceImpl extends BaseServiceImpl<DocumentCategoryMapper, DocumentCategory> implements DocumentCategoryService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(CategoryCreateDTO createDTO) {
        // 1. 创建分类
        DocumentCategory category = BeanUtils.copyProperties(createDTO, DocumentCategory.class);
        baseMapper.insert(category);

        return category.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(CategoryUpdateDTO updateDTO) {
        // 1. 更新分类
        DocumentCategory category = baseMapper.selectById(updateDTO.getId());
        BeanUtils.copyProperties(updateDTO, category);
        baseMapper.updateById(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        // 1. 检查是否有子分类
        if (hasChildren(id)) {
            throw new IllegalArgumentException("存在子分类，无法删除");
        }

        // 2. 检查是否有关联文档
        if (hasDocuments(id)) {
            throw new IllegalArgumentException("分类下存在文档，无法删除");
        }

        // 3. 删除分类
        baseMapper.deleteById(id);
    }

    @Override
    public CategoryVO getCategory(Long id) {
        // 1. 获取分类
        DocumentCategory category = baseMapper.selectById(id);
        if (category == null) {
            return null;
        }

        // 2. 转换为VO
        return BeanUtils.copyProperties(category, CategoryVO.class);
    }

    @Override
    public List<CategoryVO> listCategories(CategoryQueryDTO queryDTO) {
        // 1. 构建查询条件
        LambdaQueryWrapper<DocumentCategory> wrapper = new LambdaQueryWrapper<>();
        // TODO: 添加查询条件

        // 2. 执行查询
        List<DocumentCategory> categories = baseMapper.selectList(wrapper);

        // 3. 转换为VO
        return BeanUtils.copyList(categories, CategoryVO.class);
    }

    @Override
    public Page<CategoryVO> pageCategories(CategoryQueryDTO queryDTO) {
        // 1. 构建查询条件
        LambdaQueryWrapper<DocumentCategory> wrapper = new LambdaQueryWrapper<>();
        // TODO: 添加查询条件

        // 2. 执行分页查询
        Page<DocumentCategory> page = baseMapper.selectPage(
            new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
            wrapper
        );

        // 3. 转换为VO
        return BeanUtils.copyPage(page, CategoryVO.class);
    }

    /**
     * 检查是否有子分类
     */
    private boolean hasChildren(Long id) {
        LambdaQueryWrapper<DocumentCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentCategory::getParentId, id);
        return baseMapper.selectCount(wrapper) > 0;
    }

    /**
     * 检查是否有关联文档
     */
    private boolean hasDocuments(Long id) {
        // TODO: 实现检查分类下是否有文档
        return false;
    }
} 