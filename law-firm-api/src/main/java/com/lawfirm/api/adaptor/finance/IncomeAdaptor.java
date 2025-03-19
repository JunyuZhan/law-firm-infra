package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.income.IncomeCreateDTO;
import com.lawfirm.model.finance.dto.income.IncomeUpdateDTO;
import com.lawfirm.model.finance.entity.Income;
import com.lawfirm.model.finance.service.IncomeService;
import com.lawfirm.model.finance.vo.income.IncomeDetailVO;
import com.lawfirm.model.finance.enums.IncomeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 收入管理适配器
 */
@Component
public class IncomeAdaptor extends BaseAdaptor {

    @Autowired
    private IncomeService incomeService;

    /**
     * 创建收入
     */
    public IncomeDetailVO createIncome(IncomeCreateDTO dto) {
        Income income = convert(dto, Income.class);
        Long id = incomeService.recordIncome(income);
        return convert(incomeService.getIncomeById(id), IncomeDetailVO.class);
    }

    /**
     * 更新收入
     */
    public IncomeDetailVO updateIncome(Long id, IncomeUpdateDTO dto) {
        Income income = convert(dto, Income.class);
        income.setId(id);
        incomeService.updateIncome(income);
        return convert(incomeService.getIncomeById(id), IncomeDetailVO.class);
    }

    /**
     * 获取收入详情
     */
    public IncomeDetailVO getIncome(Long id) {
        Income income = incomeService.getIncomeById(id);
        return convert(income, IncomeDetailVO.class);
    }

    /**
     * 删除收入
     */
    public void deleteIncome(Long id) {
        incomeService.deleteIncome(id);
    }

    /**
     * 获取所有收入
     */
    public List<IncomeDetailVO> listIncomes() {
        List<Income> incomes = incomeService.listIncomes(null, null, null, null);
        return incomes.stream()
                .map(income -> convert(income, IncomeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据收入类型查询收入
     */
    public List<IncomeDetailVO> getIncomesByType(IncomeTypeEnum type) {
        List<Income> incomes = incomeService.listIncomes(type.ordinal(), null, null, null);
        return incomes.stream()
                .map(income -> convert(income, IncomeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据客户ID查询收入
     */
    public List<IncomeDetailVO> getIncomesByClientId(Long clientId) {
        List<Income> incomes = incomeService.listIncomesByClient(clientId, null, null);
        return incomes.stream()
                .map(income -> convert(income, IncomeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据合同ID查询收入
     */
    public List<IncomeDetailVO> getIncomesByContractId(Long contractId) {
        List<Income> incomes = incomeService.listIncomesByContract(contractId);
        return incomes.stream()
                .map(income -> convert(income, IncomeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询收入
     */
    public List<IncomeDetailVO> getIncomesByDepartmentId(Long departmentId) {
        // 由于IncomeService没有直接提供按部门查询的方法，我们需要在Service层添加这个方法
        // 暂时返回空列表
        return List.of();
    }

    /**
     * 检查收入是否存在
     */
    public boolean existsIncome(Long id) {
        return incomeService.getIncomeById(id) != null;
    }

    /**
     * 获取收入数量
     */
    public long countIncomes() {
        return incomeService.listIncomes(null, null, null, null).size();
    }
}