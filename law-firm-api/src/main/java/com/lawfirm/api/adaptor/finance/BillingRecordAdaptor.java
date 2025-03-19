package com.lawfirm.api.adaptor.finance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.entity.BillingRecord;
import com.lawfirm.model.finance.service.BillingRecordService;
import com.lawfirm.model.finance.vo.billing.BillingDetailVO;
import com.lawfirm.model.finance.enums.BillingStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    public Long createBillingRecord(BillingRecord billingRecord) {
        return billingRecordService.createBillingRecord(billingRecord);
    }

    /**
     * 更新账单记录
     */
    public boolean updateBillingRecord(BillingRecord billingRecord) {
        return billingRecordService.updateBillingRecord(billingRecord);
    }

    /**
     * 获取账单记录详情
     */
    public BillingDetailVO getBillingRecord(Long id) {
        BillingRecord billingRecord = billingRecordService.getBillingRecordById(id);
        return convert(billingRecord, BillingDetailVO.class);
    }

    /**
     * 删除账单记录
     */
    public boolean deleteBillingRecord(Long id) {
        return billingRecordService.deleteBillingRecord(id);
    }

    /**
     * 更新账单记录状态
     */
    public boolean updateBillingRecordStatus(Long billingRecordId, BillingStatusEnum status, String remark) {
        return billingRecordService.updateBillingRecordStatus(billingRecordId, status, remark);
    }

    /**
     * 确认账单
     */
    public boolean confirmBillingRecord(Long billingRecordId, Long operatorId, String remark) {
        return billingRecordService.confirmBillingRecord(billingRecordId, operatorId, remark);
    }

    /**
     * 取消账单
     */
    public boolean cancelBillingRecord(Long billingRecordId, String reason) {
        return billingRecordService.cancelBillingRecord(billingRecordId, reason);
    }

    /**
     * 查询账单记录列表
     */
    public List<BillingDetailVO> listBillingRecords(BillingStatusEnum status, Long clientId, Long caseId,
                                                   LocalDateTime startTime, LocalDateTime endTime) {
        List<BillingRecord> records = billingRecordService.listBillingRecords(status, clientId, caseId, startTime, endTime);
        return records.stream()
                .map(record -> convert(record, BillingDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 分页查询账单记录
     */
    public IPage<BillingDetailVO> pageBillingRecords(IPage<BillingRecord> page, BillingStatusEnum status,
                                                    Long clientId, Long caseId, LocalDateTime startTime, LocalDateTime endTime) {
        IPage<BillingRecord> recordPage = billingRecordService.pageBillingRecords(page, status, clientId, caseId, startTime, endTime);
        return recordPage.convert(record -> convert(record, BillingDetailVO.class));
    }

    /**
     * 按客户查询账单记录
     */
    public List<BillingDetailVO> listBillingRecordsByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        List<BillingRecord> records = billingRecordService.listBillingRecordsByClient(clientId, startTime, endTime);
        return records.stream()
                .map(record -> convert(record, BillingDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按案件查询账单记录
     */
    public List<BillingDetailVO> listBillingRecordsByCase(Long caseId) {
        List<BillingRecord> records = billingRecordService.listBillingRecordsByCase(caseId);
        return records.stream()
                .map(record -> convert(record, BillingDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 统计账单金额
     */
    public BigDecimal sumBillingAmount(BillingStatusEnum status, Long clientId, Long caseId,
                                     LocalDateTime startTime, LocalDateTime endTime) {
        return billingRecordService.sumBillingAmount(status, clientId, caseId, startTime, endTime);
    }

    /**
     * 统计客户账单金额
     */
    public BigDecimal sumClientBillingAmount(Long clientId, BillingStatusEnum status,
                                           LocalDateTime startTime, LocalDateTime endTime) {
        return billingRecordService.sumClientBillingAmount(clientId, status, startTime, endTime);
    }

    /**
     * 统计案件账单金额
     */
    public BigDecimal sumCaseBillingAmount(Long caseId, BillingStatusEnum status) {
        return billingRecordService.sumCaseBillingAmount(caseId, status);
    }

    /**
     * 导出账单记录数据
     */
    public String exportBillingRecords(List<Long> billingRecordIds) {
        return billingRecordService.exportBillingRecords(billingRecordIds);
    }
} 