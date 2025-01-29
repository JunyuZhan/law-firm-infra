package com.lawfirm.cases.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.CaseFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * 案件文件Mapper
 */
@Mapper
public interface CaseFileMapper extends BaseMapper<CaseFile> {
} 