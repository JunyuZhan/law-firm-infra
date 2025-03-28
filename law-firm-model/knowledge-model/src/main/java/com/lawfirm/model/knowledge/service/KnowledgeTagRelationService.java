package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.entity.KnowledgeTagRelation;

import java.util.List;

/**
 * 知识标签关联Service接口
 */
public interface KnowledgeTagRelationService extends BaseService<KnowledgeTagRelation> {

    /**
     * 创建知识与标签的关联
     */
    void createRelations(Long knowledgeId, List<Long> tagIds);

    /**
     * 删除知识的所有标签关联
     */
    void deleteByKnowledgeId(Long knowledgeId);

    /**
     * 删除知识的所有标签关联（兼容方法）
     */
    void removeByKnowledgeId(Long knowledgeId);

    /**
     * 删除标签的所有知识关联
     */
    void deleteByTagId(Long tagId);

    /**
     * 获取知识的标签ID列表
     */
    List<Long> getTagIdsByKnowledgeId(Long knowledgeId);

    /**
     * 获取标签关联的知识ID列表
     */
    List<Long> getKnowledgeIdsByTagId(Long tagId);
}