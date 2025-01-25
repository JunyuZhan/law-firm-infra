package com.lawfirm.finance.service;

import com.lawfirm.finance.config.TestConfig;
import com.lawfirm.finance.service.impl.FinancialReportServiceImpl;
import com.lawfirm.finance.vo.FinancialReportVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class FinancialReportServiceTest {

    @Mock
    private FeeRecordService feeRecordService;

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private FinancialReportServiceImpl financialReportService;

    @BeforeEach
    void setUp() {
        when(feeRecordService.calculateTotalAmount(anyLong())).thenReturn(new BigDecimal("10000.00"));
        when(feeRecordService.calculateTotalPaidAmount(anyLong())).thenReturn(new BigDecimal("8000.00"));
        when(expenseService.calculateTotalExpense(anyLong())).thenReturn(new BigDecimal("5000.00"));
    }

    @Test
    void generateReport() {
        LocalDateTime startTime = LocalDateTime.now().minusMonths(1);
        LocalDateTime endTime = LocalDateTime.now();

        FinancialReportVO report = financialReportService.generateReport(1L, startTime, endTime);

        assertNotNull(report);
        assertEquals(new BigDecimal("10000.00"), report.getTotalIncome());
        assertEquals(new BigDecimal("8000.00"), report.getTotalPaidIncome());
        assertEquals(new BigDecimal("2000.00"), report.getTotalUnpaidIncome());
        assertEquals(new BigDecimal("5000.00"), report.getTotalExpense());
        assertEquals(new BigDecimal("5000.00"), report.getGrossProfit());
        assertEquals(new BigDecimal("3000.00"), report.getNetProfit());

        verify(feeRecordService, times(1)).calculateTotalAmount(anyLong());
        verify(feeRecordService, times(1)).calculateTotalPaidAmount(anyLong());
        verify(expenseService, times(1)).calculateTotalExpense(anyLong());
    }

    @Test
    void generateMonthlyReport() {
        FinancialReportVO report = financialReportService.generateMonthlyReport(1L, 2024, 3);

        assertNotNull(report);
        assertEquals(YearMonth.of(2024, 3).atDay(1).atStartOfDay(), report.getStartTime());
        assertEquals(YearMonth.of(2024, 3).atEndOfMonth().atTime(23, 59, 59), report.getEndTime());
    }

    @Test
    void generateAnnualReport() {
        FinancialReportVO report = financialReportService.generateAnnualReport(1L, 2024);

        assertNotNull(report);
        assertEquals(YearMonth.of(2024, 1).atDay(1).atStartOfDay(), report.getStartTime());
        assertEquals(YearMonth.of(2024, 12).atEndOfMonth().atTime(23, 59, 59), report.getEndTime());
    }

    @Test
    void generateQuarterlyReport() {
        FinancialReportVO report = financialReportService.generateQuarterlyReport(1L, 2024, 1);

        assertNotNull(report);
        assertEquals(YearMonth.of(2024, 1).atDay(1).atStartOfDay(), report.getStartTime());
        assertEquals(YearMonth.of(2024, 3).atEndOfMonth().atTime(23, 59, 59), report.getEndTime());
    }
} 