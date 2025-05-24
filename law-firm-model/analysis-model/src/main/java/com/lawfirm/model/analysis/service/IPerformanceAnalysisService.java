package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.PerformanceAnalysisQueryDTO;
import com.lawfirm.model.analysis.vo.PerformanceAnalysisVO;
import com.lawfirm.model.analysis.vo.PageResultVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

/**
 * 绩效分析服务接口
 */
@Tag(name = "绩效分析", description = "绩效相关数据分析接口")
public interface IPerformanceAnalysisService {
    @Operation(summary = "绩效分析分页", description = "按条件分页统计绩效分析结果")
    PageResultVO<PerformanceAnalysisVO> analyzePerformance(
        @Parameter(description = "绩效分析查询参数") PerformanceAnalysisQueryDTO queryDTO
    );

    @Operation(summary = "绩效TOP榜", description = "获取绩效分析TOP榜")
    List<PerformanceAnalysisVO> topPerformance(
        @Parameter(description = "绩效分析查询参数") PerformanceAnalysisQueryDTO queryDTO
    );
} 