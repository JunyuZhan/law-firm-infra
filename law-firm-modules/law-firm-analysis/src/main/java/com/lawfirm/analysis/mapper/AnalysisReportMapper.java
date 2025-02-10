package com.lawfirm.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.analysis.model.entity.AnalysisReport;
import org.apache.ibatis.annotations.Mapper;

/**
 * 统计分析报告Mapper接口
 */
@Mapper
public interface AnalysisReportMapper extends BaseMapper<AnalysisReport> {
} 
