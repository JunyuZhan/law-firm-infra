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
 * <p>
 * 该适配器负责处理与财务费用管理相关的所有操作，包括费用的创建、更新、查询和统计等功能。
 * 作为API层与服务层之间的桥梁，转换请求参数并调用FeeService提供的服务。
 * 使用指定Bean名称"financeAdaptor"以避免与案件模块中的同名适配器冲突。
 * </p>
 * 
 * @author JunyuZhan
 * @version 1.0.0
 * @since 1.0.0
 */
@Component("financeAdaptor")
public class FeeAdaptor extends BaseAdaptor {

    @Autowired
    private FeeService feeService;

    /**
     * 创建费用
     * 
     * @param fee 费用实体
     * @return 创建的费用ID
     */
    public Long createFee(Fee fee) {
        return feeService.createFee(fee);
    }

    /**
     * 更新费用
     * 
     * @param fee 费用实体
     * @return 更新是否成功
     */
    public boolean updateFee(Fee fee) {
        return feeService.updateFee(fee);
    }

    /**
     * 获取费用详情
     * 
     * @param id 费用ID
     * @return 费用详情视图对象
     */
    public FeeDetailVO getFee(Long id) {
        Fee fee = feeService.getFeeById(id);
        return convert(fee, FeeDetailVO.class);
    }

    /**
     * 删除费用
     * 
     * @param id 费用ID
     * @return 删除是否成功
     */
    public boolean deleteFee(Long id) {
        return feeService.deleteFee(id);
    }

    /**
     * 更新费用状态
     * 
     * @param feeId 费用ID
     * @param status 状态枚举
     * @param remark 备注
     * @return 更新是否成功
     */
    public boolean updateFeeStatus(Long feeId, FeeStatusEnum status, String remark) {
        return feeService.updateFeeStatus(feeId, status, remark);
    }

    /**
     * 审批费用
     * 
     * @param feeId 费用ID
     * @param approved 是否通过
     * @param approverId 审批人ID
     * @param remark 审批备注
     * @return 审批是否成功
     */
    public boolean approveFee(Long feeId, boolean approved, Long approverId, String remark) {
        return feeService.approveFee(feeId, approved, approverId, remark);
    }

    /**
     * 支付费用
     * 
     * @param feeId 费用ID
     * @param accountId 支付账户ID
     * @param paymentTime 支付时间
     * @param remark 支付备注
     * @return 支付是否成功
     */
    public boolean payFee(Long feeId, Long accountId, LocalDateTime paymentTime, String remark) {
        return feeService.payFee(feeId, accountId, paymentTime, remark);
    }

    /**
     * 取消费用
     * 
     * @param feeId 费用ID
     * @param reason 取消原因
     * @return 取消是否成功
     */
    public boolean cancelFee(Long feeId, String reason) {
        return feeService.cancelFee(feeId, reason);
    }

    /**
     * 查询费用列表
     * 
     * @param feeType 费用类型枚举
     * @param status 费用状态枚举
     * @param departmentId 部门ID
     * @param costCenterId 成本中心ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 费用详情视图对象列表
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
     * 
     * @param page 分页参数
     * @param feeType 费用类型枚举
     * @param status 费用状态枚举
     * @param departmentId 部门ID
     * @param costCenterId 成本中心ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 费用详情视图对象分页
     */
    public IPage<FeeDetailVO> pageFees(IPage<Fee> page, FeeTypeEnum feeType, FeeStatusEnum status,
                                     Long departmentId, Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        IPage<Fee> feePage = feeService.pageFees(page, feeType, status, departmentId, costCenterId, startTime, endTime);
        return feePage.convert(fee -> convert(fee, FeeDetailVO.class));
    }

    /**
     * 按部门查询费用
     * 
     * @param departmentId 部门ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 费用详情视图对象列表
     */
    public List<FeeDetailVO> listFeesByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Fee> fees = feeService.listFeesByDepartment(departmentId, startTime, endTime);
        return fees.stream()
                .map(fee -> convert(fee, FeeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按成本中心查询费用
     * 
     * @param costCenterId 成本中心ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 费用详情视图对象列表
     */
    public List<FeeDetailVO> listFeesByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Fee> fees = feeService.listFeesByCostCenter(costCenterId, startTime, endTime);
        return fees.stream()
                .map(fee -> convert(fee, FeeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 按案件查询费用
     * 
     * @param caseId 案件ID
     * @return 费用详情视图对象列表
     */
    public List<FeeDetailVO> listFeesByCase(Long caseId) {
        List<Fee> fees = feeService.listFeesByCase(caseId);
        return fees.stream()
                .map(fee -> convert(fee, FeeDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 统计费用金额
     * 
     * @param feeType 费用类型枚举
     * @param status 费用状态枚举
     * @param departmentId 部门ID
     * @param costCenterId 成本中心ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 费用总金额
     */
    public BigDecimal sumFeeAmount(FeeTypeEnum feeType, FeeStatusEnum status, Long departmentId, Long costCenterId,
                                 LocalDateTime startTime, LocalDateTime endTime) {
        return feeService.sumFeeAmount(feeType, status, departmentId, costCenterId, startTime, endTime);
    }

    /**
     * 统计部门费用金额
     * 
     * @param departmentId 部门ID
     * @param feeType 费用类型枚举
     * @param status 费用状态枚举
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 部门费用总金额
     */
    public BigDecimal sumDepartmentFeeAmount(Long departmentId, FeeTypeEnum feeType, FeeStatusEnum status,
                                          LocalDateTime startTime, LocalDateTime endTime) {
        return feeService.sumDepartmentFeeAmount(departmentId, feeType, status, startTime, endTime);
    }

    /**
     * 统计成本中心费用金额
     * 
     * @param costCenterId 成本中心ID
     * @param feeType 费用类型枚举
     * @param status 费用状态枚举
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 成本中心费用总金额
     */
    public BigDecimal sumCostCenterFeeAmount(Long costCenterId, FeeTypeEnum feeType, FeeStatusEnum status,
                                          LocalDateTime startTime, LocalDateTime endTime) {
        return feeService.sumCostCenterFeeAmount(costCenterId, feeType, status, startTime, endTime);
    }

    /**
     * 统计案件费用金额
     * 
     * @param caseId 案件ID
     * @param feeType 费用类型枚举
     * @param status 费用状态枚举
     * @return 案件费用总金额
     */
    public BigDecimal sumCaseFeeAmount(Long caseId, FeeTypeEnum feeType, FeeStatusEnum status) {
        return feeService.sumCaseFeeAmount(caseId, feeType, status);
    }

    /**
     * 导出费用数据
     * 
     * @param feeIds 费用ID列表
     * @return 导出文件路径
     */
    public String exportFees(List<Long> feeIds) {
        return feeService.exportFees(feeIds);
    }
} 