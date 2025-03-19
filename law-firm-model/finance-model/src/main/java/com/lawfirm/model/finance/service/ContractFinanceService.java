package com.lawfirm.model.finance.service;

import com.lawfirm.model.finance.entity.Income;
import com.lawfirm.model.finance.entity.Invoice;
import com.lawfirm.model.finance.entity.PaymentPlan;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.vo.contract.ContractFinanceVO;
import com.lawfirm.model.finance.vo.receivable.ReceivableDetailVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同财务服务接口
 * 处理合同与财务模块的交互
 */
public interface ContractFinanceService {

    /**
     * 获取合同财务信息
     *
     * @param contractId 合同ID
     * @return 合同财务信息
     */
    ContractFinanceVO getContractFinanceInfo(Long contractId);

    /**
     * 更新合同实际金额
     *
     * @param contractId   合同ID
     * @param actualAmount 实际金额
     * @param remark       备注
     * @return 是否更新成功
     */
    boolean updateContractAmount(Long contractId, BigDecimal actualAmount, String remark);

    /**
     * 更新合同税率
     *
     * @param contractId 合同ID
     * @param taxRate    税率
     * @param remark     备注
     * @return 是否更新成功
     */
    boolean updateContractTaxRate(Long contractId, BigDecimal taxRate, String remark);

    /**
     * 获取合同收款计划
     *
     * @param contractId 合同ID
     * @return 收款计划
     */
    Object getContractPaymentPlan(Long contractId);

    /**
     * 更新合同收款计划
     *
     * @param contractId 合同ID
     * @param planData   计划数据
     * @return 是否更新成功
     */
    boolean updateContractPaymentPlan(Long contractId, Object planData);

    /**
     * 获取合同发票列表（对象格式）
     *
     * @param contractId 合同ID
     * @return 发票列表
     */
    List<Invoice> getContractInvoiceObjects(Long contractId);

    /**
     * 获取合同财务审计记录
     *
     * @param contractId   合同ID
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @return 审计记录列表
     */
    List<Object> getContractFinanceAuditLogs(Long contractId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * 导出合同财务报表
     *
     * @param contractId 合同ID
     * @return 导出文件URL
     */
    String exportContractFinanceReport(Long contractId);

    /**
     * 根据合同创建应收账款
     *
     * @param contractId 合同ID
     * @param contractNo 合同编号
     * @param amount     合同金额
     * @param clientId   客户ID
     * @return 应收账款ID
     */
    Long createContractReceivable(Long contractId, String contractNo, BigDecimal amount, Long clientId);

    /**
     * 更新合同应收账款
     *
     * @param contractId   合同ID
     * @param contractNo   合同编号
     * @param updateAmount 更新金额
     * @return 是否更新成功
     */
    boolean updateContractReceivable(Long contractId, String contractNo, BigDecimal updateAmount);

    /**
     * 创建合同收款计划
     *
     * @param contractId   合同ID
     * @param contractNo   合同编号
     * @param planName     计划名称
     * @param totalAmount  总金额
     * @param installments 期数
     * @param clientId     客户ID
     * @return 收款计划ID
     */
    Long createPaymentPlan(Long contractId, String contractNo, String planName, 
                         BigDecimal totalAmount, Integer installments, Long clientId);

    /**
     * 记录合同收款
     *
     * @param contractId  合同ID
     * @param contractNo  合同编号
     * @param amount      收款金额
     * @param accountId   收款账户ID
     * @param description 说明
     * @return 收入记录ID
     */
    Long recordContractIncome(Long contractId, String contractNo, BigDecimal amount, 
                             Long accountId, String description);

    /**
     * 处理合同发票申请
     *
     * @param contractId  合同ID
     * @param contractNo  合同编号
     * @param amount      开票金额
     * @param description 说明
     * @return 发票ID
     */
    Long processContractInvoice(Long contractId, String contractNo, BigDecimal amount, String description);

    /**
     * 获取合同应收账款列表
     *
     * @param contractId 合同ID
     * @return 应收账款列表
     */
    List<ReceivableDetailVO> getContractReceivables(Long contractId);

    /**
     * 获取合同收款记录
     *
     * @param contractId 合同ID
     * @return 收款记录列表
     */
    List<Income> getContractIncomes(Long contractId);

    /**
     * 获取合同收款计划
     *
     * @param contractId 合同ID
     * @return 收款计划列表
     */
    List<PaymentPlan> getContractPaymentPlans(Long contractId);

    /**
     * 获取合同发票记录
     *
     * @param contractId 合同ID
     * @return 发票记录列表
     */
    List<Invoice> getContractInvoices(Long contractId);

    /**
     * 统计合同财务状况
     *
     * @param contractId 合同ID
     * @return 合同财务状况统计
     */
    ContractFinanceSummary getContractFinanceSummary(Long contractId);

    /**
     * 合同财务汇总信息
     */
    interface ContractFinanceSummary {
        /**
         * 获取合同总金额
         */
        BigDecimal getTotalAmount();

        /**
         * 获取已收金额
         */
        BigDecimal getReceivedAmount();

        /**
         * 获取待收金额
         */
        BigDecimal getUnreceivedAmount();

        /**
         * 获取已开票金额
         */
        BigDecimal getInvoicedAmount();

        /**
         * 获取待开票金额
         */
        BigDecimal getUninvoicedAmount();

        /**
         * 获取收款进度百分比
         */
        BigDecimal getReceiveProgress();

        /**
         * 获取开票进度百分比
         */
        BigDecimal getInvoiceProgress();
    }
} 