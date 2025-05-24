package com.lawfirm.model.evidence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.evidence.entity.EvidenceTagRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 证据-标签关联实体的MyBatis Mapper接口
 */
@Mapper
public interface EvidenceTagRelationMapper extends BaseMapper<EvidenceTagRelation> {
    // 可根据需要添加自定义SQL方法
} 