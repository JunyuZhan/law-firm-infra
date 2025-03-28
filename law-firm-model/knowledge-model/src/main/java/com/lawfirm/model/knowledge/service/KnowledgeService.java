package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.dto.KnowledgeDTO;
import com.lawfirm.model.knowledge.entity.Knowledge;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 知识库Service接口
 */
public interface KnowledgeService extends BaseService<Knowledge> {

    /**
     * 创建知识文档
     * 
     * @param dto 知识DTO
     * @return 知识实体
     */
    Knowledge addKnowledge(KnowledgeDTO dto);

    /**
     * 根据分类ID查询知识列表
     */
    List<Knowledge> listByCategoryId(Long categoryId);

    /**
     * 根据标签ID查询知识列表
     */
    List<Knowledge> listByTagId(Long tagId);

    /**
     * 根据关键词搜索知识
     */
    List<Knowledge> searchByKeyword(String keyword);

    /**
     * 获取最新知识
     */
    List<Knowledge> listLatest(Integer limit);

    /**
     * 获取相关知识
     */
    List<Knowledge> listRelated(Long categoryId, Long id, Integer limit);
    
    /**
     * 上传知识文档附件
     *
     * @param file 文件
     * @param knowledgeId 知识文档ID
     * @return 附件ID
     * @throws Exception 上传异常
     */
    Long uploadAttachment(MultipartFile file, Long knowledgeId) throws Exception;
} 