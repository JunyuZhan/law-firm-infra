package com.lawfirm.finance.service;

import com.lawfirm.finance.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 支出记录服务接口
 */
public interface ExpenseService {
    
    /**
     * 创建支出记录
     */
    Expense createExpense(Expense expense);
    
    /**
     * 更新支出记录
     */
    Expense updateExpense(Expense expense);
    
    /**
     * 获取支出记录
     */
    Expense getExpense(Long id);
    
    /**
     * 删除支出记录
     */
    void deleteExpense(Long id);
    
    /**
     * 分页查询支出记录
     */
    Page<Expense> findExpenses(Pageable pageable);
    
    /**
     * 根据律所ID查询支出记录
     */
    List<Expense> findExpensesByLawFirmId(Long lawFirmId);
    
    /**
     * 根据部门ID查询支出记录
     */
    List<Expense> findExpensesByDepartmentId(Long departmentId);
    
    /**
     * 更新支出状态
     */
    Expense updateExpenseStatus(Long id, String status);
    
    /**
     * 统计总支出
     */
    BigDecimal calculateTotalExpense(Long lawFirmId);
    
    /**
     * 按类型统计支出
     */
    BigDecimal calculateExpenseByType(Long lawFirmId, String expenseType);
} 