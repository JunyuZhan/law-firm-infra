package com.lawfirm.model.evidence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.evidence.entity.Evidence;
import org.apache.ibatis.annotations.Mapper;

/**
 * 证据实体的MyBatis Mapper接口
 * 提供对证据表的增删改查操作
 */
@Mapper
public interface EvidenceMapper extends BaseMapper<Evidence> {
    // 可根据需要添加自定义SQL方法
} 