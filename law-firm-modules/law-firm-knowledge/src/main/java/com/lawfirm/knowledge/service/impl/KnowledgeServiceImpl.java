package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.knowledge.entity.Knowledge;
import com.lawfirm.knowledge.mapper.KnowledgeMapper;
import com.lawfirm.knowledge.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识库服务实现类
 */
@Slf4j
@Service("knowledgeServiceImpl")
@RequiredArgsConstructor
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    private final KnowledgeMapper knowledgeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createKnowledge(Knowledge knowledge) {
        knowledge.setViewCount(0);
        knowledge.setLikeCount(0);
        knowledge.setFavoriteCount(0);
        knowledge.setCreateTime(LocalDateTime.now());
        knowledge.setUpdateTime(LocalDateTime.now());
        save(knowledge);
        return knowledge.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateKnowledge(Knowledge knowledge) {
        knowledge.setUpdateTime(LocalDateTime.now());
        updateById(knowledge);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteKnowledge(Long id) {
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishKnowledge(Long id) {
        Knowledge knowledge = getById(id);
        if (knowledge != null) {
            knowledge.setStatus(1);
            knowledge.setUpdateTime(LocalDateTime.now());
            updateById(knowledge);
        }
    }

    @Override
    public IPage<Knowledge> pageKnowledge(Integer page, Integer size, Integer type, Integer status, String keyword) {
        LambdaQueryWrapper<Knowledge> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type != null, Knowledge::getType, type)
                .eq(status != null, Knowledge::getStatus, status)
                .like(StringUtils.isNotBlank(keyword), Knowledge::getTitle, keyword)
                .orderByDesc(Knowledge::getCreateTime);
        return page(new Page<>(page, size), wrapper);
    }

    @Override
    public List<Knowledge> listByCategory(Long categoryId) {
        return knowledgeMapper.selectByCategoryId(categoryId);
    }

    @Override
    public List<Knowledge> listByTag(String tag) {
        return knowledgeMapper.selectByTag(tag);
    }

    @Override
    public List<Knowledge> listByAuthor(Long authorId) {
        return knowledgeMapper.selectByAuthorId(authorId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void viewKnowledge(Long id, Long userId, String ip) {
        knowledgeMapper.incrementViewCount(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeKnowledge(Long id, Long userId) {
        knowledgeMapper.incrementLikeCount(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikeKnowledge(Long id, Long userId) {
        knowledgeMapper.decrementLikeCount(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void favoriteKnowledge(Long id, Long userId) {
        knowledgeMapper.incrementFavoriteCount(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unfavoriteKnowledge(Long id, Long userId) {
        knowledgeMapper.decrementFavoriteCount(id);
    }
} 