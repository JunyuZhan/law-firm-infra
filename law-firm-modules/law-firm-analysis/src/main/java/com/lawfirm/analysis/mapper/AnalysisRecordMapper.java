package com.lawfirm.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.analysis.model.entity.AnalysisRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 统计分析记录Mapper接口
 */
@Mapper
public interface AnalysisRecordMapper extends BaseMapper<AnalysisRecord> {
} 