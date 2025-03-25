package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Expense;
import com.lawfirm.model.finance.enums.ExpenseTypeEnum;
import com.lawfirm.model.finance.mapper.ExpenseMapper;
import com.lawfirm.model.finance.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 财务支出服务实现类
 */
@Slf4j
@Service("financeExpenseServiceImpl")
@RequiredArgsConstructor
public class ExpenseServiceImpl extends BaseServiceImpl<ExpenseMapper, Expense> implements ExpenseService {

    private final SecurityContext securityContext;

    @Override
    @PreAuthorize("hasPermission('expense', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "expense", allEntries = true)
    public Long recordExpense(Expense expense) {
        log.info("记录支出: expense={}", expense);
        expense.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        expense.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        expense.setCreateTime(LocalDateTime.now());
        expense.setUpdateTime(LocalDateTime.now());
        save(expense);
        return expense.getId();
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "expense", allEntries = true)
    public boolean updateExpense(Expense expense) {
        log.info("更新支出: expense={}", expense);
        expense.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        expense.setUpdateTime(LocalDateTime.now());
        return update(expense);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "expense", allEntries = true)
    public boolean deleteExpense(Long expenseId) {
        log.info("删除支出: expenseId={}", expenseId);
        return remove(expenseId);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'view')")
    @Cacheable(value = "expense", key = "#expenseId")
    public Expense getExpenseById(Long expenseId) {
        log.info("获取支出: expenseId={}", expenseId);
        return getById(expenseId);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'approve')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "expense", allEntries = true)
    public boolean approveExpense(Long expenseId, Long approverId, boolean approved, String remark) {
        log.info("审批支出: expenseId={}, approverId={}, approved={}, remark={}", 
                expenseId, approverId, approved, remark);
        Expense expense = getExpenseById(expenseId);
        if (expense == null) {
            return false;
        }
        expense.setStatus(approved ? 1 : 2); // 1:通过，2:拒绝
        expense.setApproverId(approverId);
        expense.setApprovalTime(LocalDateTime.now());
        expense.setRemark(remark);
        expense.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        expense.setUpdateTime(LocalDateTime.now());
        return update(expense);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'pay')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "expense", allEntries = true)
    public boolean payExpense(Long expenseId, Long accountId, LocalDateTime paymentTime, String remark) {
        log.info("支付支出: expenseId={}, accountId={}, paymentTime={}, remark={}", 
                expenseId, accountId, paymentTime, remark);
        Expense expense = getExpenseById(expenseId);
        if (expense == null) {
            return false;
        }
        expense.setStatus(3); // 3:已支付
        expense.setAccountId(accountId);
        expense.setExpenseTime(paymentTime);
        expense.setRemark(remark);
        expense.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        expense.setUpdateTime(LocalDateTime.now());
        return update(expense);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'cancel')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "expense", allEntries = true)
    public boolean cancelExpense(Long expenseId, String reason) {
        log.info("取消支出: expenseId={}, reason={}", expenseId, reason);
        Expense expense = getExpenseById(expenseId);
        if (expense == null) {
            return false;
        }
        expense.setStatus(4); // 4:已取消
        expense.setRemark(reason);
        expense.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        expense.setUpdateTime(LocalDateTime.now());
        return update(expense);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'view')")
    @Cacheable(value = "expense", key = "'list:' + #expenseType + ':' + #status + ':' + #startTime + ':' + #endTime")
    public List<Expense> listExpenses(Integer expenseType, Integer status, 
                                    LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询支出列表: expenseType={}, status={}, startTime={}, endTime={}", 
                expenseType, status, startTime, endTime);
        LambdaQueryWrapper<Expense> wrapper = new LambdaQueryWrapper<>();
        if (expenseType != null) {
            wrapper.eq(Expense::getExpenseType, expenseType);
        }
        if (status != null) {
            wrapper.eq(Expense::getStatus, status);
        }
        if (startTime != null) {
            wrapper.ge(Expense::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Expense::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Expense::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'view')")
    @Cacheable(value = "expense", key = "'page:' + #page.current + ':' + #page.size + ':' + #expenseType + ':' + #status + ':' + #startTime + ':' + #endTime")
    public IPage<Expense> pageExpenses(IPage<Expense> page, Integer expenseType, Integer status, 
                                     LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询支出: page={}, expenseType={}, status={}, startTime={}, endTime={}", 
                page, expenseType, status, startTime, endTime);
        LambdaQueryWrapper<Expense> wrapper = new LambdaQueryWrapper<>();
        if (expenseType != null) {
            wrapper.eq(Expense::getExpenseType, expenseType);
        }
        if (status != null) {
            wrapper.eq(Expense::getStatus, status);
        }
        if (startTime != null) {
            wrapper.ge(Expense::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Expense::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Expense::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'view')")
    @Cacheable(value = "expense", key = "'list_by_department:' + #departmentId + ':' + #startTime + ':' + #endTime")
    public List<Expense> listExpensesByDepartment(Long departmentId, 
                                                LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按部门查询支出: departmentId={}, startTime={}, endTime={}", 
                departmentId, startTime, endTime);
        LambdaQueryWrapper<Expense> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Expense::getDepartmentId, departmentId);
        if (startTime != null) {
            wrapper.ge(Expense::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Expense::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Expense::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'view')")
    @Cacheable(value = "expense", key = "'list_by_cost_center:' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public List<Expense> listExpensesByCostCenter(Long costCenterId, 
                                                LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按成本中心查询支出: costCenterId={}, startTime={}, endTime={}", 
                costCenterId, startTime, endTime);
        LambdaQueryWrapper<Expense> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Expense::getCostCenterId, costCenterId);
        if (startTime != null) {
            wrapper.ge(Expense::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Expense::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Expense::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'view')")
    @Cacheable(value = "expense", key = "'sum_amount:' + #expenseType + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumExpenseAmount(Integer expenseType, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计支出总额: expenseType={}, startTime={}, endTime={}", 
                expenseType, startTime, endTime);
        List<Expense> expenses = listExpenses(expenseType, 4, startTime, endTime); // 4:已支付
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'view')")
    @Cacheable(value = "expense", key = "'stat_by_month:' + #year + ':' + #month")
    public List<ExpenseStat> statisticExpenseByMonth(Integer year, Integer month) {
        log.info("按月统计支出: year={}, month={}", year, month);
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (month != null) {
            YearMonth yearMonth = YearMonth.of(year, month);
            startTime = yearMonth.atDay(1).atStartOfDay();
            endTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        } else {
            startTime = LocalDateTime.of(year, 1, 1, 0, 0, 0);
            endTime = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        }
        
        List<Expense> expenses = listExpenses(null, 4, startTime, endTime); // 4:已支付
        Map<YearMonth, List<Expense>> groupedExpenses = expenses.stream()
                .collect(Collectors.groupingBy(expense -> 
                        YearMonth.from(expense.getCreateTime())));
        
        List<ExpenseStat> stats = new ArrayList<>();
        groupedExpenses.forEach((yearMonth, monthExpenses) -> {
            ExpenseStat stat = new ExpenseStat() {
                @Override
                public String getDimension() {
                    return yearMonth.toString();
                }
                
                @Override
                public BigDecimal getAmount() {
                    return monthExpenses.stream()
                            .map(Expense::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                
                @Override
                public Integer getCount() {
                    return monthExpenses.size();
                }
            };
            stats.add(stat);
        });
        
        return stats;
    }

    @Override
    @PreAuthorize("hasPermission('expense', 'view')")
    @Cacheable(value = "expense", key = "'stat_by_type:' + #startTime + ':' + #endTime")
    public List<ExpenseStat> statisticExpenseByType(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按类型统计支出: startTime={}, endTime={}", startTime, endTime);
        List<Expense> expenses = listExpenses(null, null, startTime, endTime);
        Map<String, List<Expense>> groupedExpenses = expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getExpenseType().getValue()));
        
        List<ExpenseStat> stats = new ArrayList<>();
        groupedExpenses.forEach((type, typeExpenses) -> {
            ExpenseStat stat = new ExpenseStat() {
                @Override
                public String getDimension() {
                    return type;
                }
                
                @Override
                public BigDecimal getAmount() {
                    return typeExpenses.stream()
                            .map(Expense::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                
                @Override
                public Integer getCount() {
                    return typeExpenses.size();
                }
            };
            stats.add(stat);
        });
        
        return stats;
    }
} 