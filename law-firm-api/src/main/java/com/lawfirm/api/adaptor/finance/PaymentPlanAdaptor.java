package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.paymentplan.PaymentPlanCreateDTO;
import com.lawfirm.model.finance.dto.paymentplan.PaymentPlanUpdateDTO;
import com.lawfirm.model.finance.entity.PaymentPlan;
import com.lawfirm.model.finance.service.PaymentPlanService;
import com.lawfirm.model.finance.vo.paymentplan.PaymentPlanVO;
import com.lawfirm.model.finance.enums.PaymentPlanStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public PaymentPlanVO createPaymentPlan(PaymentPlanCreateDTO dto) {
        PaymentPlan paymentPlan = paymentPlanService.createPaymentPlan(dto);
        return convert(paymentPlan, PaymentPlanVO.class);
    }

    /**
     * 更新付款计划
     */
    public PaymentPlanVO updatePaymentPlan(Long id, PaymentPlanUpdateDTO dto) {
        PaymentPlan paymentPlan = paymentPlanService.updatePaymentPlan(id, dto);
        return convert(paymentPlan, PaymentPlanVO.class);
    }

    /**
     * 获取付款计划详情
     */
    public PaymentPlanVO getPaymentPlan(Long id) {
        PaymentPlan paymentPlan = paymentPlanService.getPaymentPlan(id);
        return convert(paymentPlan, PaymentPlanVO.class);
    }

    /**
     * 删除付款计划
     */
    public void deletePaymentPlan(Long id) {
        paymentPlanService.deletePaymentPlan(id);
    }

    /**
     * 获取所有付款计划
     */
    public List<PaymentPlanVO> listPaymentPlans() {
        List<PaymentPlan> paymentPlans = paymentPlanService.listPaymentPlans();
        return paymentPlans.stream()
                .map(paymentPlan -> convert(paymentPlan, PaymentPlanVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新付款计划状态
     */
    public void updatePaymentPlanStatus(Long id, PaymentPlanStatusEnum status) {
        paymentPlanService.updatePaymentPlanStatus(id, status);
    }

    /**
     * 根据合同ID查询付款计划
     */
    public List<PaymentPlanVO> getPaymentPlansByContractId(Long contractId) {
        List<PaymentPlan> paymentPlans = paymentPlanService.getPaymentPlansByContractId(contractId);
        return paymentPlans.stream()
                .map(paymentPlan -> convert(paymentPlan, PaymentPlanVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据客户ID查询付款计划
     */
    public List<PaymentPlanVO> getPaymentPlansByClientId(Long clientId) {
        List<PaymentPlan> paymentPlans = paymentPlanService.getPaymentPlansByClientId(clientId);
        return paymentPlans.stream()
                .map(paymentPlan -> convert(paymentPlan, PaymentPlanVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询付款计划
     */
    public List<PaymentPlanVO> getPaymentPlansByDepartmentId(Long departmentId) {
        List<PaymentPlan> paymentPlans = paymentPlanService.getPaymentPlansByDepartmentId(departmentId);
        return paymentPlans.stream()
                .map(paymentPlan -> convert(paymentPlan, PaymentPlanVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查付款计划是否存在
     */
    public boolean existsPaymentPlan(Long id) {
        return paymentPlanService.existsPaymentPlan(id);
    }

    /**
     * 获取付款计划数量
     */
    public long countPaymentPlans() {
        return paymentPlanService.countPaymentPlans();
    }
} 