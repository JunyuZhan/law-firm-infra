package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.PaymentPlan;
import com.lawfirm.model.finance.enums.PaymentPlanStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 付款计划服务接口
 */
public interface PaymentPlanService {
    
    /**
     * 创建付款计划
     *
     * @param paymentPlan 付款计划信息
     * @return 付款计划ID
     */
    Long createPaymentPlan(PaymentPlan paymentPlan);
    
    /**
     * 更新付款计划
     *
     * @param paymentPlan 付款计划信息
     * @return 是否更新成功
     */
    boolean updatePaymentPlan(PaymentPlan paymentPlan);
    
    /**
     * 删除付款计划
     *
     * @param paymentPlanId 付款计划ID
     * @return 是否删除成功
     */
    boolean deletePaymentPlan(Long paymentPlanId);
    
    /**
     * 获取付款计划详情
     *
     * @param paymentPlanId 付款计划ID
     * @return 付款计划信息
     */
    PaymentPlan getPaymentPlanById(Long paymentPlanId);
    
    /**
     * 更新付款计划状态
     *
     * @param paymentPlanId 付款计划ID
     * @param status 状态
     * @param remark 备注
     * @return 是否更新成功
     */
    boolean updatePaymentPlanStatus(Long paymentPlanId, PaymentPlanStatusEnum status, String remark);
    
    /**
     * 确认付款
     *
     * @param paymentPlanId 付款计划ID
     * @param actualAmount 实际付款金额
     * @param paymentTime 付款时间
     * @param operatorId 操作人ID
     * @param remark 备注
     * @return 是否确认成功
     */
    boolean confirmPayment(Long paymentPlanId, BigDecimal actualAmount, LocalDateTime paymentTime,
                          Long operatorId, String remark);
    
    /**
     * 取消付款计划
     *
     * @param paymentPlanId 付款计划ID
     * @param reason 取消原因
     * @return 是否取消成功
     */
    boolean cancelPaymentPlan(Long paymentPlanId, String reason);
    
    /**
     * 查询付款计划列表
     *
     * @param status 付款计划状态，可为null
     * @param contractId 合同ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 付款计划列表
     */
    List<PaymentPlan> listPaymentPlans(PaymentPlanStatusEnum status, Long contractId,
                                      LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询付款计划
     *
     * @param page 分页参数
     * @param status 付款计划状态，可为null
     * @param contractId 合同ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页付款计划信息
     */
    IPage<PaymentPlan> pagePaymentPlans(IPage<PaymentPlan> page, PaymentPlanStatusEnum status,
                                       Long contractId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按合同查询付款计划
     *
     * @param contractId 合同ID
     * @return 付款计划列表
     */
    List<PaymentPlan> listPaymentPlansByContract(Long contractId);
    
    /**
     * 按客户查询付款计划
     *
     * @param clientId 客户ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 付款计划列表
     */
    List<PaymentPlan> listPaymentPlansByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计付款计划金额
     *
     * @param status 付款计划状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 付款计划总金额
     */
    BigDecimal sumPaymentPlanAmount(PaymentPlanStatusEnum status, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计合同付款计划金额
     *
     * @param contractId 合同ID
     * @param status 付款计划状态，可为null
     * @return 付款计划总金额
     */
    BigDecimal sumContractPaymentPlanAmount(Long contractId, PaymentPlanStatusEnum status);
    
    /**
     * 导出付款计划数据
     *
     * @param paymentPlanIds 付款计划ID列表
     * @return 导出文件路径
     */
    String exportPaymentPlans(List<Long> paymentPlanIds);
} 