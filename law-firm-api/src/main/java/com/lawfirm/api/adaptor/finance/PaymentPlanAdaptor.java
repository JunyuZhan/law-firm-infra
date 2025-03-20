package com.lawfirm.api.adaptor.finance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.paymentplan.PaymentPlanCreateDTO;
import com.lawfirm.model.finance.dto.paymentplan.PaymentPlanUpdateDTO;
import com.lawfirm.model.finance.entity.PaymentPlan;
import com.lawfirm.model.finance.service.PaymentPlanService;
import com.lawfirm.model.finance.vo.payment.PaymentPlanVO;
import com.lawfirm.model.finance.enums.PaymentPlanStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 付款计划管理适配器
 */
@Component
public class PaymentPlanAdaptor extends BaseAdaptor {

    @Autowired
    private PaymentPlanService paymentPlanService;

    /**
     * 创建付款计划
     */
    public Long createPaymentPlan(PaymentPlanCreateDTO dto) {
        PaymentPlan paymentPlan = convert(dto, PaymentPlan.class);
        return paymentPlanService.createPaymentPlan(paymentPlan);
    }

    /**
     * 更新付款计划
     */
    public boolean updatePaymentPlan(Long id, PaymentPlanUpdateDTO dto) {
        PaymentPlan paymentPlan = convert(dto, PaymentPlan.class);
        paymentPlan.setId(id);
        return paymentPlanService.updatePaymentPlan(paymentPlan);
    }

    /**
     * 获取付款计划详情
     */
    public PaymentPlanVO getPaymentPlan(Long id) {
        PaymentPlan paymentPlan = paymentPlanService.getPaymentPlanById(id);
        return convert(paymentPlan, PaymentPlanVO.class);
    }

    /**
     * 删除付款计划
     */
    public boolean deletePaymentPlan(Long id) {
        return paymentPlanService.deletePaymentPlan(id);
    }

    /**
     * 查询付款计划列表
     */
    public List<PaymentPlanVO> listPaymentPlans(PaymentPlanStatusEnum status, Long contractId,
                                           LocalDateTime startTime, LocalDateTime endTime) {
        List<PaymentPlan> paymentPlans = paymentPlanService.listPaymentPlans(status, contractId, startTime, endTime);
        return paymentPlans.stream()
                .map(paymentPlan -> convert(paymentPlan, PaymentPlanVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新付款计划状态
     */
    public boolean updatePaymentPlanStatus(Long id, PaymentPlanStatusEnum status, String remark) {
        return paymentPlanService.updatePaymentPlanStatus(id, status, remark);
    }

    /**
     * 确认付款
     */
    public boolean confirmPayment(Long id, BigDecimal actualAmount, LocalDateTime paymentTime,
                               Long operatorId, String remark) {
        return paymentPlanService.confirmPayment(id, actualAmount, paymentTime, operatorId, remark);
    }

    /**
     * 取消付款计划
     */
    public boolean cancelPaymentPlan(Long id, String reason) {
        return paymentPlanService.cancelPaymentPlan(id, reason);
    }

    /**
     * 分页查询付款计划
     */
    public IPage<PaymentPlanVO> pagePaymentPlans(IPage<PaymentPlan> page, PaymentPlanStatusEnum status,
                                            Long contractId, LocalDateTime startTime, LocalDateTime endTime) {
        IPage<PaymentPlan> paymentPlanPage = paymentPlanService.pagePaymentPlans(page, status, contractId, startTime, endTime);
        return paymentPlanPage.convert(paymentPlan -> convert(paymentPlan, PaymentPlanVO.class));
    }

    /**
     * 按合同查询付款计划
     */
    public List<PaymentPlanVO> listPaymentPlansByContract(Long contractId) {
        List<PaymentPlan> paymentPlans = paymentPlanService.listPaymentPlansByContract(contractId);
        return paymentPlans.stream()
                .map(paymentPlan -> convert(paymentPlan, PaymentPlanVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按客户查询付款计划
     */
    public List<PaymentPlanVO> listPaymentPlansByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        List<PaymentPlan> paymentPlans = paymentPlanService.listPaymentPlansByClient(clientId, startTime, endTime);
        return paymentPlans.stream()
                .map(paymentPlan -> convert(paymentPlan, PaymentPlanVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 统计付款计划金额
     */
    public BigDecimal sumPaymentPlanAmount(PaymentPlanStatusEnum status, LocalDateTime startTime, LocalDateTime endTime) {
        return paymentPlanService.sumPaymentPlanAmount(status, startTime, endTime);
    }

    /**
     * 统计合同付款计划金额
     */
    public BigDecimal sumContractPaymentPlanAmount(Long contractId, PaymentPlanStatusEnum status) {
        return paymentPlanService.sumContractPaymentPlanAmount(contractId, status);
    }

    /**
     * 导出付款计划数据
     */
    public String exportPaymentPlans(List<Long> paymentPlanIds) {
        return paymentPlanService.exportPaymentPlans(paymentPlanIds);
    }
} 