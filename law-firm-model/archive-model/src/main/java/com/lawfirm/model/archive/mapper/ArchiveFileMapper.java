package com.lawfirm.model.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.archive.entity.ArchiveFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * 档案文件Mapper接口
 */
@Mapper
public interface ArchiveFileMapper extends BaseMapper<ArchiveFile> {
} 