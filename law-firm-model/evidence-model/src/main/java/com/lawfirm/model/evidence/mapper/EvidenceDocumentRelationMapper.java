package com.lawfirm.model.evidence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.evidence.entity.EvidenceDocumentRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 证据-文档关联实体的MyBatis Mapper接口
 * 提供对证据-文档关联表的增删改查操作
 */
@Mapper
public interface EvidenceDocumentRelationMapper extends BaseMapper<EvidenceDocumentRelation> {
    // 可根据需要添加自定义SQL方法
} 