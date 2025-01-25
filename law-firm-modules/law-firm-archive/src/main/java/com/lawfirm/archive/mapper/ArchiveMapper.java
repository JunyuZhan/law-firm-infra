package com.lawfirm.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.archive.model.entity.Archive;
import org.apache.ibatis.annotations.Mapper;

/**
 * 档案Mapper接口
 */
@Mapper
public interface ArchiveMapper extends BaseMapper<Archive> {
    // 特殊的查询方法可以在这里定义
} 