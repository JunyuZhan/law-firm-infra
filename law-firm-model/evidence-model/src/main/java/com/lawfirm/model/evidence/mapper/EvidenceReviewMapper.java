package com.lawfirm.model.evidence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.evidence.entity.EvidenceReview;
import org.apache.ibatis.annotations.Mapper;

/**
 * 证据评审实体的MyBatis Mapper接口
 */
@Mapper
public interface EvidenceReviewMapper extends BaseMapper<EvidenceReview> {
    // 可根据需要添加自定义SQL方法
} 