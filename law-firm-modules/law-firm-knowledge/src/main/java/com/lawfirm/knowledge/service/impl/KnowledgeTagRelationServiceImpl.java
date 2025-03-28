package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.knowledge.entity.KnowledgeTagRelation;
import com.lawfirm.model.knowledge.mapper.KnowledgeTagRelationMapper;
import com.lawfirm.model.knowledge.service.KnowledgeTagRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 知识标签关联服务实现类
 */
@Slf4j
@Service("knowledgeTagRelationServiceImpl")
public class KnowledgeTagRelationServiceImpl extends BaseServiceImpl<KnowledgeTagRelationMapper, KnowledgeTagRelation> implements KnowledgeTagRelationService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRelations(Long knowledgeId, List<Long> tagIds) {
        if (knowledgeId == null || CollectionUtils.isEmpty(tagIds)) {
            return;
        }
        
        try {
            // 先删除已有关联
            deleteByKnowledgeId(knowledgeId);
            
            // 批量插入新关联
            List<KnowledgeTagRelation> relations = new ArrayList<>();
            for (Long tagId : tagIds) {
                KnowledgeTagRelation relation = new KnowledgeTagRelation();
                relation.setKnowledgeId(knowledgeId);
                relation.setTagId(tagId);
                relations.add(relation);
            }
            
            saveBatch(relations);
            log.info("创建知识标签关联成功，知识ID: {}, 标签数量: {}", knowledgeId, tagIds.size());
        } catch (Exception e) {
            log.error("创建知识标签关联失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return;
        }
        
        try {
            baseMapper.deleteByKnowledgeId(knowledgeId);
            log.info("删除知识标签关联成功，知识ID: {}", knowledgeId);
        } catch (Exception e) {
            log.error("删除知识标签关联失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTagId(Long tagId) {
        if (tagId == null) {
            return;
        }
        
        try {
            baseMapper.deleteByTagId(tagId);
            log.info("删除标签知识关联成功，标签ID: {}", tagId);
        } catch (Exception e) {
            log.error("删除标签知识关联失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Long> getTagIdsByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return new ArrayList<>();
        }
        
        return baseMapper.selectTagIdsByKnowledgeId(knowledgeId);
    }

    @Override
    public List<Long> getKnowledgeIdsByTagId(Long tagId) {
        if (tagId == null) {
            return Collections.emptyList();
        }
        return baseMapper.selectKnowledgeIdsByTagId(tagId);
    }

    /**
     * 删除知识的所有标签关联（兼容方法）
     */
    @Override
    public void removeByKnowledgeId(Long knowledgeId) {
        deleteByKnowledgeId(knowledgeId);
    }
} 