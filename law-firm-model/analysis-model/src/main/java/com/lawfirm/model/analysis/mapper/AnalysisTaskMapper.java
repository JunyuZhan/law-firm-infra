package com.lawfirm.model.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.analysis.entity.AnalysisTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分析任务Mapper接口
 */
@Mapper
public interface AnalysisTaskMapper extends BaseMapper<AnalysisTask> {
    // 可扩展自定义方法
} 