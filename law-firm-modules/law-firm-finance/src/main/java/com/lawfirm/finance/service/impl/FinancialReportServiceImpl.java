package com.lawfirm.finance.service.impl;

import com.lawfirm.finance.service.ExpenseService;
import com.lawfirm.finance.service.FeeRecordService;
import com.lawfirm.finance.service.FinancialReportService;
import com.lawfirm.finance.vo.FinancialReportVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FinancialReportServiceImpl implements FinancialReportService {

    private final FeeRecordService feeRecordService;
    private final ExpenseService expenseService;

    @Override
    public FinancialReportVO generateReport(Long lawFirmId, LocalDateTime startTime, LocalDateTime endTime) {
        FinancialReportVO report = new FinancialReportVO();
        report.setLawFirmId(lawFirmId);
        report.setStartTime(startTime);
        report.setEndTime(endTime);

        // 计算收入
        BigDecimal totalIncome = feeRecordService.calculateTotalAmount(lawFirmId);
        BigDecimal totalPaidIncome = feeRecordService.calculateTotalPaidAmount(lawFirmId);
        report.setTotalIncome(totalIncome);
        report.setTotalPaidIncome(totalPaidIncome);
        report.setTotalUnpaidIncome(totalIncome.subtract(totalPaidIncome));

        // 计算支出
        BigDecimal totalExpense = expenseService.calculateTotalExpense(lawFirmId);
        report.setTotalExpense(totalExpense);

        // 计算利润
        report.setGrossProfit(totalIncome.subtract(totalExpense));
        report.setNetProfit(totalPaidIncome.subtract(totalExpense));

        // 按类型统计支出
        Map<String, BigDecimal> expenseByType = new HashMap<>();
        expenseByType.put("日常运营", expenseService.calculateExpenseByType(lawFirmId, "日常运营"));
        expenseByType.put("人员工资", expenseService.calculateExpenseByType(lawFirmId, "人员工资"));
        expenseByType.put("办公设备", expenseService.calculateExpenseByType(lawFirmId, "办公设备"));
        expenseByType.put("其他支出", expenseService.calculateExpenseByType(lawFirmId, "其他支出"));
        report.setExpenseByType(expenseByType);

        return report;
    }

    @Override
    public FinancialReportVO generateMonthlyReport(Long lawFirmId, int year, int month) {
        LocalDateTime startTime = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endTime = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);
        return generateReport(lawFirmId, startTime, endTime);
    }

    @Override
    public FinancialReportVO generateAnnualReport(Long lawFirmId, int year) {
        LocalDateTime startTime = YearMonth.of(year, 1).atDay(1).atStartOfDay();
        LocalDateTime endTime = YearMonth.of(year, 12).atEndOfMonth().atTime(23, 59, 59);
        return generateReport(lawFirmId, startTime, endTime);
    }

    @Override
    public FinancialReportVO generateQuarterlyReport(Long lawFirmId, int year, int quarter) {
        int startMonth = (quarter - 1) * 3 + 1;
        int endMonth = quarter * 3;
        
        LocalDateTime startTime = YearMonth.of(year, startMonth).atDay(1).atStartOfDay();
        LocalDateTime endTime = YearMonth.of(year, endMonth).atEndOfMonth().atTime(23, 59, 59);
        return generateReport(lawFirmId, startTime, endTime);
    }
} 