package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseFinanceDTO;
import com.lawfirm.model.cases.service.business.CaseFinanceService;
import com.lawfirm.model.cases.vo.business.CaseFinanceVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件费用管理控制器实现
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/cases/fees")
@RequiredArgsConstructor
@Tag(name = "案件费用管理", description = "案件费用管理相关接口")
public class FeeControllerImpl {

    private final CaseFinanceService financeService;

    @PostMapping("/income")
    @Operation(summary = "记录收入")
    public Long recordIncome(@RequestBody @Validated CaseFinanceDTO financeDTO) {
        log.info("记录收入: {}", financeDTO);
        return financeService.recordIncome(financeDTO);
    }

    @PostMapping("/expense")
    @Operation(summary = "记录支出")
    public Long recordExpense(@RequestBody @Validated CaseFinanceDTO financeDTO) {
        log.info("记录支出: {}", financeDTO);
        return financeService.recordExpense(financeDTO);
    }

    @PostMapping("/batch")
    @Operation(summary = "批量记录财务信息")
    public boolean batchRecordFinance(@RequestBody @Validated List<CaseFinanceDTO> financeDTOs) {
        log.info("批量记录财务信息: {}", financeDTOs);
        return financeService.batchRecordFinance(financeDTOs);
    }

    @PutMapping
    @Operation(summary = "更新财务记录")
    public boolean updateFinance(@RequestBody @Validated CaseFinanceDTO financeDTO) {
        log.info("更新财务记录: {}", financeDTO);
        return financeService.updateFinance(financeDTO);
    }

    @DeleteMapping("/{financeId}")
    @Operation(summary = "删除财务记录")
    public boolean deleteFinance(@PathVariable("financeId") Long financeId) {
        log.info("删除财务记录: {}", financeId);
        return financeService.deleteFinance(financeId);
    }

    @GetMapping("/{financeId}")
    @Operation(summary = "获取财务记录详情")
    public CaseFinanceVO getFinanceDetail(@PathVariable("financeId") Long financeId) {
        log.info("获取财务记录详情: {}", financeId);
        return financeService.getFinanceDetail(financeId);
    }

    @GetMapping("/cases/{caseId}")
    @Operation(summary = "获取案件的所有财务记录")
    public List<CaseFinanceVO> listCaseFinances(@PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有财务记录: caseId={}", caseId);
        return financeService.listCaseFinances(caseId);
    }

    @GetMapping("/cases/{caseId}/page")
    @Operation(summary = "分页查询财务记录")
    public IPage<CaseFinanceVO> pageFinances(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "财务类型") @RequestParam(required = false) Integer financeType,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询财务记录: caseId={}, financeType={}, startTime={}, endTime={}, pageNum={}, pageSize={}", 
                caseId, financeType, startTime, endTime, pageNum, pageSize);
        return financeService.pageFinances(caseId, financeType, startTime, endTime, pageNum, pageSize);
    }

    @PostMapping("/cases/{caseId}/bills")
    @Operation(summary = "生成账单")
    public Long generateBill(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("生成账单: caseId={}, startTime={}, endTime={}", caseId, startTime, endTime);
        return financeService.generateBill(caseId, startTime, endTime);
    }

    @PutMapping("/{financeId}/review")
    @Operation(summary = "审核财务记录")
    public boolean reviewFinance(
            @PathVariable("financeId") Long financeId,
            @Parameter(description = "是否通过") @RequestParam boolean approved,
            @Parameter(description = "审核意见") @RequestParam(required = false) String opinion) {
        log.info("审核财务记录: financeId={}, approved={}, opinion={}", financeId, approved, opinion);
        return financeService.reviewFinance(financeId, approved, opinion);
    }

    @GetMapping("/cases/{caseId}/income")
    @Operation(summary = "计算案件总收入")
    public BigDecimal calculateTotalIncome(@PathVariable("caseId") Long caseId) {
        log.info("计算案件总收入: caseId={}", caseId);
        return financeService.calculateTotalIncome(caseId);
    }

    @GetMapping("/cases/{caseId}/expense")
    @Operation(summary = "计算案件总支出")
    public BigDecimal calculateTotalExpense(@PathVariable("caseId") Long caseId) {
        log.info("计算案件总支出: caseId={}", caseId);
        return financeService.calculateTotalExpense(caseId);
    }

    @GetMapping("/cases/{caseId}/profit")
    @Operation(summary = "计算案件利润")
    public BigDecimal calculateProfit(@PathVariable("caseId") Long caseId) {
        log.info("计算案件利润: caseId={}", caseId);
        return financeService.calculateProfit(caseId);
    }

    @PutMapping("/cases/{caseId}/budget")
    @Operation(summary = "设置预算")
    public boolean setBudget(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "预算金额") @RequestParam BigDecimal budget) {
        log.info("设置预算: caseId={}, budget={}", caseId, budget);
        return financeService.setBudget(caseId, budget);
    }

    @GetMapping("/cases/{caseId}/budget/overrun")
    @Operation(summary = "检查预算是否超支")
    public boolean checkBudgetOverrun(@PathVariable("caseId") Long caseId) {
        log.info("检查预算是否超支: caseId={}", caseId);
        return financeService.checkBudgetOverrun(caseId);
    }

    @GetMapping("/cases/{caseId}/export")
    @Operation(summary = "导出财务记录")
    public String exportFinanceRecords(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("导出财务记录: caseId={}, startTime={}, endTime={}", caseId, startTime, endTime);
        return financeService.exportFinanceRecords(caseId, startTime, endTime);
    }

    @GetMapping("/cases/{caseId}/count")
    @Operation(summary = "统计财务记录数量")
    public int countFinances(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "财务类型") @RequestParam(required = false) Integer financeType) {
        log.info("统计财务记录数量: caseId={}, financeType={}", caseId, financeType);
        return financeService.countFinances(caseId, financeType);
    }
} 