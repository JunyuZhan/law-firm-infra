package com.lawfirm.model.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.archive.entity.ArchiveSyncRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 档案同步记录Mapper接口
 */
@Mapper
public interface ArchiveSyncRecordMapper extends BaseMapper<ArchiveSyncRecord> {
} 