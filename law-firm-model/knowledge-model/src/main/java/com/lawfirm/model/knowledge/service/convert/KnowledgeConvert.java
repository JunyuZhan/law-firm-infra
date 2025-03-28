package com.lawfirm.model.knowledge.service.convert;

import com.lawfirm.model.knowledge.dto.KnowledgeDTO;
import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.knowledge.entity.KnowledgeCategory;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;
import com.lawfirm.model.knowledge.vo.KnowledgeCategoryVO;
import com.lawfirm.model.knowledge.vo.KnowledgeTagVO;
import com.lawfirm.model.knowledge.vo.KnowledgeVO;

import java.util.List;

/**
 * 知识转换器接口
 */
public interface KnowledgeConvert {

    /**
     * DTO转实体
     */
    Knowledge fromDTO(KnowledgeDTO dto);

    /**
     * 实体转VO
     */
    KnowledgeVO toVO(Knowledge entity);

    /**
     * 实体列表转VO列表
     */
    List<KnowledgeVO> toVOList(List<Knowledge> entities);

    /**
     * 分类实体转VO
     */
    KnowledgeCategoryVO toCategoryVO(KnowledgeCategory entity);

    /**
     * 分类实体列表转VO列表
     */
    List<KnowledgeCategoryVO> toCategoryVOList(List<KnowledgeCategory> entities);

    /**
     * 标签实体转VO
     */
    KnowledgeTagVO toTagVO(KnowledgeTag entity);

    /**
     * 标签实体列表转VO列表
     */
    List<KnowledgeTagVO> toTagVOList(List<KnowledgeTag> entities);
} 