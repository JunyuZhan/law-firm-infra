package com.lawfirm.knowledge.service.convert;

import com.lawfirm.model.knowledge.dto.KnowledgeDTO;
import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.knowledge.entity.KnowledgeCategory;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;
import com.lawfirm.model.knowledge.service.KnowledgeCategoryService;
import com.lawfirm.model.knowledge.service.convert.KnowledgeConvert;
import com.lawfirm.model.knowledge.vo.KnowledgeCategoryVO;
import com.lawfirm.model.knowledge.vo.KnowledgeTagVO;
import com.lawfirm.model.knowledge.vo.KnowledgeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 知识转换器实现
 */
@Component("knowledgeConvert")
public class KnowledgeConvertImpl implements KnowledgeConvert {

    @Autowired
    private KnowledgeCategoryService categoryService;

    /**
     * DTO转实体
     */
    @Override
    public Knowledge fromDTO(KnowledgeDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Knowledge entity = new Knowledge();
        BeanUtils.copyProperties(dto, entity);
        
        // 其他自定义转换逻辑
        
        return entity;
    }

    /**
     * 实体转VO
     */
    @Override
    public KnowledgeVO toVO(Knowledge entity) {
        if (entity == null) {
            return null;
        }
        
        KnowledgeVO vo = new KnowledgeVO();
        BeanUtils.copyProperties(entity, vo);
        
        // 设置分类名称
        if (entity.getCategoryId() != null) {
            KnowledgeCategory category = categoryService.getById(entity.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getName());
            }
        }
        
        return vo;
    }

    /**
     * 实体列表转VO列表
     */
    @Override
    public List<KnowledgeVO> toVOList(List<Knowledge> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        
        return entities.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 分类实体转VO
     */
    @Override
    public KnowledgeCategoryVO toCategoryVO(KnowledgeCategory entity) {
        if (entity == null) {
            return null;
        }
        
        KnowledgeCategoryVO vo = new KnowledgeCategoryVO();
        BeanUtils.copyProperties(entity, vo);
        
        if (entity.getChildren() != null) {
            vo.setChildren(toCategoryVOList(entity.getChildren()));
        }
        
        return vo;
    }

    /**
     * 分类实体列表转VO列表
     */
    @Override
    public List<KnowledgeCategoryVO> toCategoryVOList(List<KnowledgeCategory> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        
        return entities.stream()
                .map(this::toCategoryVO)
                .collect(Collectors.toList());
    }

    /**
     * 标签实体转VO
     */
    @Override
    public KnowledgeTagVO toTagVO(KnowledgeTag entity) {
        if (entity == null) {
            return null;
        }
        
        KnowledgeTagVO vo = new KnowledgeTagVO();
        BeanUtils.copyProperties(entity, vo);
        
        return vo;
    }

    /**
     * 标签实体列表转VO列表
     */
    @Override
    public List<KnowledgeTagVO> toTagVOList(List<KnowledgeTag> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        
        return entities.stream()
                .map(this::toTagVO)
                .collect(Collectors.toList());
    }
} 