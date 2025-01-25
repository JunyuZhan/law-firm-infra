package com.lawfirm.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.archive.model.entity.ArchiveBorrow;
import org.apache.ibatis.annotations.Mapper;

/**
 * 档案借阅Mapper接口
 */
@Mapper
public interface ArchiveBorrowMapper extends BaseMapper<ArchiveBorrow> {
} 