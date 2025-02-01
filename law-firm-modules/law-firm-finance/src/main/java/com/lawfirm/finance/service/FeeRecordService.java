package com.lawfirm.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.finance.entity.FeeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 费用记录服务接口
 */
public interface FeeRecordService extends IService<FeeRecord> {
    
    /**
     * 创建费用记录
     */
    FeeRecord createFeeRecord(FeeRecord feeRecord);

    /**
     * 更新费用记录
     */
    FeeRecord updateFeeRecord(FeeRecord feeRecord);

    /**
     * 删除费用记录
     */
    void deleteFeeRecord(Long id);

    /**
     * 获取费用记录
     */
    FeeRecord getFeeRecord(Long id);

    /**
     * 获取律所的所有费用记录
     */
    List<FeeRecord> getLawFirmFeeRecords(Long lawFirmId);

    /**
     * 获取律所在指定时间范围内的费用记录
     */
    List<FeeRecord> getLawFirmFeeRecordsByTimeRange(Long lawFirmId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取律所的总收入
     */
    BigDecimal getTotalIncome(Long lawFirmId);

    /**
     * 获取律所的已收费用总额
     */
    BigDecimal getTotalPaidAmount(Long lawFirmId);

    /**
     * 支付费用
     */
    void payFee(Long feeId, BigDecimal amount);

    /**
     * 分页查询费用记录
     */
    Page<FeeRecord> findFeeRecords(Pageable pageable);

    /**
     * 根据案件ID查询费用记录
     */
    List<FeeRecord> findFeeRecordsByCaseId(Long caseId);

    /**
     * 根据客户ID查询费用记录
     */
    List<FeeRecord> findFeeRecordsByClientId(Long clientId);

    /**
     * 更新支付状态
     */
    FeeRecord updatePaymentStatus(Long id, String status, BigDecimal paidAmount);

    /**
     * 计算总金额
     */
    BigDecimal calculateTotalAmount(Long lawFirmId);

    /**
     * 计算已支付总金额
     */
    BigDecimal calculateTotalPaidAmount(Long lawFirmId);
} 