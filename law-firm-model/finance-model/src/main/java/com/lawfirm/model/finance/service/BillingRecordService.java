package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.BillingRecord;
import com.lawfirm.model.finance.enums.BillingStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 账单记录服务接口
 */
public interface BillingRecordService {
    
    /**
     * 创建账单记录
     *
     * @param billingRecord 账单记录信息
     * @return 账单记录ID
     */
    Long createBillingRecord(BillingRecord billingRecord);
    
    /**
     * 更新账单记录
     *
     * @param billingRecord 账单记录信息
     * @return 是否更新成功
     */
    boolean updateBillingRecord(BillingRecord billingRecord);
    
    /**
     * 删除账单记录
     *
     * @param billingRecordId 账单记录ID
     * @return 是否删除成功
     */
    boolean deleteBillingRecord(Long billingRecordId);
    
    /**
     * 获取账单记录详情
     *
     * @param billingRecordId 账单记录ID
     * @return 账单记录信息
     */
    BillingRecord getBillingRecordById(Long billingRecordId);
    
    /**
     * 更新账单记录状态
     *
     * @param billingRecordId 账单记录ID
     * @param status 状态
     * @param remark 备注
     * @return 是否更新成功
     */
    boolean updateBillingRecordStatus(Long billingRecordId, BillingStatusEnum status, String remark);
    
    /**
     * 确认账单
     *
     * @param billingRecordId 账单记录ID
     * @param operatorId 操作人ID
     * @param remark 确认意见
     * @return 是否确认成功
     */
    boolean confirmBillingRecord(Long billingRecordId, Long operatorId, String remark);
    
    /**
     * 取消账单
     *
     * @param billingRecordId 账单记录ID
     * @param reason 取消原因
     * @return 是否取消成功
     */
    boolean cancelBillingRecord(Long billingRecordId, String reason);
    
    /**
     * 查询账单记录列表
     *
     * @param status 账单状态，可为null
     * @param clientId 客户ID，可为null
     * @param caseId 案件ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 账单记录列表
     */
    List<BillingRecord> listBillingRecords(BillingStatusEnum status, Long clientId, Long caseId,
                                          LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询账单记录
     *
     * @param page 分页参数
     * @param status 账单状态，可为null
     * @param clientId 客户ID，可为null
     * @param caseId 案件ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页账单记录信息
     */
    IPage<BillingRecord> pageBillingRecords(IPage<BillingRecord> page, BillingStatusEnum status,
                                           Long clientId, Long caseId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按客户查询账单记录
     *
     * @param clientId 客户ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 账单记录列表
     */
    List<BillingRecord> listBillingRecordsByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按案件查询账单记录
     *
     * @param caseId 案件ID
     * @return 账单记录列表
     */
    List<BillingRecord> listBillingRecordsByCase(Long caseId);
    
    /**
     * 统计账单金额
     *
     * @param status 账单状态，可为null
     * @param clientId 客户ID，可为null
     * @param caseId 案件ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 账单总金额
     */
    BigDecimal sumBillingAmount(BillingStatusEnum status, Long clientId, Long caseId,
                               LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计客户账单金额
     *
     * @param clientId 客户ID
     * @param status 账单状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 账单总金额
     */
    BigDecimal sumClientBillingAmount(Long clientId, BillingStatusEnum status,
                                    LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计案件账单金额
     *
     * @param caseId 案件ID
     * @param status 账单状态，可为null
     * @return 账单总金额
     */
    BigDecimal sumCaseBillingAmount(Long caseId, BillingStatusEnum status);
    
    /**
     * 导出账单记录数据
     *
     * @param billingRecordIds 账单记录ID列表
     * @return 导出文件路径
     */
    String exportBillingRecords(List<Long> billingRecordIds);
} 