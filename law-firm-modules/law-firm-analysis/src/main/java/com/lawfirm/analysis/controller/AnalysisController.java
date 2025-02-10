package com.lawfirm.analysis.controller;

import com.lawfirm.analysis.model.dto.AnalysisRequest;
import com.lawfirm.analysis.model.dto.AnalysisResponse;
import com.lawfirm.analysis.service.AnalysisService;
import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 统计分析控制器
 */
@Tag(name = "统计分析接口")
@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping
    @Operation(summary = "创建分析任务")
    public Result<AnalysisResponse> createAnalysis(@Validated @RequestBody AnalysisRequest request) {
        return Result.ok(analysisService.createAnalysis(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取分析记录")
    public Result<AnalysisResponse> getAnalysis(
            @Parameter(description = "记录ID") @PathVariable Long id) {
        return Result.ok(analysisService.getAnalysis(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询分析记录")
    public Result<PageResult<AnalysisResponse>> pageAnalysis(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.ok(analysisService.pageAnalysis(pageNum, pageSize));
    }
} 