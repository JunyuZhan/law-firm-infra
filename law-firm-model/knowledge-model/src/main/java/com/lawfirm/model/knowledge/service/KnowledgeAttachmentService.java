package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.entity.KnowledgeAttachment;

import java.util.List;

/**
 * 知识附件Service接口
 */
public interface KnowledgeAttachmentService extends BaseService<KnowledgeAttachment> {

    /**
     * 根据知识ID查询附件列表
     */
    List<KnowledgeAttachment> listByKnowledgeId(Long knowledgeId);

    /**
     * 根据文件类型查询附件列表
     */
    List<KnowledgeAttachment> listByFileType(String fileType);

    /**
     * 删除知识的所有附件
     */
    void deleteByKnowledgeId(Long knowledgeId);

    /**
     * 删除知识的所有附件（兼容方法）
     */
    void removeByKnowledgeId(Long knowledgeId);

    /**
     * 统计知识附件数量
     */
    Integer countByKnowledgeId(Long knowledgeId);
} 