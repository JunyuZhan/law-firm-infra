package com.lawfirm.finance.service.impl;

import com.lawfirm.finance.entity.Expense;
import com.lawfirm.finance.repository.ExpenseRepository;
import com.lawfirm.finance.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Override
    @Transactional
    public Expense createExpense(Expense expense) {
        expense.setExpenseStatus("PENDING");
        expense.setExpenseTime(LocalDateTime.now());
        return expenseRepository.save(expense);
    }

    @Override
    @Transactional
    public Expense updateExpense(Expense expense) {
        if (!expenseRepository.existsById(expense.getId())) {
            throw new EntityNotFoundException("支出记录不存在");
        }
        return expenseRepository.save(expense);
    }

    @Override
    public Expense getExpense(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("支出记录不存在"));
    }

    @Override
    @Transactional
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public Page<Expense> findExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    @Override
    public List<Expense> findExpensesByLawFirmId(Long lawFirmId) {
        return expenseRepository.findByLawFirmId(lawFirmId);
    }

    @Override
    public List<Expense> findExpensesByDepartmentId(Long departmentId) {
        return expenseRepository.findByDepartmentId(departmentId);
    }

    @Override
    @Transactional
    public Expense updateExpenseStatus(Long id, String status) {
        Expense expense = getExpense(id);
        expense.setExpenseStatus(status);
        
        if ("PAID".equals(status)) {
            expense.setExpenseTime(LocalDateTime.now());
        }
        
        return expenseRepository.save(expense);
    }

    @Override
    public BigDecimal calculateTotalExpense(Long lawFirmId) {
        return expenseRepository.findByLawFirmId(lawFirmId).stream()
                .filter(e -> "PAID".equals(e.getExpenseStatus()))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateExpenseByType(Long lawFirmId, String expenseType) {
        return expenseRepository.findByLawFirmId(lawFirmId).stream()
                .filter(e -> "PAID".equals(e.getExpenseStatus()) && expenseType.equals(e.getExpenseType()))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 