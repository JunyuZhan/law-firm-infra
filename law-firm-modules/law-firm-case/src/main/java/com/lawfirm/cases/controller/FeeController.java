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
 * 案件费用控制器
 */
@Slf4j
@RestController("caseFeeController")
@RequestMapping("/api/case/fee")
@RequiredArgsConstructor
@Tag(name = "案件费用管理", description = "提供案件费用管理功能，包括费用记录、结算、统计报表等操作")
public class FeeController {

    private final CaseFinanceService financeService;

    @Operation(
        summary = "记录收入",
        description = "记录案件相关的收入，包括律师费、咨询费等各类收入"
    )
    @PostMapping("/income")
    public Long recordIncome(
            @Parameter(description = "收入记录信息，包括金额、类型、备注等") 
            @RequestBody @Validated CaseFinanceDTO financeDTO) {
        log.info("记录收入: {}", financeDTO);
        return financeService.recordIncome(financeDTO);
    }

    @Operation(
        summary = "记录支出",
        description = "记录案件相关的支出，包括差旅费、文印费等各类支出"
    )
    @PostMapping("/expense")
    public Long recordExpense(
            @Parameter(description = "支出记录信息，包括金额、类型、备注等") 
            @RequestBody @Validated CaseFinanceDTO financeDTO) {
        log.info("记录支出: {}", financeDTO);
        return financeService.recordExpense(financeDTO);
    }

    @Operation(
        summary = "批量记录财务信息",
        description = "批量记录多条财务信息，支持同时录入多笔收支记录"
    )
    @PostMapping("/batch")
    public boolean batchRecordFinance(
            @Parameter(description = "财务记录列表，每条记录包括金额、类型等信息") 
            @RequestBody @Validated List<CaseFinanceDTO> financeDTOs) {
        log.info("批量记录财务信息: {}", financeDTOs);
        return financeService.batchRecordFinance(financeDTOs);
    }

    @Operation(
        summary = "更新财务记录",
        description = "更新已有的财务记录信息，包括金额、类型、备注等"
    )
    @PutMapping
    public boolean updateFinance(
            @Parameter(description = "更新的财务记录信息") 
            @RequestBody @Validated CaseFinanceDTO financeDTO) {
        log.info("更新财务记录: {}", financeDTO);
        return financeService.updateFinance(financeDTO);
    }

    @Operation(
        summary = "删除财务记录",
        description = "删除指定的财务记录，如果记录已被审核或关联其他数据则不允许删除"
    )
    @DeleteMapping("/{financeId}")
    public boolean deleteFinance(
            @Parameter(description = "财务记录ID") 
            @PathVariable("financeId") Long financeId) {
        log.info("删除财务记录: {}", financeId);
        return financeService.deleteFinance(financeId);
    }

    @Operation(
        summary = "获取财务记录详情",
        description = "获取指定财务记录的详细信息，包括金额、类型、时间、备注等"
    )
    @GetMapping("/{financeId}")
    public CaseFinanceVO getFinanceDetail(
            @Parameter(description = "财务记录ID") 
            @PathVariable("financeId") Long financeId) {
        log.info("获取财务记录详情: {}", financeId);
        return financeService.getFinanceDetail(financeId);
    }

    @Operation(
        summary = "获取案件的所有财务记录",
        description = "获取指定案件的所有财务记录，包括收入和支出"
    )
    @GetMapping("/cases/{caseId}")
    public List<CaseFinanceVO> listCaseFinances(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有财务记录: caseId={}", caseId);
        return financeService.listCaseFinances(caseId);
    }

