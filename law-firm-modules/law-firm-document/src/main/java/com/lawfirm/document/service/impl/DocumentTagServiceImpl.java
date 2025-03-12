package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.utils.BeanUtils;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.tag.TagCreateDTO;
import com.lawfirm.model.document.dto.tag.TagQueryDTO;
import com.lawfirm.model.document.dto.tag.TagUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentTag;
import com.lawfirm.model.document.mapper.DocumentTagMapper;
import com.lawfirm.model.document.service.DocumentTagService;
import com.lawfirm.model.document.vo.TagVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文档标签服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentTagServiceImpl extends BaseServiceImpl<DocumentTagMapper, DocumentTag> implements DocumentTagService {

    private final DocumentTagRelMapper tagRelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTag(TagCreateDTO createDTO) {
        // 1. 检查标签编码是否已存在
        if (isTagCodeExists(createDTO.getTagCode())) {
            throw new IllegalArgumentException("标签编码已存在");
        }

        // 2. 创建标签
        DocumentTag tag = BeanUtils.copyProperties(createDTO, DocumentTag.class);
        baseMapper.insert(tag);

        return tag.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(TagUpdateDTO updateDTO) {
        // 1. 检查标签是否存在
        DocumentTag tag = baseMapper.selectById(updateDTO.getId());
        if (tag == null) {
            throw new IllegalArgumentException("标签不存在");
        }

        // 2. 如果修改了标签编码，检查是否已存在
        if (StringUtils.isNotBlank(updateDTO.getTagCode()) 
            && !updateDTO.getTagCode().equals(tag.getTagCode())
            && isTagCodeExists(updateDTO.getTagCode())) {
            throw new IllegalArgumentException("标签编码已存在");
        }

        // 3. 更新标签
        BeanUtils.copyProperties(updateDTO, tag);
        baseMapper.updateById(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Long id) {
        // 1. 检查标签是否存在
        DocumentTag tag = baseMapper.selectById(id);
        if (tag == null) {
            throw new IllegalArgumentException("标签不存在");
        }

        // 2. 检查是否为系统标签
        if (tag.getIsSystem()) {
            throw new IllegalArgumentException("系统标签不允许删除");
        }

        // 3. 检查是否有关联文档
        if (hasDocuments(id)) {
            throw new IllegalArgumentException("标签下存在文档，无法删除");
        }

        // 4. 删除标签
        baseMapper.deleteById(id);
    }

    @Override
    public TagVO getTag(Long id) {
        // 1. 获取标签
        DocumentTag tag = baseMapper.selectById(id);
        if (tag == null) {
            return null;
        }

        // 2. 转换为VO
        return BeanUtils.copyProperties(tag, TagVO.class);
    }

    @Override
    public List<TagVO> listTags(TagQueryDTO queryDTO) {
        // 1. 构建查询条件
        LambdaQueryWrapper<DocumentTag> wrapper = buildQueryWrapper(queryDTO);

        // 2. 执行查询
        List<DocumentTag> tags = baseMapper.selectList(wrapper);

        // 3. 转换为VO
        return BeanUtils.copyList(tags, TagVO.class);
    }

    @Override
    public Page<TagVO> pageTags(TagQueryDTO queryDTO) {
        // 1. 构建查询条件
        LambdaQueryWrapper<DocumentTag> wrapper = buildQueryWrapper(queryDTO);

        // 2. 执行分页查询
        Page<DocumentTag> page = baseMapper.selectPage(
            new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
            wrapper
        );

        // 3. 转换为VO
        return BeanUtils.copyPage(page, TagVO.class);
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<DocumentTag> buildQueryWrapper(TagQueryDTO queryDTO) {
        LambdaQueryWrapper<DocumentTag> wrapper = new LambdaQueryWrapper<>();
        
        // 1. 添加基本查询条件
        wrapper.like(StringUtils.isNotBlank(queryDTO.getTagName()), DocumentTag::getTagName, queryDTO.getTagName())
               .like(StringUtils.isNotBlank(queryDTO.getTagCode()), DocumentTag::getTagCode, queryDTO.getTagCode())
               .eq(StringUtils.isNotBlank(queryDTO.getTagType()), DocumentTag::getTagType, queryDTO.getTagType())
               .eq(queryDTO.getIsSystem() != null, DocumentTag::getIsSystem, queryDTO.getIsSystem())
               .eq(queryDTO.getIsEnabled() != null, DocumentTag::getIsEnabled, queryDTO.getIsEnabled());

        // 2. 关键词搜索
        if (StringUtils.isNotBlank(queryDTO.getKeyword())) {
            wrapper.and(w -> w.like(DocumentTag::getTagName, queryDTO.getKeyword())
                            .or()
                            .like(DocumentTag::getTagCode, queryDTO.getKeyword())
                            .or()
                            .like(DocumentTag::getDescription, queryDTO.getKeyword()));
        }

        // 3. 添加排序条件
        if (StringUtils.isNotBlank(queryDTO.getSortField())) {
            boolean isAsc = "asc".equalsIgnoreCase(queryDTO.getSortDirection());
            switch (queryDTO.getSortField()) {
                case "tagName":
                    wrapper.orderBy(true, isAsc, DocumentTag::getTagName);
                    break;
                case "tagCode":
                    wrapper.orderBy(true, isAsc, DocumentTag::getTagCode);
                    break;
                case "createTime":
                    wrapper.orderBy(true, isAsc, DocumentTag::getCreatedTime);
                    break;
                default:
                    wrapper.orderByDesc(DocumentTag::getCreatedTime);
            }
        } else {
            wrapper.orderByDesc(DocumentTag::getCreatedTime);
        }

        return wrapper;
    }

    /**
     * 检查标签编码是否已存在
     */
    private boolean isTagCodeExists(String tagCode) {
        LambdaQueryWrapper<DocumentTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentTag::getTagCode, tagCode);
        return baseMapper.selectCount(wrapper) > 0;
    }

    /**
     * 检查是否有关联文档
     */
    private boolean hasDocuments(Long tagId) {
        return tagRelMapper.selectCount(new LambdaQueryWrapper<DocumentTagRel>()
            .eq(DocumentTagRel::getTagId, tagId)) > 0;
    }
} 