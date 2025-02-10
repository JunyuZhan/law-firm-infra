package com.lawfirm.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.document.entity.DocumentVersion;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档版本Mapper接口
 */
@Mapper
public interface DocumentVersionMapper extends BaseMapper<DocumentVersion> {
} 