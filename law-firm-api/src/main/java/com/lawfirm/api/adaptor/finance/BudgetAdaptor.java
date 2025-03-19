package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.budget.BudgetCreateDTO;
import com.lawfirm.model.finance.dto.budget.BudgetUpdateDTO;
import com.lawfirm.model.finance.entity.Budget;
import com.lawfirm.model.finance.service.BudgetService;
import com.lawfirm.model.finance.vo.budget.BudgetVO;
import com.lawfirm.model.finance.enums.BudgetStatusEnum;
import com.lawfirm.model.finance.enums.BudgetTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public BudgetVO createBudget(BudgetCreateDTO dto) {
        Budget budget = budgetService.createBudget(dto);
        return convert(budget, BudgetVO.class);
    }

    /**
     * 更新预算
     */
    public BudgetVO updateBudget(Long id, BudgetUpdateDTO dto) {
        Budget budget = budgetService.updateBudget(id, dto);
        return convert(budget, BudgetVO.class);
    }

    /**
     * 获取预算详情
     */
    public BudgetVO getBudget(Long id) {
        Budget budget = budgetService.getBudget(id);
        return convert(budget, BudgetVO.class);
    }

    /**
     * 删除预算
     */
    public void deleteBudget(Long id) {
        budgetService.deleteBudget(id);
    }

    /**
     * 获取所有预算
     */
    public List<BudgetVO> listBudgets() {
        List<Budget> budgets = budgetService.listBudgets();
        return budgets.stream()
                .map(budget -> convert(budget, BudgetVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新预算状态
     */
    public void updateBudgetStatus(Long id, BudgetStatusEnum status) {
        budgetService.updateBudgetStatus(id, status);
    }

    /**
     * 根据预算类型查询预算
     */
    public List<BudgetVO> getBudgetsByType(BudgetTypeEnum type) {
        List<Budget> budgets = budgetService.getBudgetsByType(type);
        return budgets.stream()
                .map(budget -> convert(budget, BudgetVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询预算
     */
    public List<BudgetVO> getBudgetsByDepartmentId(Long departmentId) {
        List<Budget> budgets = budgetService.getBudgetsByDepartmentId(departmentId);
        return budgets.stream()
                .map(budget -> convert(budget, BudgetVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查预算是否存在
     */
    public boolean existsBudget(Long id) {
        return budgetService.existsBudget(id);
    }

    /**
     * 获取预算数量
     */
    public long countBudgets() {
        return budgetService.countBudgets();
    }
} 