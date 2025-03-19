package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.expense.ExpenseCreateDTO;
import com.lawfirm.model.finance.dto.expense.ExpenseUpdateDTO;
import com.lawfirm.model.finance.entity.Expense;
import com.lawfirm.model.finance.service.ExpenseService;
import com.lawfirm.model.finance.vo.expense.ExpenseVO;
import com.lawfirm.model.finance.enums.ExpenseTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 支出管理适配器
 */
@Component
public class ExpenseAdaptor extends BaseAdaptor {

    @Autowired
    private ExpenseService expenseService;

    /**
     * 创建支出
     */
    public ExpenseVO createExpense(ExpenseCreateDTO dto) {
        Expense expense = expenseService.createExpense(dto);
        return convert(expense, ExpenseVO.class);
    }

    /**
     * 更新支出
     */
    public ExpenseVO updateExpense(Long id, ExpenseUpdateDTO dto) {
        Expense expense = expenseService.updateExpense(id, dto);
        return convert(expense, ExpenseVO.class);
    }

    /**
     * 获取支出详情
     */
    public ExpenseVO getExpense(Long id) {
        Expense expense = expenseService.getExpense(id);
        return convert(expense, ExpenseVO.class);
    }

    /**
     * 删除支出
     */
    public void deleteExpense(Long id) {
        expenseService.deleteExpense(id);
    }

    /**
     * 获取所有支出
     */
    public List<ExpenseVO> listExpenses() {
        List<Expense> expenses = expenseService.listExpenses();
        return expenses.stream()
                .map(expense -> convert(expense, ExpenseVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据支出类型查询支出
     */
    public List<ExpenseVO> getExpensesByType(ExpenseTypeEnum type) {
        List<Expense> expenses = expenseService.getExpensesByType(type);
        return expenses.stream()
                .map(expense -> convert(expense, ExpenseVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询支出
     */
    public List<ExpenseVO> getExpensesByDepartmentId(Long departmentId) {
        List<Expense> expenses = expenseService.getExpensesByDepartmentId(departmentId);
        return expenses.stream()
                .map(expense -> convert(expense, ExpenseVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据成本中心ID查询支出
     */
    public List<ExpenseVO> getExpensesByCostCenterId(Long costCenterId) {
        List<Expense> expenses = expenseService.getExpensesByCostCenterId(costCenterId);
        return expenses.stream()
                .map(expense -> convert(expense, ExpenseVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据预算ID查询支出
     */
    public List<ExpenseVO> getExpensesByBudgetId(Long budgetId) {
        List<Expense> expenses = expenseService.getExpensesByBudgetId(budgetId);
        return expenses.stream()
                .map(expense -> convert(expense, ExpenseVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查支出是否存在
     */
    public boolean existsExpense(Long id) {
        return expenseService.existsExpense(id);
    }

    /**
     * 获取支出数量
     */
    public long countExpenses() {
        return expenseService.countExpenses();
    }
} 