    @Operation(
        summary = "分页查询财务记录",
        description = "分页查询案件的财务记录，支持按类型和时间范围筛选"
    )
    @GetMapping("/cases/{caseId}/page")
    public IPage<CaseFinanceVO> pageFinances(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "财务类型：1-收入，2-支出") 
            @RequestParam(required = false) Integer financeType,
            @Parameter(description = "开始时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @Parameter(description = "页码，从1开始") 
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页显示记录数") 
            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询财务记录: caseId={}, financeType={}, startTime={}, endTime={}, pageNum={}, pageSize={}", 
                caseId, financeType, startTime, endTime, pageNum, pageSize);
        return financeService.pageFinances(caseId, financeType, startTime, endTime, pageNum, pageSize);
    }

    @Operation(
        summary = "生成账单",
        description = "根据指定时间范围生成案件费用账单，包括收支明细和汇总信息"
    )
    @PostMapping("/cases/{caseId}/bills")
    public Long generateBill(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "账单开始时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "账单结束时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("生成账单: caseId={}, startTime={}, endTime={}", caseId, startTime, endTime);
        return financeService.generateBill(caseId, startTime, endTime);
    }

    @Operation(
        summary = "审核财务记录",
        description = "审核财务记录，可以选择通过或拒绝，需要提供审核意见"
    )
    @PutMapping("/{financeId}/review")
    public boolean reviewFinance(
            @Parameter(description = "财务记录ID") 
            @PathVariable("financeId") Long financeId,
            @Parameter(description = "是否通过：true-通过，false-拒绝") 
            @RequestParam boolean approved,
            @Parameter(description = "审核意见，说明通过或拒绝的原因") 
            @RequestParam(required = false) String opinion) {
        log.info("审核财务记录: financeId={}, approved={}, opinion={}", financeId, approved, opinion);
        return financeService.reviewFinance(financeId, approved, opinion);
    }

    @Operation(
        summary = "计算案件总收入",
        description = "计算指定案件的所有已审核通过的收入总额"
    )
    @GetMapping("/cases/{caseId}/income")
    public BigDecimal calculateTotalIncome(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("计算案件总收入: caseId={}", caseId);
        return financeService.calculateTotalIncome(caseId);
    }

    @Operation(
        summary = "计算案件总支出",
        description = "计算指定案件的所有已审核通过的支出总额"
    )
    @GetMapping("/cases/{caseId}/expense")
    public BigDecimal calculateTotalExpense(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("计算案件总支出: caseId={}", caseId);
        return financeService.calculateTotalExpense(caseId);
    }

    @Operation(
        summary = "计算案件利润",
        description = "计算指定案件的利润（总收入减去总支出）"
    )
    @GetMapping("/cases/{caseId}/profit")
    public BigDecimal calculateProfit(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("计算案件利润: caseId={}", caseId);
        return financeService.calculateProfit(caseId);
    }

    @Operation(
        summary = "设置预算",
        description = "设置案件的预算金额，用于费用控制和超支预警"
    )
    @PutMapping("/cases/{caseId}/budget")
    public boolean setBudget(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "预算金额，精确到分") 
            @RequestParam BigDecimal budget) {
        log.info("设置预算: caseId={}, budget={}", caseId, budget);
        return financeService.setBudget(caseId, budget);
    }

    @Operation(
        summary = "检查预算是否超支",
        description = "检查案件的实际支出是否超过预算金额"
    )
    @GetMapping("/cases/{caseId}/budget/overrun")
    public boolean checkBudgetOverrun(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("检查预算是否超支: caseId={}", caseId);
        return financeService.checkBudgetOverrun(caseId);
    }

    @Operation(
        summary = "导出财务记录",
        description = "导出指定时间范围内的财务记录，支持多种格式如Excel、PDF等"
    )
    @GetMapping("/cases/{caseId}/export")
    public String exportFinanceRecords(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "导出记录的开始时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "导出记录的结束时间，格式：yyyy-MM-dd HH:mm:ss") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("导出财务记录: caseId={}, startTime={}, endTime={}", caseId, startTime, endTime);
        return financeService.exportFinanceRecords(caseId, startTime, endTime);
    }

    @Operation(
        summary = "统计财务记录数量",
        description = "统计指定案件的财务记录数量，可按类型统计"
    )
    @GetMapping("/cases/{caseId}/count")
    public int countFinances(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "财务类型：1-收入，2-支出") 
            @RequestParam(required = false) Integer financeType) {
        log.info("统计财务记录数量: caseId={}, financeType={}", caseId, financeType);
        return financeService.countFinances(caseId, financeType);
    }
} 