package com.lawfirm.api.adaptor.finance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.entity.Expense;
import com.lawfirm.model.finance.service.ExpenseService;
import com.lawfirm.model.finance.vo.expense.ExpenseDetailVO;
import com.lawfirm.model.finance.service.ExpenseService.ExpenseStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
     * 记录支出
     */
    public Long recordExpense(Expense expense) {
        return expenseService.recordExpense(expense);
    }

    /**
     * 更新支出信息
     */
    public boolean updateExpense(Expense expense) {
        return expenseService.updateExpense(expense);
    }

    /**
     * 获取支出详情
     */
    public ExpenseDetailVO getExpense(Long id) {
        Expense expense = expenseService.getExpenseById(id);
        return convert(expense, ExpenseDetailVO.class);
    }

    /**
     * 删除支出记录
     */
    public boolean deleteExpense(Long id) {
        return expenseService.deleteExpense(id);
    }

    /**
     * 审批支出
     */
    public boolean approveExpense(Long expenseId, Long approverId, boolean approved, String remark) {
        return expenseService.approveExpense(expenseId, approverId, approved, remark);
    }

    /**
     * 支付支出
     */
    public boolean payExpense(Long expenseId, Long accountId, LocalDateTime paymentTime, String remark) {
        return expenseService.payExpense(expenseId, accountId, paymentTime, remark);
    }

    /**
     * 取消支出
     */
    public boolean cancelExpense(Long expenseId, String reason) {
        return expenseService.cancelExpense(expenseId, reason);
    }

    /**
     * 查询支出列表
     */
    public List<ExpenseDetailVO> listExpenses(Integer expenseType, Integer status,
                                            LocalDateTime startTime, LocalDateTime endTime) {
        List<Expense> expenses = expenseService.listExpenses(expenseType, status, startTime, endTime);
        return expenses.stream()
                .map(expense -> convert(expense, ExpenseDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 分页查询支出
     */
    public IPage<ExpenseDetailVO> pageExpenses(IPage<Expense> page, Integer expenseType, Integer status,
                                             LocalDateTime startTime, LocalDateTime endTime) {
        IPage<Expense> expensePage = expenseService.pageExpenses(page, expenseType, status, startTime, endTime);
        return expensePage.convert(expense -> convert(expense, ExpenseDetailVO.class));
    }

    /**
     * 按部门查询支出
     */
    public List<ExpenseDetailVO> listExpensesByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Expense> expenses = expenseService.listExpensesByDepartment(departmentId, startTime, endTime);
        return expenses.stream()
                .map(expense -> convert(expense, ExpenseDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按成本中心查询支出
     */
    public List<ExpenseDetailVO> listExpensesByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Expense> expenses = expenseService.listExpensesByCostCenter(costCenterId, startTime, endTime);
        return expenses.stream()
                .map(expense -> convert(expense, ExpenseDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 统计支出总额
     */
    public BigDecimal sumExpenseAmount(Integer expenseType, LocalDateTime startTime, LocalDateTime endTime) {
        return expenseService.sumExpenseAmount(expenseType, startTime, endTime);
    }

    /**
     * 按月统计支出
     */
    public List<ExpenseStat> statisticExpenseByMonth(Integer year, Integer month) {
        return expenseService.statisticExpenseByMonth(year, month);
    }

    /**
     * 按类型统计支出
     */
    public List<ExpenseStat> statisticExpenseByType(LocalDateTime startTime, LocalDateTime endTime) {
        return expenseService.statisticExpenseByType(startTime, endTime);
    }
}