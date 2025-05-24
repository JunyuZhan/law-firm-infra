package com.lawfirm.model.evidence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.evidence.entity.EvidenceAttachment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 证据附件实体的MyBatis Mapper接口
 */
@Mapper
public interface EvidenceAttachmentMapper extends BaseMapper<EvidenceAttachment> {
    // 可根据需要添加自定义SQL方法
} 