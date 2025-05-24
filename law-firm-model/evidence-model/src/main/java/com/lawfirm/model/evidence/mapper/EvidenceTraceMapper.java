package com.lawfirm.model.evidence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.evidence.entity.EvidenceTrace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 证据流转/溯源实体的MyBatis Mapper接口
 */
@Mapper
public interface EvidenceTraceMapper extends BaseMapper<EvidenceTrace> {
    // 可根据需要添加自定义SQL方法
} 