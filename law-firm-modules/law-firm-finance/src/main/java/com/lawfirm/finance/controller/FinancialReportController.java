package com.lawfirm.finance.controller;

import com.lawfirm.common.core.response.ResponseResult;
import com.lawfirm.model.finance.entity.FinancialReport;
import com.lawfirm.model.finance.enums.ReportTypeEnum;
import com.lawfirm.model.finance.service.FinancialReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 财务报表管理控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/finance/reports")
@Tag(name = "财务报表管理", description = "财务报表相关接口")
public class FinancialReportController {

    private final FinancialReportService financialReportService;

    @PostMapping
    @Operation(summary = "创建财务报表")
    public ResponseResult<Long> createReport(@Valid @RequestBody FinancialReport report) {
        return ResponseResult.success(financialReportService.createReport(report));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新财务报表")
    public ResponseResult<Boolean> updateReport(@PathVariable("id") Long id, 
                                              @Valid @RequestBody FinancialReport report) {
        report.setId(id);
        return ResponseResult.success(financialReportService.updateReport(report));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除财务报表")
    public ResponseResult<Boolean> deleteReport(@PathVariable("id") Long id) {
        return ResponseResult.success(financialReportService.deleteReport(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取财务报表详情")
    public ResponseResult<FinancialReport> getReport(@PathVariable("id") Long id) {
        return ResponseResult.success(financialReportService.getReportById(id));
    }

    @PostMapping("/generate")
    @Operation(summary = "生成财务报表")
    public ResponseResult<Long> generateReport(
            @Parameter(description = "报表类型") @RequestParam ReportTypeEnum reportType,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId) {
        return ResponseResult.success(financialReportService.generateReport(reportType, startTime, endTime, departmentId));
    }

    @GetMapping("/list")
    @Operation(summary = "查询财务报表列表")
    public ResponseResult<List<FinancialReport>> listReports(
            @Parameter(description = "报表类型") @RequestParam(required = false) ReportTypeEnum reportType,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(financialReportService.listReports(reportType, departmentId, startTime, endTime));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "按部门查询报表")
    public ResponseResult<List<FinancialReport>> listReportsByDepartment(
            @PathVariable("departmentId") Long departmentId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(financialReportService.listReportsByDepartment(departmentId, startTime, endTime));
    }

    @GetMapping("/cost-center/{costCenterId}")
    @Operation(summary = "按成本中心查询报表")
    public ResponseResult<List<FinancialReport>> listReportsByCostCenter(
            @PathVariable("costCenterId") Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(financialReportService.listReportsByCostCenter(costCenterId, startTime, endTime));
    }

    @GetMapping("/{id}/statistics")
    @Operation(summary = "获取报表统计数据")
    public ResponseResult<Map<String, BigDecimal>> getReportStatistics(@PathVariable("id") Long id) {
        return ResponseResult.success(financialReportService.getReportStatistics(id));
    }

    @GetMapping("/department/{departmentId}/statistics")
    @Operation(summary = "获取部门报表统计数据")
    public ResponseResult<Map<String, BigDecimal>> getDepartmentReportStatistics(
            @PathVariable("departmentId") Long departmentId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(financialReportService.getDepartmentReportStatistics(departmentId, startTime, endTime));
    }

    @GetMapping("/cost-center/{costCenterId}/statistics")
    @Operation(summary = "获取成本中心报表统计数据")
    public ResponseResult<Map<String, BigDecimal>> getCostCenterReportStatistics(
            @PathVariable("costCenterId") Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseResult.success(financialReportService.getCostCenterReportStatistics(costCenterId, startTime, endTime));
    }

    @PostMapping("/{id}/export")
    @Operation(summary = "导出财务报表")
    public ResponseResult<String> exportReport(@PathVariable("id") Long id) {
        return ResponseResult.success(financialReportService.exportReport(id));
    }
}
