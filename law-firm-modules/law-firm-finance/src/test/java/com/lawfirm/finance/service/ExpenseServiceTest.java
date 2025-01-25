package com.lawfirm.finance.service;

import com.lawfirm.finance.config.TestConfig;
import com.lawfirm.finance.entity.Expense;
import com.lawfirm.finance.repository.ExpenseRepository;
import com.lawfirm.finance.service.impl.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense testExpense;

    @BeforeEach
    void setUp() {
        testExpense = new Expense();
        testExpense.setId(1L);
        testExpense.setAmount(new BigDecimal("1000.00"));
        testExpense.setExpenseStatus("PENDING");
        testExpense.setExpenseType("日常运营");
        testExpense.setLawFirmId(1L);
        testExpense.setDepartmentId(1L);
        testExpense.setApplicantId(1L);
        testExpense.setDescription("测试支出记录");
    }

    @Test
    void createExpense() {
        when(expenseRepository.save(any(Expense.class))).thenReturn(testExpense);

        Expense created = expenseService.createExpense(testExpense);

        assertNotNull(created);
        assertEquals(testExpense.getAmount(), created.getAmount());
        assertEquals("PENDING", created.getExpenseStatus());
        assertNotNull(created.getExpenseTime());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void updateExpense() {
        when(expenseRepository.existsById(anyLong())).thenReturn(true);
        when(expenseRepository.save(any(Expense.class))).thenReturn(testExpense);

        testExpense.setAmount(new BigDecimal("2000.00"));
        Expense updated = expenseService.updateExpense(testExpense);

        assertNotNull(updated);
        assertEquals(new BigDecimal("2000.00"), updated.getAmount());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void getExpense() {
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(testExpense));

        Expense found = expenseService.getExpense(1L);

        assertNotNull(found);
        assertEquals(testExpense.getId(), found.getId());
        verify(expenseRepository, times(1)).findById(1L);
    }

    @Test
    void findExpensesByLawFirmId() {
        when(expenseRepository.findByLawFirmId(anyLong())).thenReturn(Arrays.asList(testExpense));

        List<Expense> expenses = expenseService.findExpensesByLawFirmId(1L);

        assertFalse(expenses.isEmpty());
        assertEquals(1, expenses.size());
        assertEquals(testExpense.getLawFirmId(), expenses.get(0).getLawFirmId());
        verify(expenseRepository, times(1)).findByLawFirmId(1L);
    }

    @Test
    void updateExpenseStatus() {
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(testExpense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(testExpense);

        Expense updated = expenseService.updateExpenseStatus(1L, "APPROVED");

        assertNotNull(updated);
        assertEquals("APPROVED", updated.getExpenseStatus());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void calculateTotalExpense() {
        when(expenseRepository.findByLawFirmId(anyLong())).thenReturn(Arrays.asList(testExpense));

        BigDecimal total = expenseService.calculateTotalExpense(1L);

        assertEquals(new BigDecimal("1000.00"), total);
        verify(expenseRepository, times(1)).findByLawFirmId(1L);
    }
} 