package com.lawfirm.model.evidence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.evidence.entity.EvidenceTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 证据标签实体的MyBatis Mapper接口
 * 提供对证据标签表的增删改查操作
 */
@Mapper
public interface EvidenceTagMapper extends BaseMapper<EvidenceTag> {
    // 可根据需要添加自定义SQL方法
} 