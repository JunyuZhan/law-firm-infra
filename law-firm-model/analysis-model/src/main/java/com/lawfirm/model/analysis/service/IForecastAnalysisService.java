package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.AnalysisRequestDTO;
import com.lawfirm.model.analysis.vo.ForecastResultVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

/**
 * 预测分析服务接口
 */
@Tag(name = "预测分析", description = "预测相关数据分析接口")
public interface IForecastAnalysisService {
    @Operation(summary = "预测分析", description = "获取预测分析结果")
    List<ForecastResultVO> forecast(
        @Parameter(description = "预测分析查询参数") AnalysisRequestDTO queryDTO
    );
} 