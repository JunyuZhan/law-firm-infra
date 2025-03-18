package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.finance.entity.Payment;
import com.lawfirm.model.finance.enums.PaymentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 付款服务接口
 */
public interface PaymentService {
    
    /**
     * 创建付款记录
     *
     * @param payment 付款信息
     * @return 付款记录ID
     */
    Long createPayment(Payment payment);
    
    /**
     * 更新付款记录
     *
     * @param payment 付款信息
     * @return 是否更新成功
     */
    boolean updatePayment(Payment payment);
    
    /**
     * 删除付款记录
     *
     * @param paymentId 付款记录ID
     * @return 是否删除成功
     */
    boolean deletePayment(Long paymentId);
    
    /**
     * 获取付款记录详情
     *
     * @param paymentId 付款记录ID
     * @return 付款记录信息
     */
    Payment getPaymentById(Long paymentId);
    
    /**
     * 更新付款状态
     *
     * @param paymentId 付款记录ID
     * @param status 状态
     * @param remark 备注
     * @return 是否更新成功
     */
    boolean updatePaymentStatus(Long paymentId, PaymentStatusEnum status, String remark);
    
    /**
     * 确认付款
     *
     * @param paymentId 付款记录ID
     * @param remark 备注
     * @return 是否确认成功
     */
    boolean confirmPayment(Long paymentId, String remark);
    
    /**
     * 取消付款
     *
     * @param paymentId 付款记录ID
     * @param reason 取消原因
     * @return 是否取消成功
     */
    boolean cancelPayment(Long paymentId, String reason);
    
    /**
     * 查询付款记录列表
     *
     * @param status 付款状态，可为null
     * @param contractId 合同ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 付款记录列表
     */
    List<Payment> listPayments(PaymentStatusEnum status, Long contractId,
                             LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询付款记录
     *
     * @param page 分页参数
     * @param status 付款状态，可为null
     * @param contractId 合同ID，可为null
     * @param clientId 客户ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页付款记录信息
     */
    Page<Payment> pagePayments(Page<Payment> page, PaymentStatusEnum status,
                              Long contractId, Long clientId,
                              LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按合同查询付款记录
     *
     * @param page 分页参数
     * @param contractId 合同ID
     * @return 付款记录列表
     */
    Page<Payment> pagePaymentsByContract(Page<Payment> page, Long contractId);
    
    /**
     * 按客户查询付款记录
     *
     * @param page 分页参数
     * @param clientId 客户ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 付款记录列表
     */
    Page<Payment> pagePaymentsByClient(Page<Payment> page, Long clientId, 
                                     LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按案件查询付款记录
     *
     * @param page 分页参数
     * @param caseId 案件ID
     * @return 付款记录列表
     */
    Page<Payment> pagePaymentsByCase(Page<Payment> page, Long caseId);
    
    /**
     * 统计付款金额
     *
     * @param status 付款状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 付款总金额
     */
    BigDecimal sumPaymentAmount(PaymentStatusEnum status, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计合同付款金额
     *
     * @param contractId 合同ID
     * @param status 付款状态，可为null
     * @return 付款总金额
     */
    BigDecimal sumContractPaymentAmount(Long contractId, PaymentStatusEnum status);
    
    /**
     * 统计客户付款金额
     *
     * @param clientId 客户ID
     * @param status 付款状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 付款总金额
     */
    BigDecimal sumClientPaymentAmount(Long clientId, PaymentStatusEnum status,
                                     LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 导出付款数据
     *
     * @param paymentIds 付款记录ID列表
     * @return 导出文件路径
     */
    String exportPayments(List<Long> paymentIds);
} 