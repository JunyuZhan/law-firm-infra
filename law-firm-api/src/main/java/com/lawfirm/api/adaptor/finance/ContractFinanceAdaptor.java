package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.contract.ContractFinanceCreateDTO;
import com.lawfirm.model.finance.dto.contract.ContractFinanceUpdateDTO;
import com.lawfirm.model.finance.entity.ContractFinance;
import com.lawfirm.model.finance.service.ContractFinanceService;
import com.lawfirm.model.finance.vo.contract.ContractFinanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同财务管理适配器
 */
@Component
public class ContractFinanceAdaptor extends BaseAdaptor {

    @Autowired
    private ContractFinanceService contractFinanceService;

    /**
     * 创建合同财务记录
     */
    public ContractFinanceVO createContractFinance(ContractFinanceCreateDTO dto) {
        ContractFinance contractFinance = contractFinanceService.createContractFinance(dto);
        return convert(contractFinance, ContractFinanceVO.class);
    }

    /**
     * 更新合同财务记录
     */
    public ContractFinanceVO updateContractFinance(Long id, ContractFinanceUpdateDTO dto) {
        ContractFinance contractFinance = contractFinanceService.updateContractFinance(id, dto);
        return convert(contractFinance, ContractFinanceVO.class);
    }

    /**
     * 获取合同财务记录详情
     */
    public ContractFinanceVO getContractFinance(Long id) {
        ContractFinance contractFinance = contractFinanceService.getContractFinance(id);
        return convert(contractFinance, ContractFinanceVO.class);
    }

    /**
     * 删除合同财务记录
     */
    public void deleteContractFinance(Long id) {
        contractFinanceService.deleteContractFinance(id);
    }

    /**
     * 获取所有合同财务记录
     */
    public List<ContractFinanceVO> listContractFinances() {
        List<ContractFinance> contractFinances = contractFinanceService.listContractFinances();
        return contractFinances.stream()
                .map(contractFinance -> convert(contractFinance, ContractFinanceVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据合同ID查询财务记录
     */
    public List<ContractFinanceVO> getContractFinancesByContractId(Long contractId) {
        List<ContractFinance> contractFinances = contractFinanceService.getContractFinancesByContractId(contractId);
        return contractFinances.stream()
                .map(contractFinance -> convert(contractFinance, ContractFinanceVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据客户ID查询合同财务记录
     */
    public List<ContractFinanceVO> getContractFinancesByClientId(Long clientId) {
        List<ContractFinance> contractFinances = contractFinanceService.getContractFinancesByClientId(clientId);
        return contractFinances.stream()
                .map(contractFinance -> convert(contractFinance, ContractFinanceVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询合同财务记录
     */
    public List<ContractFinanceVO> getContractFinancesByDepartmentId(Long departmentId) {
        List<ContractFinance> contractFinances = contractFinanceService.getContractFinancesByDepartmentId(departmentId);
        return contractFinances.stream()
                .map(contractFinance -> convert(contractFinance, ContractFinanceVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 计算合同应收金额
     */
    public Double calculateContractReceivable(Long contractId) {
        return contractFinanceService.calculateContractReceivable(contractId);
    }

    /**
     * 计算合同已收金额
     */
    public Double calculateContractReceived(Long contractId) {
        return contractFinanceService.calculateContractReceived(contractId);
    }

    /**
     * 计算合同未收金额
     */
    public Double calculateContractUnreceived(Long contractId) {
        return contractFinanceService.calculateContractUnreceived(contractId);
    }

    /**
     * 检查合同财务记录是否存在
     */
    public boolean existsContractFinance(Long id) {
        return contractFinanceService.existsContractFinance(id);
    }

    /**
     * 获取合同财务记录数量
     */
    public long countContractFinances() {
        return contractFinanceService.countContractFinances();
    }
} 