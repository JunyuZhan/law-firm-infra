package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;

import java.util.List;

/**
 * 知识标签Service接口
 */
public interface KnowledgeTagService extends BaseService<KnowledgeTag> {

    /**
     * 根据标签编码查询标签
     */
    KnowledgeTag getByCode(String code);

    /**
     * 根据标签名称模糊查询标签列表
     */
    List<KnowledgeTag> listByName(String name);

    /**
     * 获取所有标签
     */
    List<KnowledgeTag> listAll();

    /**
     * 根据知识ID查询标签列表
     */
    List<KnowledgeTag> listByKnowledgeId(Long knowledgeId);

    /**
     * 获取热门标签
     */
    List<KnowledgeTag> listHotTags(Integer limit);
} 