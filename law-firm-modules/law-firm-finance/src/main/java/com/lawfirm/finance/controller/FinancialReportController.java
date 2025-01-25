package com.lawfirm.finance.controller;

import com.lawfirm.finance.service.FinancialReportService;
import com.lawfirm.finance.vo.FinancialReportVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "财务报表管理", description = "财务报表的生成和查询接口")
@RestController
@RequestMapping("/api/v1/finance/reports")
@RequiredArgsConstructor
public class FinancialReportController {

    private final FinancialReportService financialReportService;

    @Operation(summary = "生成自定义报表", description = "根据时间范围生成自定义财务报表")
    @GetMapping("/custom")
    public ResponseEntity<FinancialReportVO> getCustomReport(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId,
            @Parameter(description = "统计开始时间", required = true) 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "统计结束时间", required = true) 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseEntity.ok(financialReportService.generateReport(lawFirmId, startTime, endTime));
    }

    @Operation(summary = "生成月度报表", description = "生成指定月份的财务报表")
    @GetMapping("/monthly")
    public ResponseEntity<FinancialReportVO> getMonthlyReport(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId,
            @Parameter(description = "年份", required = true) @RequestParam int year,
            @Parameter(description = "月份(1-12)", required = true) @RequestParam int month) {
        return ResponseEntity.ok(financialReportService.generateMonthlyReport(lawFirmId, year, month));
    }

    @Operation(summary = "生成年度报表", description = "生成指定年份的财务报表")
    @GetMapping("/annual")
    public ResponseEntity<FinancialReportVO> getAnnualReport(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId,
            @Parameter(description = "年份", required = true) @RequestParam int year) {
        return ResponseEntity.ok(financialReportService.generateAnnualReport(lawFirmId, year));
    }

    @Operation(summary = "生成季度报表", description = "生成指定季度的财务报表")
    @GetMapping("/quarterly")
    public ResponseEntity<FinancialReportVO> getQuarterlyReport(
            @Parameter(description = "律所ID", required = true) @RequestParam Long lawFirmId,
            @Parameter(description = "年份", required = true) @RequestParam int year,
            @Parameter(description = "季度(1-4)", required = true) @RequestParam int quarter) {
        return ResponseEntity.ok(financialReportService.generateQuarterlyReport(lawFirmId, year, quarter));
    }
} 