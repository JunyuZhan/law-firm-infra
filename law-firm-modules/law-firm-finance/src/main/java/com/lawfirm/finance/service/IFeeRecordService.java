package com.lawfirm.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.finance.entity.FeeRecord;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 收费记录Service接口
 */
public interface IFeeRecordService extends IService<FeeRecord> {

    /**
     * 创建收费记录
     *
     * @param feeRecord 收费记录
     * @return 收费记录ID
     */
    Long createFeeRecord(FeeRecord feeRecord);

    /**
     * 更新收费记录
     *
     * @param feeRecord 收费记录
     */
    void updateFeeRecord(FeeRecord feeRecord);

    /**
     * 删除收费记录
     *
     * @param id 收费记录ID
     */
    void deleteFeeRecord(Long id);

    /**
     * 分页查询收费记录
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param feeType 收费类型
     * @param feeStatus 收费状态
     * @param clientId 客户ID
     * @param lawFirmId 律所ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    IPage<FeeRecord> pageFeeRecords(int pageNum, int pageSize, String feeType, String feeStatus, 
                                   Long clientId, Long lawFirmId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取收费记录详情
     *
     * @param id 收费记录ID
     * @return 收费记录
     */
    FeeRecord getFeeRecordById(Long id);

    /**
     * 获取客户未支付金额
     *
     * @param clientId 客户ID
     * @return 未支付金额
     */
    BigDecimal getUnpaidAmount(Long clientId);

    /**
     * 支付费用
     *
     * @param id 收费记录ID
     * @param amount 支付金额
     */
    void payFee(Long id, BigDecimal amount);

    /**
     * 根据案件ID查询收费记录
     *
     * @param caseId 案件ID
     * @return 收费记录列表
     */
    List<FeeRecord> getFeeRecordsByCaseId(Long caseId);

    /**
     * 根据客户ID查询收费记录
     *
     * @param clientId 客户ID
     * @return 收费记录列表
     */
    List<FeeRecord> getFeeRecordsByClientId(Long clientId);

    /**
     * 获取律所收费统计
     *
     * @param lawFirmId 律所ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 收费记录列表
     */
    List<FeeRecord> getFeeStatistics(Long lawFirmId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 计算总收费金额
     *
     * @param lawFirmId 律所ID
     * @return 总金额
     */
    BigDecimal calculateTotalAmount(Long lawFirmId);

    /**
     * 计算已收费金额
     *
     * @param lawFirmId 律所ID
     * @return 已收金额
     */
    BigDecimal calculateTotalPaidAmount(Long lawFirmId);
} 