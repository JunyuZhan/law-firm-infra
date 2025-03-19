package com.lawfirm.api.adaptor.finance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.entity.Budget;
import com.lawfirm.model.finance.service.BudgetService;
import com.lawfirm.model.finance.vo.budget.BudgetDetailVO;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预算管理适配器
 */
@Component
public class BudgetAdaptor extends BaseAdaptor {

    @Autowired
    private BudgetService budgetService;

    /**
     * 创建预算
     */
    public Long createBudget(Budget budget) {
        return budgetService.createBudget(budget);
    }

    /**
     * 更新预算
     */
    public boolean updateBudget(Budget budget) {
        return budgetService.updateBudget(budget);
    }

    /**
     * 获取预算详情
     */
    public BudgetDetailVO getBudget(Long id) {
        Budget budget = budgetService.getBudgetById(id);
        return convert(budget, BudgetDetailVO.class);
    }

    /**
     * 删除预算
     */
    public boolean deleteBudget(Long id) {
        return budgetService.deleteBudget(id);
    }

    /**
     * 更新预算状态
     */
    public boolean updateBudgetStatus(Long budgetId, BudgetStatusEnum status, String remark) {
        return budgetService.updateBudgetStatus(budgetId, status, remark);
    }

    /**
     * 审批预算
     */
    public boolean approveBudget(Long budgetId, boolean approved, Long approverId, String remark) {
        return budgetService.approveBudget(budgetId, approved, approverId, remark);
    }

    /**
     * 调整预算
     */
    public boolean adjustBudget(Long budgetId, BigDecimal amount, Long operatorId, String remark) {
        return budgetService.adjustBudget(budgetId, amount, operatorId, remark);
    }

    /**
     * 查询预算列表
     */
    public List<BudgetDetailVO> listBudgets(BudgetStatusEnum status, Long departmentId, Long costCenterId,
                                          LocalDateTime startTime, LocalDateTime endTime) {
        List<Budget> budgets = budgetService.listBudgets(status, departmentId, costCenterId, startTime, endTime);
        return budgets.stream()
                .map(budget -> convert(budget, BudgetDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 分页查询预算
     */
    public IPage<BudgetDetailVO> pageBudgets(IPage<Budget> page, BudgetStatusEnum status, Long departmentId,
                                           Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        IPage<Budget> budgetPage = budgetService.pageBudgets(page, status, departmentId, costCenterId, startTime, endTime);
        return budgetPage.convert(budget -> convert(budget, BudgetDetailVO.class));
    }

    /**
     * 按部门查询预算
     */
    public List<BudgetDetailVO> listBudgetsByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Budget> budgets = budgetService.listBudgetsByDepartment(departmentId, startTime, endTime);
        return budgets.stream()
                .map(budget -> convert(budget, BudgetDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按成本中心查询预算
     */
    public List<BudgetDetailVO> listBudgetsByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Budget> budgets = budgetService.listBudgetsByCostCenter(costCenterId, startTime, endTime);
        return budgets.stream()
                .map(budget -> convert(budget, BudgetDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 统计预算金额
     */
    public BigDecimal sumBudgetAmount(BudgetStatusEnum status, Long departmentId, Long costCenterId,
                                    LocalDateTime startTime, LocalDateTime endTime) {
        return budgetService.sumBudgetAmount(status, departmentId, costCenterId, startTime, endTime);
    }

    /**
     * 统计部门预算金额
     */
    public BigDecimal sumDepartmentBudgetAmount(Long departmentId, BudgetStatusEnum status,
                                              LocalDateTime startTime, LocalDateTime endTime) {
        return budgetService.sumDepartmentBudgetAmount(departmentId, status, startTime, endTime);
    }

    /**
     * 统计成本中心预算金额
     */
    public BigDecimal sumCostCenterBudgetAmount(Long costCenterId, BudgetStatusEnum status,
                                              LocalDateTime startTime, LocalDateTime endTime) {
        return budgetService.sumCostCenterBudgetAmount(costCenterId, status, startTime, endTime);
    }

    /**
     * 导出预算数据
     */
    public String exportBudgets(List<Long> budgetIds) {
        return budgetService.exportBudgets(budgetIds);
    }
}