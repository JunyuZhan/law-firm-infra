package com.lawfirm.api.adaptor.finance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.entity.Fee;
import com.lawfirm.model.finance.service.FeeService;
import com.lawfirm.model.finance.vo.fee.FeeDetailVO;
import com.lawfirm.model.finance.enums.FeeStatusEnum;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 费用管理适配器
 */
@Component
public class FeeAdaptor extends BaseAdaptor {

    @Autowired
    private FeeService feeService;

    /**
     * 创建费用
     */
    public Long createFee(Fee fee) {
        return feeService.createFee(fee);
    }

    /**
     * 更新费用
     */
    public boolean updateFee(Fee fee) {
        return feeService.updateFee(fee);
    }

    /**
     * 获取费用详情
     */
    public FeeDetailVO getFee(Long id) {
        Fee fee = feeService.getFeeById(id);
        return convert(fee, FeeDetailVO.class);
    }

    /**
     * 删除费用
     */
    public boolean deleteFee(Long id) {
        return feeService.deleteFee(id);
    }

    /**
     * 更新费用状态
     */
    public boolean updateFeeStatus(Long feeId, FeeStatusEnum status, String remark) {
        return feeService.updateFeeStatus(feeId, status, remark);
    }

    /**
     * 审批费用
     */
    public boolean approveFee(Long feeId, boolean approved, Long approverId, String remark) {
        return feeService.approveFee(feeId, approved, approverId, remark);
    }

    /**
     * 支付费用
     */
    public boolean payFee(Long feeId, Long accountId, LocalDateTime paymentTime, String remark) {
        return feeService.payFee(feeId, accountId, paymentTime, remark);
    }

    /**
     * 取消费用
     */
    public boolean cancelFee(Long feeId, String reason) {
        return feeService.cancelFee(feeId, reason);
    }

    /**
     * 查询费用列表
     */
    public List<FeeDetailVO> listFees(FeeTypeEnum feeType, FeeStatusEnum status, Long departmentId, Long costCenterId,
                                    LocalDateTime startTime, LocalDateTime endTime) {
        List<Fee> fees = feeService.listFees(feeType, status, departmentId, costCenterId, startTime, endTime);
        return fees.stream()
                .map(fee -> convert(fee, FeeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 分页查询费用
     */
    public IPage<FeeDetailVO> pageFees(IPage<Fee> page, FeeTypeEnum feeType, FeeStatusEnum status,
                                     Long departmentId, Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        IPage<Fee> feePage = feeService.pageFees(page, feeType, status, departmentId, costCenterId, startTime, endTime);
        return feePage.convert(fee -> convert(fee, FeeDetailVO.class));
    }

    /**
     * 按部门查询费用
     */
    public List<FeeDetailVO> listFeesByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Fee> fees = feeService.listFeesByDepartment(departmentId, startTime, endTime);
        return fees.stream()
                .map(fee -> convert(fee, FeeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按成本中心查询费用
     */
    public List<FeeDetailVO> listFeesByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Fee> fees = feeService.listFeesByCostCenter(costCenterId, startTime, endTime);
        return fees.stream()
                .map(fee -> convert(fee, FeeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按案件查询费用
     */
    public List<FeeDetailVO> listFeesByCase(Long caseId) {
        List<Fee> fees = feeService.listFeesByCase(caseId);
        return fees.stream()
                .map(fee -> convert(fee, FeeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 统计费用金额
     */
    public BigDecimal sumFeeAmount(FeeTypeEnum feeType, FeeStatusEnum status, Long departmentId, Long costCenterId,
                                 LocalDateTime startTime, LocalDateTime endTime) {
        return feeService.sumFeeAmount(feeType, status, departmentId, costCenterId, startTime, endTime);
    }

    /**
     * 统计部门费用金额
     */
    public BigDecimal sumDepartmentFeeAmount(Long departmentId, FeeTypeEnum feeType, FeeStatusEnum status,
                                          LocalDateTime startTime, LocalDateTime endTime) {
        return feeService.sumDepartmentFeeAmount(departmentId, feeType, status, startTime, endTime);
    }

    /**
     * 统计成本中心费用金额
     */
    public BigDecimal sumCostCenterFeeAmount(Long costCenterId, FeeTypeEnum feeType, FeeStatusEnum status,
                                          LocalDateTime startTime, LocalDateTime endTime) {
        return feeService.sumCostCenterFeeAmount(costCenterId, feeType, status, startTime, endTime);
    }

    /**
     * 统计案件费用金额
     */
    public BigDecimal sumCaseFeeAmount(Long caseId, FeeTypeEnum feeType, FeeStatusEnum status) {
        return feeService.sumCaseFeeAmount(caseId, feeType, status);
    }

    /**
     * 导出费用数据
     */
    public String exportFees(List<Long> feeIds) {
        return feeService.exportFees(feeIds);
    }
} 