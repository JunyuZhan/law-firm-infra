package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.finance.entity.Income;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.entity.PaymentPlan;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import com.lawfirm.model.finance.service.ContractFinanceService;
import com.lawfirm.model.finance.service.IncomeService;
import com.lawfirm.model.finance.service.InvoiceService;
import com.lawfirm.model.finance.service.PaymentPlanService;
import com.lawfirm.model.finance.service.ReceivableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同财务服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContractFinanceServiceImpl implements ContractFinanceService {

    private final ReceivableService receivableService;
    private final IncomeService incomeService;
    private final InvoiceService invoiceService;
    private final PaymentPlanService paymentPlanService;
    private final SecurityContext securityContext;

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "contract_finance", allEntries = true)
    public Long createContractReceivable(Long contractId, String contractNo, BigDecimal amount, Long clientId) {
        log.info("创建合同应收账款: contractId={}, contractNo={}, amount={}, clientId={}", 
                contractId, contractNo, amount, clientId);
        
        Receivable receivable = new Receivable();
        receivable.setContractId(contractId);
        receivable.setContractNo(contractNo);
        receivable.setTotalAmount(amount);
        receivable.setClientId(clientId);
        receivable.setStatusCode(1);
        receivable.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        receivable.setCreateTime(LocalDateTime.now());
        receivable.setUpdateTime(LocalDateTime.now());
        
        receivableService.createReceivable(receivable);
        return receivable.getId();
    }

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "contract_finance", allEntries = true)
    public boolean updateContractReceivable(Long contractId, String contractNo, BigDecimal updateAmount) {
        log.info("更新合同应收账款: contractId={}, contractNo={}, updateAmount={}", 
                contractId, contractNo, updateAmount);
        List<Receivable> receivables = receivableService.listReceivablesByContract(contractId);
        if (receivables.isEmpty()) {
            return false;
        }
        Receivable receivable = receivables.get(0);
        receivable.setTotalAmount(updateAmount);
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        receivable.setUpdateTime(LocalDateTime.now());
        return receivableService.updateReceivable(receivable);
    }

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "contract_finance", allEntries = true)
    public Long createPaymentPlan(Long contractId, String contractNo, String planName, 
                                BigDecimal totalAmount, Integer installments, Long clientId) {
        log.info("创建合同收款计划: contractId={}, contractNo={}, planName={}, totalAmount={}, installments={}, clientId={}", 
                contractId, contractNo, planName, totalAmount, installments, clientId);
        
        PaymentPlan plan = new PaymentPlan();
        plan.setPlanName(planName);
        plan.setTotalAmount(totalAmount);
        plan.setInstallments(installments);
        plan.setClientId(clientId);
        plan.setStatus(1);
        plan.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        plan.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        plan.setCreateTime(LocalDateTime.now());
        plan.setUpdateTime(LocalDateTime.now());
        
        paymentPlanService.createPaymentPlan(plan);
        return plan.getId();
    }

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "contract_finance", allEntries = true)
    public Long recordContractIncome(Long contractId, String contractNo, BigDecimal amount, 
                                   Long accountId, String description) {
        log.info("记录合同收款: contractId={}, contractNo={}, amount={}, accountId={}, description={}", 
                contractId, contractNo, amount, accountId, description);
        
        Income income = new Income();
        income.setCaseId(contractId);
        income.setAmount(amount);
        income.setAccountId(accountId);
        income.setDescription(description);
        income.setConfirmStatus(1);
        income.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        income.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        income.setCreateTime(LocalDateTime.now());
        income.setUpdateTime(LocalDateTime.now());
        
        return incomeService.recordIncome(income);
    }

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "contract_finance", allEntries = true)
    public Long processContractInvoice(Long contractId, String contractNo, BigDecimal amount, String description) {
        log.info("处理合同发票申请: contractId={}, contractNo={}, amount={}, description={}", 
                contractId, contractNo, amount, description);
        
        Invoice invoice = new Invoice();
        invoice.setCaseId(contractId);
        invoice.setAmount(amount);
        invoice.setContent(description);
        invoice.setInvoiceStatus(InvoiceStatusEnum.PENDING);
        invoice.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        invoice.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        invoice.setCreateTime(LocalDateTime.now());
        invoice.setUpdateTime(LocalDateTime.now());
        
        invoiceService.createInvoice(invoice);
        return invoice.getId();
    }

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'view')")
    @Cacheable(value = "contract_finance", key = "'receivables:' + #contractId")
    public List<Receivable> getContractReceivables(Long contractId) {
        log.info("获取合同应收账款: contractId={}", contractId);
        return receivableService.listReceivablesByContract(contractId);
    }

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'view')")
    @Cacheable(value = "contract_finance", key = "'incomes:' + #contractId")
    public List<Income> getContractIncomes(Long contractId) {
        log.info("获取合同收款记录: contractId={}", contractId);
        return incomeService.listIncomesByContract(contractId);
    }

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'view')")
    @Cacheable(value = "contract_finance", key = "'payment_plans:' + #contractId")
    public List<PaymentPlan> getContractPaymentPlans(Long contractId) {
        log.info("获取合同收款计划: contractId={}", contractId);
        return paymentPlanService.listPaymentPlansByContract(contractId);
    }

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'view')")
    @Cacheable(value = "contract_finance", key = "'invoices:' + #contractId")
    public List<Invoice> getContractInvoices(Long contractId) {
        log.info("获取合同发票记录: contractId={}", contractId);
        return invoiceService.listInvoicesByContract(contractId);
    }

    @Override
    @PreAuthorize("hasPermission('contract_finance', 'view')")
    @Cacheable(value = "contract_finance", key = "'summary:' + #contractId")
    public ContractFinanceSummary getContractFinanceSummary(Long contractId) {
        log.info("统计合同财务状况: contractId={}", contractId);
        List<Receivable> receivables = getContractReceivables(contractId);
        List<Income> incomes = getContractIncomes(contractId);
        List<Invoice> invoices = getContractInvoices(contractId);

        BigDecimal totalAmount = receivables.stream()
                .map(Receivable::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal receivedAmount = incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal invoicedAmount = invoices.stream()
                .map(Invoice::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ContractFinanceSummary() {
            @Override
            public BigDecimal getTotalAmount() {
                return totalAmount;
            }

            @Override
            public BigDecimal getReceivedAmount() {
                return receivedAmount;
            }

            @Override
            public BigDecimal getUnreceivedAmount() {
                return totalAmount.subtract(receivedAmount);
            }

            @Override
            public BigDecimal getInvoicedAmount() {
                return invoicedAmount;
            }

            @Override
            public BigDecimal getUninvoicedAmount() {
                return totalAmount.subtract(invoicedAmount);
            }

            @Override
            public BigDecimal getReceiveProgress() {
                if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
                    return BigDecimal.ZERO;
                }
                return receivedAmount.divide(totalAmount, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"));
            }

            @Override
            public BigDecimal getInvoiceProgress() {
                if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
                    return BigDecimal.ZERO;
                }
                return invoicedAmount.divide(totalAmount, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"));
            }
        };
    }
}