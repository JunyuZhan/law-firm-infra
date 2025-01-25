package com.lawfirm.cases.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.cases.model.entity.CaseFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * 案件文件Mapper接口
 */
@Mapper
public interface CaseFileMapper extends BaseMapper<CaseFile> {
} 