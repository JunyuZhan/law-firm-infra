package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.dto.CaseAnalysisQueryDTO;
import com.lawfirm.model.analysis.vo.CaseAnalysisResultVO;
import com.lawfirm.model.analysis.vo.PageResultVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

/**
 * 案件分析服务接口
 */
@Tag(name = "案件分析", description = "案件相关数据分析接口")
public interface ICaseAnalysisService {
    @Operation(summary = "案件分析分页", description = "按条件分页统计案件分析结果")
    PageResultVO<CaseAnalysisResultVO> analyzeCases(
        @Parameter(description = "案件分析查询参数") CaseAnalysisQueryDTO queryDTO
    );

    @Operation(summary = "案件TOP榜", description = "获取案件分析TOP榜")
    List<CaseAnalysisResultVO> topCases(
        @Parameter(description = "案件分析查询参数") CaseAnalysisQueryDTO queryDTO
    );
} 