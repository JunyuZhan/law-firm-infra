package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.Budget;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 预算服务接口
 */
public interface BudgetService {
    
    /**
     * 创建预算
     *
     * @param budget 预算信息
     * @return 预算ID
     */
    Long createBudget(Budget budget);
    
    /**
     * 更新预算
     *
     * @param budget 预算信息
     * @return 是否更新成功
     */
    boolean updateBudget(Budget budget);
    
    /**
     * 删除预算
     *
     * @param budgetId 预算ID
     * @return 是否删除成功
     */
    boolean deleteBudget(Long budgetId);
    
    /**
     * 获取预算详情
     *
     * @param budgetId 预算ID
     * @return 预算信息
     */
    Budget getBudgetById(Long budgetId);
    
    /**
     * 更新预算状态
     *
     * @param budgetId 预算ID
     * @param status 状态
     * @param remark 备注
     * @return 是否更新成功
     */
    boolean updateBudgetStatus(Long budgetId, BudgetStatusEnum status, String remark);
    
    /**
     * 审批预算
     *
     * @param budgetId 预算ID
     * @param approved 是否通过
     * @param approverId 审批人ID
     * @param remark 审批意见
     * @return 是否审批成功
     */
    boolean approveBudget(Long budgetId, boolean approved, Long approverId, String remark);
    
    /**
     * 调整预算
     *
     * @param budgetId 预算ID
     * @param amount 调整金额
     * @param operatorId 操作人ID
     * @param remark 调整原因
     * @return 是否调整成功
     */
    boolean adjustBudget(Long budgetId, BigDecimal amount, Long operatorId, String remark);
    
    /**
     * 查询预算列表
     *
     * @param status 预算状态，可为null
     * @param departmentId 部门ID，可为null
     * @param costCenterId 成本中心ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 预算列表
     */
    List<Budget> listBudgets(BudgetStatusEnum status, Long departmentId, Long costCenterId,
                            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询预算
     *
     * @param page 分页参数
     * @param status 预算状态，可为null
     * @param departmentId 部门ID，可为null
     * @param costCenterId 成本中心ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页预算信息
     */
    IPage<Budget> pageBudgets(IPage<Budget> page, BudgetStatusEnum status, Long departmentId,
                             Long costCenterId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按部门查询预算
     *
     * @param departmentId 部门ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 预算列表
     */
    List<Budget> listBudgetsByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按成本中心查询预算
     *
     * @param costCenterId 成本中心ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 预算列表
     */
    List<Budget> listBudgetsByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计预算金额
     *
     * @param status 预算状态，可为null
     * @param departmentId 部门ID，可为null
     * @param costCenterId 成本中心ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 预算总金额
     */
    BigDecimal sumBudgetAmount(BudgetStatusEnum status, Long departmentId, Long costCenterId,
                              LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计部门预算金额
     *
     * @param departmentId 部门ID
     * @param status 预算状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 预算总金额
     */
    BigDecimal sumDepartmentBudgetAmount(Long departmentId, BudgetStatusEnum status,
                                        LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计成本中心预算金额
     *
     * @param costCenterId 成本中心ID
     * @param status 预算状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 预算总金额
     */
    BigDecimal sumCostCenterBudgetAmount(Long costCenterId, BudgetStatusEnum status,
                                        LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 导出预算数据
     *
     * @param budgetIds 预算ID列表
     * @return 导出文件路径
     */
    String exportBudgets(List<Long> budgetIds);
} 