package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.billing.BillingRecordCreateDTO;
import com.lawfirm.model.finance.dto.billing.BillingRecordUpdateDTO;
import com.lawfirm.model.finance.entity.BillingRecord;
import com.lawfirm.model.finance.service.BillingRecordService;
import com.lawfirm.model.finance.vo.billing.BillingRecordVO;
import com.lawfirm.model.finance.enums.BillingStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 账单记录管理适配器
 */
@Component
public class BillingRecordAdaptor extends BaseAdaptor {

    @Autowired
    private BillingRecordService billingRecordService;

    /**
     * 创建账单记录
     */
    public BillingRecordVO createBillingRecord(BillingRecordCreateDTO dto) {
        BillingRecord billingRecord = billingRecordService.createBillingRecord(dto);
        return convert(billingRecord, BillingRecordVO.class);
    }

    /**
     * 更新账单记录
     */
    public BillingRecordVO updateBillingRecord(Long id, BillingRecordUpdateDTO dto) {
        BillingRecord billingRecord = billingRecordService.updateBillingRecord(id, dto);
        return convert(billingRecord, BillingRecordVO.class);
    }

    /**
     * 获取账单记录详情
     */
    public BillingRecordVO getBillingRecord(Long id) {
        BillingRecord billingRecord = billingRecordService.getBillingRecord(id);
        return convert(billingRecord, BillingRecordVO.class);
    }

    /**
     * 删除账单记录
     */
    public void deleteBillingRecord(Long id) {
        billingRecordService.deleteBillingRecord(id);
    }

    /**
     * 获取所有账单记录
     */
    public List<BillingRecordVO> listBillingRecords() {
        List<BillingRecord> billingRecords = billingRecordService.listBillingRecords();
        return billingRecords.stream()
                .map(billingRecord -> convert(billingRecord, BillingRecordVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新账单记录状态
     */
    public void updateBillingRecordStatus(Long id, BillingStatusEnum status) {
        billingRecordService.updateBillingRecordStatus(id, status);
    }

    /**
     * 根据合同ID查询账单记录
     */
    public List<BillingRecordVO> getBillingRecordsByContractId(Long contractId) {
        List<BillingRecord> billingRecords = billingRecordService.getBillingRecordsByContractId(contractId);
        return billingRecords.stream()
                .map(billingRecord -> convert(billingRecord, BillingRecordVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据客户ID查询账单记录
     */
    public List<BillingRecordVO> getBillingRecordsByClientId(Long clientId) {
        List<BillingRecord> billingRecords = billingRecordService.getBillingRecordsByClientId(clientId);
        return billingRecords.stream()
                .map(billingRecord -> convert(billingRecord, BillingRecordVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询账单记录
     */
    public List<BillingRecordVO> getBillingRecordsByDepartmentId(Long departmentId) {
        List<BillingRecord> billingRecords = billingRecordService.getBillingRecordsByDepartmentId(departmentId);
        return billingRecords.stream()
                .map(billingRecord -> convert(billingRecord, BillingRecordVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据付款计划ID查询账单记录
     */
    public List<BillingRecordVO> getBillingRecordsByPaymentPlanId(Long paymentPlanId) {
        List<BillingRecord> billingRecords = billingRecordService.getBillingRecordsByPaymentPlanId(paymentPlanId);
        return billingRecords.stream()
                .map(billingRecord -> convert(billingRecord, BillingRecordVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查账单记录是否存在
     */
    public boolean existsBillingRecord(Long id) {
        return billingRecordService.existsBillingRecord(id);
    }

    /**
     * 获取账单记录数量
     */
    public long countBillingRecords() {
        return billingRecordService.countBillingRecords();
    }
} 