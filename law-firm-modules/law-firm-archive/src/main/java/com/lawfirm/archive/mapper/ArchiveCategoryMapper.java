package com.lawfirm.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.archive.model.entity.ArchiveCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 档案分类Mapper接口
 */
@Mapper
public interface ArchiveCategoryMapper extends BaseMapper<ArchiveCategory> {
} 