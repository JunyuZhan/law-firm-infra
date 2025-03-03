package com.lawfirm.model.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.search.entity.SearchIndex;
import org.apache.ibatis.annotations.Mapper;

/**
 * 搜索索引Mapper接口
 */
@Mapper
public interface SearchIndexMapper extends BaseMapper<SearchIndex> {
} 