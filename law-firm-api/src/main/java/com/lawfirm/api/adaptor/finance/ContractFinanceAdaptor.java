package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.entity.Income;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.entity.PaymentPlan;
import com.lawfirm.model.finance.service.ContractFinanceService;
import com.lawfirm.model.finance.vo.contract.ContractFinanceVO;
import com.lawfirm.model.finance.vo.receivable.ReceivableDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同财务管理适配器
 */
@Component
public class ContractFinanceAdaptor extends BaseAdaptor {

    @Autowired
    private ContractFinanceService contractFinanceService;

    /**
     * 获取合同财务信息
     */
    public ContractFinanceVO getContractFinanceInfo(Long contractId) {
        return contractFinanceService.getContractFinanceInfo(contractId);
    }

    /**
     * 更新合同实际金额
     */
    public boolean updateContractAmount(Long contractId, BigDecimal actualAmount, String remark) {
        return contractFinanceService.updateContractAmount(contractId, actualAmount, remark);
    }

    /**
     * 更新合同税率
     */
    public boolean updateContractTaxRate(Long contractId, BigDecimal taxRate, String remark) {
        return contractFinanceService.updateContractTaxRate(contractId, taxRate, remark);
    }

    /**
     * 获取合同收款计划
     */
    public Object getContractPaymentPlan(Long contractId) {
        return contractFinanceService.getContractPaymentPlan(contractId);
    }

    /**
     * 更新合同收款计划
     */
    public boolean updateContractPaymentPlan(Long contractId, Object planData) {
        return contractFinanceService.updateContractPaymentPlan(contractId, planData);
    }

    /**
     * 获取合同发票列表
     */
    public List<Invoice> getContractInvoiceObjects(Long contractId) {
        return contractFinanceService.getContractInvoiceObjects(contractId);
    }

    /**
     * 获取合同财务审计记录
     */
    public List<Object> getContractFinanceAuditLogs(Long contractId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return contractFinanceService.getContractFinanceAuditLogs(contractId, startDateTime, endDateTime);
    }

    /**
     * 导出合同财务报表
     */
    public String exportContractFinanceReport(Long contractId) {
        return contractFinanceService.exportContractFinanceReport(contractId);
    }

    /**
     * 创建合同应收账款
     */
    public Long createContractReceivable(Long contractId, String contractNo, BigDecimal amount, Long clientId) {
        return contractFinanceService.createContractReceivable(contractId, contractNo, amount, clientId);
    }

    /**
     * 更新合同应收账款
     */
    public boolean updateContractReceivable(Long contractId, String contractNo, BigDecimal updateAmount) {
        return contractFinanceService.updateContractReceivable(contractId, contractNo, updateAmount);
    }

    /**
     * 创建合同收款计划
     */
    public Long createPaymentPlan(Long contractId, String contractNo, String planName, 
                         BigDecimal totalAmount, Integer installments, Long clientId) {
        return contractFinanceService.createPaymentPlan(contractId, contractNo, planName, 
                                      totalAmount, installments, clientId);
    }

    /**
     * 记录合同收款
     */
    public Long recordContractIncome(Long contractId, String contractNo, BigDecimal amount, 
                           Long accountId, String description) {
        return contractFinanceService.recordContractIncome(contractId, contractNo, amount, 
                                     accountId, description);
    }

    /**
     * 处理合同发票申请
     */
    public Long processContractInvoice(Long contractId, String contractNo, BigDecimal amount, String description) {
        return contractFinanceService.processContractInvoice(contractId, contractNo, amount, description);
    }

    /**
     * 获取合同应收账款列表
     */
    public List<ReceivableDetailVO> getContractReceivables(Long contractId) {
        return contractFinanceService.getContractReceivables(contractId);
    }

    /**
     * 获取合同收款记录
     */
    public List<Income> getContractIncomes(Long contractId) {
        return contractFinanceService.getContractIncomes(contractId);
    }

    /**
     * 获取合同收款计划列表
     */
    public List<PaymentPlan> getContractPaymentPlans(Long contractId) {
        return contractFinanceService.getContractPaymentPlans(contractId);
    }

    /**
     * 获取合同发票记录
     */
    public List<Invoice> getContractInvoices(Long contractId) {
        return contractFinanceService.getContractInvoices(contractId);
    }

    /**
     * 获取合同财务汇总信息
     */
    public ContractFinanceService.ContractFinanceSummary getContractFinanceSummary(Long contractId) {
        return contractFinanceService.getContractFinanceSummary(contractId);
    }
} 