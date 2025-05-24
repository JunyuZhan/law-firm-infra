package com.lawfirm.model.evidence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.evidence.entity.EvidenceFactRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 证据-事实关联实体的MyBatis Mapper接口
 */
@Mapper
public interface EvidenceFactRelationMapper extends BaseMapper<EvidenceFactRelation> {
    // 可根据需要添加自定义SQL方法
} 