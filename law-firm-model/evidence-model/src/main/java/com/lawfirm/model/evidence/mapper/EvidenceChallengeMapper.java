package com.lawfirm.model.evidence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.evidence.entity.EvidenceChallenge;
import org.apache.ibatis.annotations.Mapper;

/**
 * 证据质证实体的MyBatis Mapper接口
 */
@Mapper
public interface EvidenceChallengeMapper extends BaseMapper<EvidenceChallenge> {
    // 可根据需要添加自定义SQL方法
} 