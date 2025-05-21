package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.knowledge.service.AuditIntegrationService;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.knowledge.entity.KnowledgeTagRelation;
import com.lawfirm.model.knowledge.mapper.KnowledgeTagRelationMapper;
import com.lawfirm.model.knowledge.service.KnowledgeTagRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.knowledge.exception.KnowledgeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 知识标签关联服务实现类
 * 
 * 注：当前实现未直接注入core层服务。
 * 如需记录关联操作的审计日志等，可参考以下方式注入core服务：
 * 
 * @Autowired
 * @Qualifier("knowledgeAuditService") 
 * private AuditIntegrationService auditService;
 */
@Slf4j
@Service("knowledgeTagRelationServiceImpl")
public class KnowledgeTagRelationServiceImpl extends BaseServiceImpl<KnowledgeTagRelationMapper, KnowledgeTagRelation> implements KnowledgeTagRelationService {

    @Autowired
    @Qualifier("knowledgeAuditService") 
    private AuditIntegrationService auditService;

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
            
            // 记录审计日志
            auditService.recordKnowledgeOperation(
                String.format("为知识文档创建标签关联，知识ID：[%s]，标签数量：[%d]", knowledgeId, tagIds.size()),
                "CREATE_TAG_RELATION",
                knowledgeId
            );
            
            log.info("创建知识标签关联成功，知识ID: {}, 标签数量: {}", knowledgeId, tagIds.size());
        } catch (Exception e) {
            log.error("创建知识标签关联失败: {}", e.getMessage(), e);
            throw KnowledgeException.failed("创建知识标签关联失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return;
        }
        
        try {
            // 获取要删除的标签关联数量
            LambdaQueryWrapper<KnowledgeTagRelation> countWrapper = Wrappers.lambdaQuery();
            countWrapper.eq(KnowledgeTagRelation::getKnowledgeId, knowledgeId);
            int count = Math.toIntExact(count(countWrapper));
            
            if (count > 0) {
                // 删除知识标签关联
                baseMapper.deleteByKnowledgeId(knowledgeId);
                
                // 记录审计日志
                auditService.recordKnowledgeOperation(
                    String.format("删除知识文档的所有标签关联，知识ID：[%s]，关联数量：[%d]", knowledgeId, count),
                    "DELETE_TAG_RELATION",
                    knowledgeId
                );
            }
            
            log.info("删除知识标签关联成功，知识ID: {}", knowledgeId);
        } catch (Exception e) {
            log.error("删除知识标签关联失败: {}", e.getMessage(), e);
            throw KnowledgeException.failed("删除知识标签关联失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTagId(Long tagId) {
        if (tagId == null) {
            return;
        }
        
        try {
            // 获取要删除的标签关联数量
            LambdaQueryWrapper<KnowledgeTagRelation> countWrapper = Wrappers.lambdaQuery();
            countWrapper.eq(KnowledgeTagRelation::getTagId, tagId);
            int count = Math.toIntExact(count(countWrapper));
            
            if (count > 0) {
                // 删除标签关联
                baseMapper.deleteByTagId(tagId);
                
                // 记录审计日志
                auditService.recordKnowledgeOperation(
                    String.format("删除标签的所有知识关联，标签ID：[%s]，关联数量：[%d]", tagId, count),
                    "DELETE_TAG_RELATION",
                    null
                );
            }
            
            log.info("删除标签知识关联成功，标签ID: {}", tagId);
        } catch (Exception e) {
            log.error("删除标签知识关联失败: {}", e.getMessage(), e);
            throw KnowledgeException.failed("删除标签知识关联失败", e);
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
     * 删除知识的所有标签关联
     */
    @Override
    public void removeByKnowledgeId(Long knowledgeId) {
        deleteByKnowledgeId(knowledgeId);
    }
} 