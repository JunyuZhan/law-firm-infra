package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.income.IncomeCreateDTO;
import com.lawfirm.model.finance.dto.income.IncomeUpdateDTO;
import com.lawfirm.model.finance.entity.Income;
import com.lawfirm.model.finance.service.IncomeService;
import com.lawfirm.model.finance.vo.income.IncomeVO;
import com.lawfirm.model.finance.enums.IncomeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public IncomeVO createIncome(IncomeCreateDTO dto) {
        Income income = incomeService.createIncome(dto);
        return convert(income, IncomeVO.class);
    }

    /**
     * 更新收入
     */
    public IncomeVO updateIncome(Long id, IncomeUpdateDTO dto) {
        Income income = incomeService.updateIncome(id, dto);
        return convert(income, IncomeVO.class);
    }

    /**
     * 获取收入详情
     */
    public IncomeVO getIncome(Long id) {
        Income income = incomeService.getIncome(id);
        return convert(income, IncomeVO.class);
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
    public List<IncomeVO> listIncomes() {
        List<Income> incomes = incomeService.listIncomes();
        return incomes.stream()
                .map(income -> convert(income, IncomeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据收入类型查询收入
     */
    public List<IncomeVO> getIncomesByType(IncomeTypeEnum type) {
        List<Income> incomes = incomeService.getIncomesByType(type);
        return incomes.stream()
                .map(income -> convert(income, IncomeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据客户ID查询收入
     */
    public List<IncomeVO> getIncomesByClientId(Long clientId) {
        List<Income> incomes = incomeService.getIncomesByClientId(clientId);
        return incomes.stream()
                .map(income -> convert(income, IncomeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据合同ID查询收入
     */
    public List<IncomeVO> getIncomesByContractId(Long contractId) {
        List<Income> incomes = incomeService.getIncomesByContractId(contractId);
        return incomes.stream()
                .map(income -> convert(income, IncomeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询收入
     */
    public List<IncomeVO> getIncomesByDepartmentId(Long departmentId) {
        List<Income> incomes = incomeService.getIncomesByDepartmentId(departmentId);
        return incomes.stream()
                .map(income -> convert(income, IncomeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查收入是否存在
     */
    public boolean existsIncome(Long id) {
        return incomeService.existsIncome(id);
    }

    /**
     * 获取收入数量
     */
    public long countIncomes() {
        return incomeService.countIncomes();
    }
} 