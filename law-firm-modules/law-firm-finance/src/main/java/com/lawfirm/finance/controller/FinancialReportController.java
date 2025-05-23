package com.lawfirm.finance.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.finance.constant.FinanceConstants;
import com.lawfirm.model.finance.entity.FinancialReport;
import com.lawfirm.model.finance.enums.ReportTypeEnum;
import com.lawfirm.model.finance.service.FinancialReportService;
import com.lawfirm.finance.service.impl.FinanceAIManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 财务报表管理控制器
 */
@Slf4j
@RestController("financialReportController")
@RequiredArgsConstructor
@RequestMapping(FinanceConstants.API_REPORT_PREFIX)
@Tag(name = "财务报表", description = "财务报表相关接口")
public class FinancialReportController {

    private final FinancialReportService financialReportService;
    private final FinanceAIManager financeAIManager;

    @PostMapping
    @Operation(summary = "创建财务报表")
    @PreAuthorize(FINANCE_CREATE)
    public CommonResult<Long> createReport(@Valid @RequestBody FinancialReport report) {
        return CommonResult.success(financialReportService.createReport(report));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新财务报表")
    @PreAuthorize(FINANCE_EDIT)
    public CommonResult<Boolean> updateReport(@PathVariable("id") Long id, 
                                              @Valid @RequestBody FinancialReport report) {
        report.setId(id);
        return CommonResult.success(financialReportService.updateReport(report));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除财务报表")
    @PreAuthorize(FINANCE_DELETE)
    public CommonResult<Boolean> deleteReport(@PathVariable("id") Long id) {
        return CommonResult.success(financialReportService.deleteReport(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取财务报表详情")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<FinancialReport> getReport(@PathVariable("id") Long id) {
        return CommonResult.success(financialReportService.getReportById(id));
    }

    @PostMapping("/generate")
    @Operation(summary = "生成财务报表")
    @PreAuthorize(FINANCE_CREATE)
    public CommonResult<Long> generateReport(
            @Parameter(description = "报表类型") @RequestParam ReportTypeEnum reportType,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId) {
        return CommonResult.success(financialReportService.generateReport(reportType, startTime, endTime, departmentId));
    }

    @GetMapping("/list")
    @Operation(summary = "查询财务报表列表")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<List<FinancialReport>> listReports(
            @Parameter(description = "报表类型") @RequestParam(required = false) ReportTypeEnum reportType,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(financialReportService.listReports(reportType, departmentId, startTime, endTime));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "按部门查询报表")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<List<FinancialReport>> listReportsByDepartment(
            @PathVariable("departmentId") Long departmentId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(financialReportService.listReportsByDepartment(departmentId, startTime, endTime));
    }

    @GetMapping("/cost-center/{costCenterId}")
    @Operation(summary = "按成本中心查询报表")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<List<FinancialReport>> listReportsByCostCenter(
            @PathVariable("costCenterId") Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(financialReportService.listReportsByCostCenter(costCenterId, startTime, endTime));
    }

    @GetMapping("/{id}/statistics")
    @Operation(summary = "获取报表统计数据")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<Map<String, BigDecimal>> getReportStatistics(@PathVariable("id") Long id) {
        return CommonResult.success(financialReportService.getReportStatistics(id));
    }

    @GetMapping("/department/{departmentId}/statistics")
    @Operation(summary = "获取部门报表统计数据")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<Map<String, BigDecimal>> getDepartmentReportStatistics(
            @PathVariable("departmentId") Long departmentId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(financialReportService.getDepartmentReportStatistics(departmentId, startTime, endTime));
    }

    @GetMapping("/cost-center/{costCenterId}/statistics")
    @Operation(summary = "获取成本中心报表统计数据")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<Map<String, BigDecimal>> getCostCenterReportStatistics(
            @PathVariable("costCenterId") Long costCenterId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return CommonResult.success(financialReportService.getCostCenterReportStatistics(costCenterId, startTime, endTime));
    }

    @PostMapping("/{id}/export")
    @Operation(summary = "导出财务报表")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<String> exportReport(@PathVariable("id") Long id) {
        return CommonResult.success(financialReportService.exportReport(id));
    }

    /**
     * AI智能摘要接口
     */
    @PostMapping("/ai/summary")
    @Operation(summary = "AI智能生成财务报表摘要")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<String> aiReportSummary(@RequestBody Map<String, Object> body) {
        String content = (String) body.get("content");
        Integer maxLength = body.get("maxLength") != null ? (Integer) body.get("maxLength") : 200;
        return CommonResult.success(financeAIManager.generateReportSummary(content, maxLength));
    }

    /**
     * AI异常检测接口
     */
    @PostMapping("/ai/anomaly")
    @Operation(summary = "AI检测财务报表异常")
    @PreAuthorize(FINANCE_VIEW)
    public CommonResult<Map<String, Object>> aiReportAnomaly(@RequestBody Map<String, Object> body) {
        String content = (String) body.get("content");
        return CommonResult.success(financeAIManager.detectFinancialAnomalies(content));
    }
}
