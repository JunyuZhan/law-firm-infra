package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.Fee;
import com.lawfirm.model.finance.enums.FeeStatusEnum;
import com.lawfirm.model.finance.enums.FeeTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 费用服务接口
 */
public interface FeeService {
    
    /**
     * 创建费用
     *
     * @param fee 费用信息
     * @return 费用ID
     */
    Long createFee(Fee fee);
    
    /**
     * 更新费用
     *
     * @param fee 费用信息
     * @return 是否更新成功
     */
    boolean updateFee(Fee fee);
    
    /**
     * 删除费用
     *
     * @param feeId 费用ID
     * @return 是否删除成功
     */
    boolean deleteFee(Long feeId);
    
    /**
     * 获取费用详情
     *
     * @param feeId 费用ID
     * @return 费用信息
     */
    Fee getFeeById(Long feeId);
    
    /**
     * 更新费用状态
     *
     * @param feeId 费用ID
     * @param status 状态
     * @param remark 备注
     * @return 是否更新成功
     */
    boolean updateFeeStatus(Long feeId, FeeStatusEnum status, String remark);
    
    /**
     * 审批费用
     *
     * @param feeId 费用ID
     * @param approved 是否通过
     * @param approverId 审批人ID
     * @param remark 审批意见
     * @return 是否审批成功
     */
    boolean approveFee(Long feeId, boolean approved, Long approverId, String remark);
    
    /**
     * 支付费用
     *
     * @param feeId 费用ID
     * @param accountId 支付账户ID
     * @param paymentTime 支付时间
     * @param remark 支付备注
     * @return 是否支付成功
     */
    boolean payFee(Long feeId, Long accountId, LocalDateTime paymentTime, String remark);
    
    /**
     * 取消费用
     *
     * @param feeId 费用ID
     * @param reason 取消原因
     * @return 是否取消成功
     */
    boolean cancelFee(Long feeId, String reason);
    
    /**
     * 查询费用列表
     *
     * @param feeType 费用类型，可为null
     * @param status 费用状态，可为null
     * @param departmentId 部门ID，可为null
     * @param costCenterId 成本中心ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 费用列表
     */
    List<Fee> listFees(FeeTypeEnum feeType, FeeStatusEnum status, Long departmentId, Long costCenterId,
                       LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询费用
     *
     * @param page 分页参数
     * @param feeType 费用类型，可为null
     * @param status 费用状态，可为null
     * @param departmentId 部门ID，可为null
     * @param costCenterId 成本中心ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页费用信息
     */
    IPage<Fee> pageFees(IPage<Fee> page, FeeTypeEnum feeType, FeeStatusEnum status,
                        Long departmentId, Long costCenterId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按部门查询费用
     *
     * @param departmentId 部门ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 费用列表
     */
    List<Fee> listFeesByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按成本中心查询费用
     *
     * @param costCenterId 成本中心ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 费用列表
     */
    List<Fee> listFeesByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按案件查询费用
     *
     * @param caseId 案件ID
     * @return 费用列表
     */
    List<Fee> listFeesByCase(Long caseId);
    
    /**
     * 统计费用金额
     *
     * @param feeType 费用类型，可为null
     * @param status 费用状态，可为null
     * @param departmentId 部门ID，可为null
     * @param costCenterId 成本中心ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 费用总金额
     */
    BigDecimal sumFeeAmount(FeeTypeEnum feeType, FeeStatusEnum status, Long departmentId, Long costCenterId,
                           LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计部门费用金额
     *
     * @param departmentId 部门ID
     * @param feeType 费用类型，可为null
     * @param status 费用状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 费用总金额
     */
    BigDecimal sumDepartmentFeeAmount(Long departmentId, FeeTypeEnum feeType, FeeStatusEnum status,
                                    LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计成本中心费用金额
     *
     * @param costCenterId 成本中心ID
     * @param feeType 费用类型，可为null
     * @param status 费用状态，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 费用总金额
     */
    BigDecimal sumCostCenterFeeAmount(Long costCenterId, FeeTypeEnum feeType, FeeStatusEnum status,
                                    LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计案件费用金额
     *
     * @param caseId 案件ID
     * @param feeType 费用类型，可为null
     * @param status 费用状态，可为null
     * @return 费用总金额
     */
    BigDecimal sumCaseFeeAmount(Long caseId, FeeTypeEnum feeType, FeeStatusEnum status);
    
    /**
     * 导出发费数据
     *
     * @param feeIds 费用ID列表
     * @return 导出文件路径
     */
    String exportFees(List<Long> feeIds);
} 