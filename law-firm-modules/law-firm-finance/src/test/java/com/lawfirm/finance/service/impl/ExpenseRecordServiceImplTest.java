package com.lawfirm.finance.service.impl;

import com.lawfirm.finance.entity.ExpenseRecord;
import com.lawfirm.finance.mapper.ExpenseRecordMapper;
import com.lawfirm.finance.repository.ExpenseRecordRepository;
import com.lawfirm.common.core.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExpenseRecordServiceImplTest {

    @Mock
    private ExpenseRecordMapper expenseRecordMapper;

    @Mock
    private ExpenseRecordRepository expenseRecordRepository;

    @InjectMocks
    private ExpenseRecordServiceImpl expenseRecordService;

    private ExpenseRecord expenseRecord;

    @BeforeEach
    void setUp() {
        expenseRecord = new ExpenseRecord();
        expenseRecord.setId(1L);
        expenseRecord.setAmount(new BigDecimal("1000.00"));
        expenseRecord.setExpenseType("OPERATION");
        expenseRecord.setExpenseStatus("PENDING");
        expenseRecord.setLawFirmId(1L);
        expenseRecord.setDepartmentId(1L);
        expenseRecord.setApplicantId(1L);
        expenseRecord.setDescription("测试支出");
    }

    @Test
    void testCreateExpenseRecord() {
        when(expenseRecordService.save(any(ExpenseRecord.class))).thenReturn(true);

        ExpenseRecord result = expenseRecordService.createExpenseRecord(expenseRecord);

        assertNotNull(result);
        assertEquals("PENDING", result.getExpenseStatus());
        verify(expenseRecordService, times(1)).save(any(ExpenseRecord.class));
    }

    @Test
    void testApproveExpense() {
        when(expenseRecordService.getById(1L)).thenReturn(expenseRecord);
        when(expenseRecordRepository.save(any(ExpenseRecord.class))).thenReturn(expenseRecord);

        assertDoesNotThrow(() -> expenseRecordService.approveExpense(1L, 1L));

        verify(expenseRecordService, times(1)).getById(1L);
        verify(expenseRecordRepository, times(1)).save(any(ExpenseRecord.class));
    }

    @Test
    void testRejectExpense() {
        when(expenseRecordService.getById(1L)).thenReturn(expenseRecord);
        when(expenseRecordRepository.save(any(ExpenseRecord.class))).thenReturn(expenseRecord);

        assertDoesNotThrow(() -> expenseRecordService.rejectExpense(1L, 1L, "拒绝原因"));

        verify(expenseRecordService, times(1)).getById(1L);
        verify(expenseRecordRepository, times(1)).save(any(ExpenseRecord.class));
    }

    @Test
    void testGetExpensesByLawFirm() {
        when(expenseRecordRepository.findByLawFirmId(1L)).thenReturn(Arrays.asList(expenseRecord));

        List<ExpenseRecord> results = expenseRecordService.getExpensesByLawFirm(1L);

        assertNotNull(results);
        assertEquals(1, results.size());
        verify(expenseRecordRepository, times(1)).findByLawFirmId(1L);
    }

    @Test
    void testGetExpensesByDepartment() {
        when(expenseRecordRepository.findByDepartmentId(1L)).thenReturn(Arrays.asList(expenseRecord));

        List<ExpenseRecord> results = expenseRecordService.getExpensesByDepartment(1L);

        assertNotNull(results);
        assertEquals(1, results.size());
        verify(expenseRecordRepository, times(1)).findByDepartmentId(1L);
    }

    @Test
    void testCalculateTotalExpense() {
        when(expenseRecordRepository.findByLawFirmId(1L)).thenReturn(Arrays.asList(expenseRecord));

        BigDecimal total = expenseRecordService.calculateTotalExpense(1L);

        assertEquals(new BigDecimal("1000.00"), total);
        verify(expenseRecordRepository, times(1)).findByLawFirmId(1L);
    }

    @Test
    void testApproveExpenseWithInvalidId() {
        when(expenseRecordService.getById(1L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> expenseRecordService.approveExpense(1L, 1L));
        verify(expenseRecordService, times(1)).getById(1L);
        verify(expenseRecordRepository, never()).save(any(ExpenseRecord.class));
    }
} 