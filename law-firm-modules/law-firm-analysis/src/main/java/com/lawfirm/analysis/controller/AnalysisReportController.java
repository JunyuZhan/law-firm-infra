package com.lawfirm.analysis.controller;

import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import com.lawfirm.analysis.model.dto.AnalysisReportRequest;
import com.lawfirm.analysis.model.dto.AnalysisReportResponse;
import com.lawfirm.analysis.service.AnalysisReportService;
import com.lawfirm.common.core.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 统计分析报告控制器
 */
@Tag(name = "统计分析报告接口")
@RestController
@RequestMapping("/analysis/report")
@RequiredArgsConstructor
public class AnalysisReportController {

    private final AnalysisReportService analysisReportService;

    @PostMapping("/generate")
    @Operation(summary = "生成分析报告")
    public Result<AnalysisReportResponse> generateReport(@Validated @RequestBody AnalysisReportRequest request) {
        return Result.ok(analysisReportService.generateReport(request));
    }

    @GetMapping("/latest/{type}")
    @Operation(summary = "获取最新报告")
    public Result<AnalysisReportResponse> getLatestReport(
            @Parameter(description = "分析类型") @PathVariable AnalysisTypeEnum type) {
        return Result.ok(analysisReportService.getLatestReport(type));
    }
} 
