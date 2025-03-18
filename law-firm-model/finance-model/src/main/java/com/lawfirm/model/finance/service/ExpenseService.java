package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 支出服务接口
 */
public interface ExpenseService {
    
    /**
     * 记录支出
     *
     * @param expense 支出信息
     * @return 支出ID
     */
    Long recordExpense(Expense expense);
    
    /**
     * 更新支出信息
     *
     * @param expense 支出信息
     * @return 是否更新成功
     */
    boolean updateExpense(Expense expense);
    
    /**
     * 删除支出记录
     *
     * @param expenseId 支出ID
     * @return 是否删除成功
     */
    boolean deleteExpense(Long expenseId);
    
    /**
     * 获取支出详情
     *
     * @param expenseId 支出ID
     * @return 支出信息
     */
    Expense getExpenseById(Long expenseId);
    
    /**
     * 审批支出
     *
     * @param expenseId 支出ID
     * @param approverId 审批人ID
     * @param approved 是否通过
     * @param remark 审批备注
     * @return 是否审批成功
     */
    boolean approveExpense(Long expenseId, Long approverId, boolean approved, String remark);
    
    /**
     * 支付支出
     *
     * @param expenseId 支出ID
     * @param accountId 支付账户ID
     * @param paymentTime 支付时间
     * @param remark 支付备注
     * @return 是否支付成功
     */
    boolean payExpense(Long expenseId, Long accountId, LocalDateTime paymentTime, String remark);
    
    /**
     * 取消支出
     *
     * @param expenseId 支出ID
     * @param reason 取消原因
     * @return 是否取消成功
     */
    boolean cancelExpense(Long expenseId, String reason);
    
    /**
     * 查询支出列表
     *
     * @param expenseType 支出类型，可为null
     * @param status 支出状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 支出列表
     */
    List<Expense> listExpenses(Integer expenseType, Integer status, 
                              LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询支出
     *
     * @param page 分页参数
     * @param expenseType 支出类型，可为null
     * @param status 支出状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页支出信息
     */
    IPage<Expense> pageExpenses(IPage<Expense> page, Integer expenseType, Integer status, 
                               LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按部门查询支出
     *
     * @param departmentId 部门ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 支出列表
     */
    List<Expense> listExpensesByDepartment(Long departmentId, 
                                          LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按成本中心查询支出
     *
     * @param costCenterId 成本中心ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 支出列表
     */
    List<Expense> listExpensesByCostCenter(Long costCenterId, 
                                          LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计支出总额
     *
     * @param expenseType 支出类型，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 支出总额
     */
    BigDecimal sumExpenseAmount(Integer expenseType, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按月统计支出
     *
     * @param year 年份
     * @param month 月份，可为null表示全年
     * @return 支出统计列表
     */
    List<ExpenseStat> statisticExpenseByMonth(Integer year, Integer month);
    
    /**
     * 按类型统计支出
     *
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 支出统计列表
     */
    List<ExpenseStat> statisticExpenseByType(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 支出统计数据
     */
    interface ExpenseStat {
        /**
         * 获取统计维度（如月份、支出类型等）
         */
        String getDimension();
        
        /**
         * 获取支出金额
         */
        BigDecimal getAmount();
        
        /**
         * 获取支出笔数
         */
        Integer getCount();
    }
} 