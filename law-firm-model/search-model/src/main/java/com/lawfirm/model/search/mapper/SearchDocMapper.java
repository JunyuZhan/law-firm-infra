package com.lawfirm.model.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.search.entity.SearchDoc;
import org.apache.ibatis.annotations.Mapper;

/**
 * 搜索文档Mapper接口
 */
@Mapper
public interface SearchDocMapper extends BaseMapper<SearchDoc> {
} 